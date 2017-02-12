package project1;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import javax.swing.text.View;

// @WebServlet(name = "generate_items_table", urlPatterns = { "/generate_items_table" })
public class generate_items_table
  extends HttpServlet
{
  private static final String CONTENT_TYPE = "text/html; charset=windows-1256";

  public void init(ServletConfig config)
    throws ServletException
  {
    super.init(config);
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    connect_to_db connect = new connect_to_db();
    Connection con = connect.connect();
    
    String item_id = "";
    
    boolean authorized_checkout = true;
    
    response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter();
    
    db_queries queries = new db_queries();
    
    //get search parameters from request
    //
    //
    
    String type = request.getParameter("type");
    String brand = request.getParameter("brand");
    String location = request.getParameter("location");
    String condition = request.getParameter("condition");
    String label = request.getParameter("label");
    String sn = request.getParameter("sn");
    String availability_available = request.getParameter("availability_available");
    String availability_out = request.getParameter("availability_out");
    
    String[] specs_names = null;  //added specs input elements ids
    String[] specs_values = null; //added specs input elements values(content)
    
    List<String> result_ids = new ArrayList<String>();  //results of items
    List<String> list_specs_names = new ArrayList<String>();  //will be used to store only specs that have values in the search
    List<String> list_specs_values = new ArrayList<String>(); //will be used to store only the specs values that are not ""
    
    //if there are added specs
    if(request.getParameterValues("specs_names[]") != null)
    {
      specs_names = request.getParameterValues("specs_names[]");  //get added specs names
      specs_values = request.getParameterValues("specs_values[]");  //get added specs values
      
      for(int j = 0; j < specs_names.length ; j++)  //if value is not "", add to lists
      {
        if(!specs_values[j].equals(""))
        {
          list_specs_names.add(specs_names[j]);
          list_specs_values.add(specs_values[j]);
        }
      }
    }
    
    //In the following lines, what is being done is the following.
    //A query, sql_master, is being built according to the search parameters of the user
    //sql_master has the general form like below:
    //SELECT * FROM (SELECT * FROM (select items.label, itemspecvalues.value, specs.name FROM items, specs, itemspecvalues 
    //WHERE itemspecvalues.item_id = items.id AND itemspecvalues.spec_id = specs.id) 
    //pivot(max(value) for name IN ('RAM' "RAM",'CPU' "CPU"))) WHERE RAM = '8' AND CPU = '3' ;
    //The query significantly changes whether the user added specific item specs to the search parameters.
    //Think of the process as first building the 'skeleton' of sql_master, and later building up it's body according to the search parameters
    
    String sql_master = "SELECT * FROM (SELECT * FROM (SELECT ITEMS.ID";
    
    if(list_specs_names.size() > 0)
    {
      sql_master = sql_master + ", ITEMSPECVALUES.VALUE, SPECS.NAME";
    }
    
    sql_master = sql_master + " FROM ITEMS";
    
    if(list_specs_names.size() > 0)
    {
      sql_master = sql_master + ", SPECS, ITEMSPECVALUES";
    }
    
    sql_master = sql_master + " WHERE ITEMS.DELETED = '0'";
    
    if(list_specs_names.size() > 0)
    {
      sql_master = sql_master + " AND ITEMSPECVALUES.ITEM_ID = ITEMS.ID AND ITEMSPECVALUES.SPEC_ID = SPECS.ID";
    }
    
    if(!type.equals("any")) //if type is selected, add type id in query
    {
      int type_id = queries.get_type_id_from_name(type);
      sql_master = sql_master + " AND ITEMS.TYPE_ID = '" + type_id + "'";
    }
    
    if(!brand.equals("any"))  //if brand is selected, add brand id in query
    {
      int brand_id = queries.get_brand_id_from_name(brand);
      sql_master = sql_master + " AND ITEMS.BRAND_ID = '" + brand_id + "'";
    }
    
    if(!location.equals("any")) //if location is selected, add location id in query
    {
      int location_id = queries.get_location_id_from_name(location);
      sql_master = sql_master + " AND ITEMS.LOCATION_ID = '" + location_id + "'";
    }
    
    if(!condition.equals("any"))  //if condition is selected, add condition id in query
    {
      int condition_id = queries.get_condition_id_from_name(condition);
      sql_master = sql_master + " AND ITEMS.CONDITION_ID = '" + condition_id + "'";
    }
    
    if(!label.equals("")) //if label is given, add it in query
    {
      sql_master = sql_master + " AND ITEMS.LABEL LIKE '%" + label + "%'";
    }
    
    if(!sn.equals(""))  //if serial number is given, add it in query
    {
      sql_master = sql_master + " AND ITEMS.SERIAL_NUMBER LIKE '%" + sn + "%'";
    }
    
    if(availability_available != null && availability_available.equals("1"))  //if availability option is available (show only available items), add this to the query
    {
      sql_master = sql_master + " AND ITEMS.AVAILABILITY = '1'";
    }
    
    else if(availability_out != null && availability_out.equals("1")) //if availability option is out (show only items that are out), add this to the query
    {
      sql_master = sql_master + " AND ITEMS.AVAILABILITY = '0'";
    }
    
    sql_master = sql_master + ")";
    
    if(list_specs_names.size() > 0) //if there are added specs
    {
      sql_master = sql_master + " PIVOT(MAX(VALUE) FOR NAME IN(";
      for(int i = 0; i < list_specs_names.size() ; i++)  //for every added spec
      {
        String[] parts1 = list_specs_names.get(i).split("spec_");  //parse the input text element id to get the added spec name
        String[] parts2 = parts1[1].split("_");
        String spec = parts2[0];
        
        sql_master = sql_master + "'" + spec + "' " + "\"" + spec + "\"";
        
        if(i< list_specs_names.size() -1)
        {
          sql_master = sql_master + ",";
        }
      }
      
      sql_master = sql_master + "))";
    }
    
    sql_master = sql_master + ")";
    
    if(list_specs_names.size() > 0)
    {
      sql_master = sql_master + " WHERE ";
      
      for(int i = 0; i < list_specs_names.size() ; i++)  //for every added spec
      {
        String[] parts1 = list_specs_names.get(i).split("spec_");  //parse the input text element id to get the added spec name
        String[] parts2 = parts1[1].split("_");
        String spec = parts2[0];
        
        if(i > 0)
        {
          sql_master = sql_master + " AND ";
        }
        
        sql_master = sql_master + spec + " = " + "'" + list_specs_values.get(i) + "'";
      }
    }
    
    //javascript links
    //
    //
    
    out.println("<script src='js/items_hq_general.js'></script>");  
    out.println("<script src='js/popup_jquery.js'></script>");
    
    //generate the results table
    //
    //
    
    out.println("<table border = '1'>");
    if(authorized_checkout)
    {
      out.println("<th>Select</th>");
    }
    out.println("<th>ID</th>");
    out.println("<th>Type</th>");
    out.println("<th>Brand</th>");
    out.println("<th>Location</th>");
    out.println("<th>Condition</th>");
    out.println("<th>Label</th>");
    out.println("<th>Serial Number</th>");
    out.println("<th>Notes</th>");
    out.println("<th>Availability</th>");
    out.println("<th>Receipt</th>");
    out.println("<th>Details</th>");
    out.println("<th>Action</th>");
    
    try
    {
      Statement stat_master = con.createStatement();
      ResultSet rs_master = stat_master.executeQuery(sql_master); //execute query
      
      //sql_master returns the IDs of items to be that fit the search options. Add each of these ID to the list of result ids
      //
      //
      
      while(rs_master.next()) 
      {
        result_ids.add(rs_master.getString(1));
      }
      
      Statement stat_final = con.createStatement();
      
      //Below we are generating results of information of the resulting items
      //Then we add them to the table
      //
      for(int k = 0; k < result_ids.size() ; k++)
      {
        String sql_final = "SELECT ITEMS.ID, TYPES.NAME, BRANDS.NAME, LOCATIONS.NAME, ITEM_CONDITIONS.NAME, ITEMS.LABEL,ITEMS.SERIAL_NUMBER, ITEMS.NOTES, ITEMS.AVAILABILITY FROM ITEMS, TYPES, BRANDS, LOCATIONS, ITEM_CONDITIONS WHERE ITEMS.TYPE_ID = TYPES.ID AND ITEMS.BRAND_ID = BRANDS.ID AND ITEMS.LOCATION_ID = LOCATIONS.ID AND ITEMS.CONDITION_ID = ITEM_CONDITIONS.ID AND ITEMS.ID = '" + result_ids.get(k) + "'";
        ResultSet rs_final = stat_final.executeQuery(sql_final);
        while(rs_final.next())
        {
          item_id = rs_final.getString(1);
          out.println("<tr>");
          if(authorized_checkout)
          {
            if(rs_final.getString(9).equals("1"))
            {
              out.println("<td><input type = 'checkbox' class = 'items_checkboxes_class' id = 'checkbox_" + item_id + "'></td>");
            }
            else
            {
              out.println("<td>-</td>");
            }
          }
          out.println("<td>" + rs_final.getString(1) + "</td>");
          out.println("<td id = 'item_" + item_id +"_type'>" + rs_final.getString(2) + "</td>");
          out.println("<td>" + rs_final.getString(3) + "</td>");
          out.println("<td>" + rs_final.getString(4) + "</td>");
          out.println("<td>" + rs_final.getString(5) + "</td>");
          out.println("<td>" + rs_final.getString(6) + "</td>");
          out.println("<td>" + rs_final.getString(7) + "</td>");
          if(rs_final.getString(8) != null && !rs_final.getString(8).equals("null"))
          {  
            out.println("<td>" + rs_final.getString(8) + "</td>");
          }
          else
          {
            out.println("<td>-</td>");
          }
          if(rs_final.getString(9).equals("1"))
          {
            out.println("<td>Available</td>");
            out.println("<td>-</td>");
          }
          else
          {
            out.println("<td>Out</td>");
            out.println("<td><a href = 'search_records.jsp?item_id=" + item_id +"'><img src = 'images/receipt.png'></td></a>");
          }
          out.println("<td><a class='showAlert' title='View' onclick = 'show_specs(" + item_id + ")'>View</a></td>");  //add View link, when clicked, popup will show with the details of the item. onclick of the link show_specs is called from popup_jquery.js and give it as parameter the item id
          out.println("<td><a + onclick = 'delete_item(" + item_id + ")'><img src = 'images/delete.png'></td></a>");  //to delete an item. onclick calls delete_item() from items_hq_general.js and takes as argument item_id
          out.println("</tr>");
        }
      }
    }
    catch(Exception e)
    {
      System.out.println(e.toString());
    }
    out.println("</table>");
    
    if(authorized_checkout)
    {
      out.println("<br><input id = 'add_to_cart_button' type = 'button' value = 'Add to cart' onclick = 'add_to_cart();' />");
    }
    out.close();
  }
}
