package project1;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    boolean authorized_checkout = false;
    boolean authorized_delete = false;
    
    HttpSession session = request.getSession(); //get session
    Access access = new Access();
    String user = (String)session.getAttribute("username");
    
    String method_checkout = "checkout_item";
    if(access.has_access(user, method_checkout))  //if logged in user has access to checkout item
    {
      authorized_checkout = true;
    }
    
    String method_delete_item = "delete_item";
    if(access.has_access(user, method_delete_item)) //if logged in user has access to delete item
    {
      authorized_delete = true;
    }
    
    
    response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter();
    
    db_queries queries = new db_queries();
    
    //get search parameters from request
    //
    
    //
    String search_item_id = request.getParameter("item_id");
    String type = request.getParameter("type");
    String brand = request.getParameter("brand");
    String location = request.getParameter("location");
    String condition = request.getParameter("condition");
    String label = request.getParameter("label");
    String sn = request.getParameter("sn");
    String model = request.getParameter("model");
    String keyword = request.getParameter("keyword");
    String availability_available = request.getParameter("availability_available");
    String availability_out = request.getParameter("availability_out");
    String availability_not_returning = request.getParameter("availability_not_returning");
    int total_items_count = 0;
    
    String[] specs_names = null;  //added specs input elements ids
    String[] specs_values = null; //added specs input elements values(content)
    
    List<Integer> result_ids = new ArrayList<>();  //results of items
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
    
    if(!search_item_id.equals("")) //if type is selected, add type id in query
    {
      sql_master = sql_master + " AND ITEMS.ID = '" + search_item_id + "'";
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
      sql_master = sql_master + " AND UPPER(ITEMS.LABEL) LIKE UPPER('%" + label + "%')";
    }
    
    if(!sn.equals(""))  //if serial number is given, add it in query
    {
      sql_master = sql_master + " AND UPPER(ITEMS.SERIAL_NUMBER) LIKE UPPER('%" + sn + "%')";
    }
    
    if(!model.equals(""))  //if serial number is given, add it in query
    {
      sql_master = sql_master + " AND UPPER(ITEMS.MODEL) LIKE UPPER('%" + model + "%')";
    }
    
    if(!keyword.equals(""))  //if keyword is given, add it in query
    {
      sql_master = sql_master + " AND (UPPER(ITEMS.SERIAL_NUMBER) LIKE UPPER('%" + keyword + "%') OR UPPER(ITEMS.LABEL) LIKE UPPER('%" + keyword + "%') OR UPPER(ITEMS.MODEL) LIKE UPPER('%" + keyword + "%') OR UPPER(ITEMS.NOTES) LIKE UPPER('%" + keyword + "%') OR UPPER(ITEMS.KEYWORD) LIKE UPPER('%" + keyword + "%'))";
    }
    
    if(availability_available != null && availability_available.equals("1"))  //if availability option is available (show only available items), add this to the query
    {
      sql_master = sql_master + " AND ITEMS.AVAILABILITY = '1'";
    }
    
    else if(availability_out != null && availability_out.equals("1")) //if availability option is out (show only items that are out), add this to the query
    {
      sql_master = sql_master + " AND ITEMS.AVAILABILITY = '0'";
    }
    else if(availability_not_returning != null && availability_not_returning.equals("1")) //if availability option is out (show only items that are out), add this to the query
    {
      sql_master = sql_master + " AND ITEMS.AVAILABILITY = '2'";
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
        
        sql_master = sql_master + "UPPER(\"" + spec + "\")" + " LIKE UPPER('%" + list_specs_values.get(i) + "%')";
      }
    }
    //javascript links
    //
    //
    //System.out.println(sql_master);
    out.println("<script src='js/items_hq_general.js'></script>");  
    out.println("<script src='js/popup_jquery.js'></script>");
    
    try
    {
      Statement stat_master = con.createStatement();
      ResultSet rs_master = stat_master.executeQuery(sql_master); //execute query
      
      //sql_master returns the IDs of items to be that fit the search options. Add each of these ID to the list of result ids
      //
      //
      
      while(rs_master.next()) 
      {
        result_ids.add(rs_master.getInt(1));
      }
      Collections.sort(result_ids);
      total_items_count = result_ids.size();
      
      //generate the results table
      //
      //
      out.println("<a class=\"export\">Export Table data into Excel</a>");
      out.println("<a class=\"hrefprintclass\" target=\"_blank\"\" onclick = \"print_results_table()\">Print table</a>");
      out.println("<div id = 'results_table_div'>");
      out.println("<table class=\"fixed_headers\" id = 'items_search_results_table'>");
      if(authorized_checkout)
      {
        out.println("<th>Select</th>");
      }
      out.println("<th>ID</th>");
      out.println("<th>Type</th>");
      out.println("<th>Brand</th>");
      out.println("<th>Model</th>");
      out.println("<th>HQ Location</th>");
      out.println("<th>Location</th>");
      out.println("<th>Condition</th>");
      out.println("<th>Label</th>");
      out.println("<th>Keyword</th>");
      out.println("<th>Availability</th>");
      out.println("<th>Receipt</th>");
      out.println("<th>Details</th>");
      out.println("</div>");
      if(authorized_delete)
      {
        out.println("<th>Action</th>");
      }
      Statement stat_final = con.createStatement();
      
      //Below we are generating results of information of the resulting items
      //Then we add them to the table
      //
        //System.out.println("Size: " + result_ids.size());
      for(int k = 0; k < result_ids.size() ; k++)
      {
        //System.out.println(k + ": " + result_ids.get(k));
        String sql_final = "SELECT ITEMS.ID, TYPES.NAME, BRANDS.NAME,ITEMS.MODEL, LOCATIONS.NAME,REMOTE_LOCATIONS.NAME, ITEM_CONDITIONS.NAME, ITEMS.LABEL,ITEMS.KEYWORD, ITEMS.AVAILABILITY FROM ITEMS, TYPES, BRANDS, LOCATIONS,REMOTE_LOCATIONS, ITEM_CONDITIONS WHERE ITEMS.TYPE_ID = TYPES.ID AND ITEMS.BRAND_ID = BRANDS.ID AND ITEMS.LOCATION_ID = LOCATIONS.ID AND ITEMS.CONDITION_ID = ITEM_CONDITIONS.ID AND ITEMS.CURRENT_LOCATION_ID = REMOTE_LOCATIONS.ID AND ITEMS.ID = '" + result_ids.get(k) + "'";
        ResultSet rs_final = stat_final.executeQuery(sql_final);
        rs_final.next();
        item_id = rs_final.getString(1);
        if(rs_final.getString(10).equals("1"))
        {
          out.println("<tr class = 'item_available_row'>");
        }
        else if(rs_final.getString(10).equals("0"))
        {
          out.println("<tr class = 'item_out_row'>");
        }
        else if(rs_final.getString(10).equals("2"))
        {
          out.println("<tr class = 'item_not_returning_row'>");
        }
        if(authorized_checkout)
        {
          if(rs_final.getString(10).equals("1"))
          {
            out.println("<td align = 'center'><input type = 'checkbox' class = 'items_checkboxes_class' id = 'checkbox_" + item_id + "'></td>");
          }
          else
          {
            out.println("<td align = 'center'>-</td>");
          }
        }
        out.println("<td class = 'search_results_item_ids_class td_id' align = 'center'>" + rs_final.getString(1) + "</td>");
        out.println("<td class = 'td_normal' align = 'center' id = 'item_" + item_id +"_type'>" + rs_final.getString(2) + "</td>");
        out.println("<td class = 'td_normal' align = 'center'>" + rs_final.getString(3) + "</td>");
        if(rs_final.getString(4) != null && !rs_final.getString(4).equals("null"))
        {
          out.println("<td class = 'td_normal' align = 'center'>" + rs_final.getString(4) + "</td>");
        }
        else
        {
          out.println("<td class = 'td_normal' align = 'center'>-</td>");
        }
        out.println("<td class = 'td_normal' align = 'center'>" + rs_final.getString(5) + "</td>");
        if(rs_final.getString(6).equals("0")) //if remote location is the same as hq location
        {
          out.println("<td class = 'td_normal' align = 'center'>" + rs_final.getString(5) + "</td>");
        }
        else
        {
          out.println("<td class = 'td_normal' align = 'center'>" + rs_final.getString(6) + "</td>");
        }
        out.println("<td class = 'td_normal' align = 'center'>" + rs_final.getString(7) + "</td>");
        if(rs_final.getString(8) != null && !rs_final.getString(8).equals("null"))
        {
          out.println("<td class = 'td_normal' align = 'center'>" + rs_final.getString(8) + "</td>");
        }
        else
        {
          out.println("<td class = 'td_normal' align = 'center'>-</td>");
        }
        if(rs_final.getString(9) != null && !rs_final.getString(9).equals("null"))
        {
          out.println("<td class = 'td_normal' align = 'center'>" + rs_final.getString(9) + "</td>");
        }
        else
        {
          out.println("<td class = 'td_normal' align = 'center'>-</td>");
        }
        if(rs_final.getString(10).equals("1"))
        {
          out.println("<td class = 'td_normal' align = 'center'>Available</td>");
          out.println("<td class = 'td_normal' align = 'center'>-</td>");
        }
        else
        {
          out.println("<td class = 'td_normal' align = 'center'>Out</td>");
          out.println("<td class = 'td_normal' align = 'center'><a href = 'search_records.jsp?item_id=" + item_id +"'><img src = 'images/receipt.png'></td></a>");
        }
        out.println("<td class = 'td_normal' align = 'center'><a class='showAlert' title='View' onclick = 'show_specs(" + item_id + ")'>View</a></td>");  //add View link, when clicked, popup will show with the details of the item. onclick of the link show_specs is called from popup_jquery.js and give it as parameter the item id
        if(authorized_delete)
        {
          out.println("<td class = 'td_normal' align = 'center'><a + onclick = 'delete_item(" + item_id + ")'><img class = 'delete_images_class' src = 'images/delete.png'></td></a>");  //to delete an item. onclick calls delete_item() from items_hq_general.js and takes as argument item_id
        }
        out.println("</tr>");
      }

    }
    catch(Exception e)
    {
      System.out.println(e.toString());
    }
    out.println("</table>");
    out.println("</div>");
    out.println("<p>Total: " + total_items_count + "</p>");
    if(authorized_checkout)
    {
      out.println("<br><button id = 'add_to_cart_button' onclick = 'add_to_cart();'>Add to cart</button>");
    }
    out.close();
  }
}
