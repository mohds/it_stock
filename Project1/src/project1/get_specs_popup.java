package project1;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;

import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

public class get_specs_popup
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
    response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter();
    
    connect_to_db connect = new connect_to_db();
    Connection con = connect.connect();
    
    db_queries queries = new db_queries();
    
    String type = ""; //will store type name (to be used in case we wanted to add new specs to the item from the popup
    List<String> brands_list = queries.get_brands_names();  //list containing all brand names
    List<String> locations_list = queries.get_locations_names(); //list containing all location names
    List<String> conditions_list = queries.get_item_conditions(); //list containing all item condition names
    
    out.println("<link type='text/css' rel='stylesheet' href='css/items_hq.css'>"); //css link
    
    String item_id = request.getParameter("item_id"); //get item id from request parameter
    
    //sql_get_general_info: used to query results exclusively from the items table (general information about the item)
    //sql_get_specs: used to query results that will show the values of specs assigned to this item
    //
    String sql_get_general_info  = "SELECT ITEMS.ID,TYPES.NAME AS Type,BRANDS.NAME AS Brand,LOCATIONS.NAME AS Location,ITEM_CONDITIONS.NAME AS Condition,ITEMS.LABEL,ITEMS.SERIAL_NUMBER,ITEMS.NOTES FROM ITEMS,TYPES,BRANDS,LOCATIONS,ITEM_CONDITIONS WHERE ITEMS.TYPE_ID = TYPES.ID AND ITEMS.BRAND_ID = BRANDS.ID AND ITEMS.LOCATION_ID = LOCATIONS.ID AND ITEMS.CONDITION_ID = ITEM_CONDITIONS.ID AND DELETED = '0' AND ITEMS.ID = '" + item_id + "'";
    String sql_get_specs = "SELECT SPECS.NAME, ITEMSPECVALUES.VALUE,SPECS.ID FROM SPECS,ITEMSPECVALUES WHERE ITEMSPECVALUES.SPEC_ID = SPECS.ID  AND ITEMSPECVALUES.ITEM_ID = '" + item_id + "'";
    
    try
    {
      //execute queries
      //
      Statement stat_general_info = con.createStatement();
      ResultSet rs_general_info = stat_general_info.executeQuery(sql_get_general_info);
      Statement stat_specs = con.createStatement();
      ResultSet rs_specs = stat_specs.executeQuery(sql_get_specs);
      
      boolean authorized = true;  //if authorized to edit items
      boolean authorized_add = true;  //if authorized to add new specs
      
      out.println("<h3>General Info: </h3>");
      
      //generate general info table
      //
      out.println("<table border = '1'>");
      out.println("<th>ID</th>");
      out.println("<th>Type</th>");
      out.println("<th>Brand</th>");
      out.println("<th>Location</th>");
      out.println("<th>Condition</th>");
      out.println("<th>Label</th>");
      out.println("<th>Serial Number</th>");
      out.println("<th>Notes</th>");
      while(rs_general_info.next())
      {
        type = rs_general_info.getString(2);
        out.println("<tr>");
        if(authorized)  //if authorized to edit item
        {
          out.println("<td>" + rs_general_info.getString(1) + "</td>"); //item id can't be changed
          out.println("<td>" + rs_general_info.getString(2) + "</td>"); //item type can't be changed
          
          //in the table, generate select to select item brand and make the user able to select a new brand
          out.println("<td>");
          out.println("<select name = 'popup_select_item_brand' id = 'popup_select_item_brand_id' onmousedown=\"if(this.options.length>8){this.size=0;}\"  onchange='this.size=0;' onblur=\"this.size=0;\" size = \"0\">");
          for(int i = 0 ; i < brands_list.size() ; i++) //loop through all brand names
          {
            String option = "<option value = '" + brands_list.get(i) + "'";   //create an option with every brand name
            if(brands_list.get(i).equals(rs_general_info.getString(3))) 
            {
              option = option + " selected";  //make the current brand name the selected option in the select
            }
            option = option +">" + brands_list.get(i) + "</option>";  
            out.println(option);  //add option to the select
          }
          out.println("</select>");
          out.println("</td>");
          
          //in the table, generate select to select item location and make the user able to select a new location
          out.println("<td>");
          out.println("<select name = 'popup_select_item_location' id = 'popup_select_item_location_id' onmousedown=\"if(this.options.length>8){this.size=0;}\"  onchange='this.size=0;' onblur=\"this.size=0;\" size = \"0\">");
          for(int i = 0 ; i < locations_list.size() ; i++)  //loop through all location names
          {
            String option = "<option value = '" + locations_list.get(i) + "'";  //create an option with every location name
            if(locations_list.get(i).equals(rs_general_info.getString(4)))  
            {
              option = option + " selected";  //make the current location name the selected option in the select
            }
            option = option +">" + locations_list.get(i) + "</option>";
            out.println(option);  //add option to the select
          }
          out.println("</select>");
          out.println("</td>");
          
          //in the table, generate select to select item condition and make the user able to select a new condition
          out.println("<td>");
          out.println("<label><select name = 'popup_select_item_condition' id = 'popup_select_item_condition_id' onmousedown=\"if(this.options.length>8){this.size=0;}\"  onchange='this.size=0;' onblur=\"this.size=0;\" size = \"0\">");
          for(int i = 0 ; i < conditions_list.size() ; i++) //loop through all item condition names
          {
            String option = "<option value = '" + conditions_list.get(i) + "'";   //create an option with every item condition name
            if(conditions_list.get(i).equals(rs_general_info.getString(5)))
            {
              option = option + " selected";  //make the current item condition name the selected option in the select
            }
            option = option +">" + conditions_list.get(i) + "</option>";
            out.println(option);  //add option to the select
          }
          out.println("</select>");
          out.println("</td>");
          
          out.println("<td><input type = 'text' id = 'popup_label_input_id' value = '" + rs_general_info.getString(6) + "'></td>"); //create an input text element for item label and make the current value the default value
          out.println("<td><input type = 'text' id = 'popup_sn_input_id' value = '" + rs_general_info.getString(7) + "'></td>");  //create an input text element for item serial number and make the current value the default value
          out.println("<td><input type = 'text' id = 'popup_notes_input_id' value = '" + rs_general_info.getString(8) + "'></td>"); //create an input text element for item notes and make the current value the default value
        }
        else  //if not authorized to edit item
        {
          //generate data in table as labels (can't edit)
          //
          out.println("<td>" + rs_general_info.getString(1) + "</td>");
          out.println("<td>" + rs_general_info.getString(2) + "</td>");
          out.println("<td>" + rs_general_info.getString(3) + "</td>");
          out.println("<td>" + rs_general_info.getString(4) + "</td>");
          out.println("<td>" + rs_general_info.getString(5) + "</td>");
          out.println("<td>" + rs_general_info.getString(6) + "</td>");
          out.println("<td>" + rs_general_info.getString(7) + "</td>");
          out.println("<td>" + rs_general_info.getString(8) + "</td>");
        }
        out.println("</tr>");
      }
      
      out.println("</table>");
      
      out.println("<h3>Specifications: </h3>");
      
      //create specs table
      //
      out.println("<table border = '1'>");
      out.println("<th>Spec</th>");
      out.println("<th>Value</th>");
      if(authorized)
      {
        out.println("<th>Action</th>");
      }
      while(rs_specs.next())
      {
        out.println("<tr>");
        if(authorized)  //if authorized to edit item
        {
          if(!rs_specs.getString(2).equals(""))
          {
            out.println("<td>" + rs_specs.getString(1) + "</td>");  //first column: spec name
            out.println("<td><input type = 'text' class = 'popup_spec_inputs' id = 'popup_spec_" + rs_specs.getString(1) + "_input ' value = '" + rs_specs.getString(2) + "'></td>"); //second column: spec value as an input text element with the current value as default value
            out.println("<td><img src = 'images/remove.png' onclick = 'delete_item_spec(" + item_id + "," + rs_specs.getString(3) + ")'></td>");
          }
        }
        else  //if not authroized to edit item
        {
          //generate data as labels(can't edit)
          //
          if(!rs_specs.getString(2).equals(""))
          {
            out.println("<td>" + rs_specs.getString(1) + "</td>");
            out.println("<td>" + rs_specs.getString(2) + "</td>");
          }
        }
        out.println("</tr>");
      }
      
      out.println("</table>");
      if(authorized_add)  //if authorized to add new specs to an item
      {
        List<String> item_specs_list = queries.get_type_specs_for_item(type,item_id);  //list contains specs relevant to item type
        
        out.println("<div id = 'div_add_new_specs_popup'>");
        
        //create select element containing specs relevant to item type
        //
        //
        out.println("<label>Add new specs: </label><select name = 'select_item_specs_popup' id = 'select_item_specs_popup_id' onmousedown=\"if(this.options.length>8){this.size=0;}\"  onchange='this.size=0;' onblur=\"this.size=0;\" size = \"0\">");
        out.println("<option value = 'Please select'>Please select</option>");
        for(int i = 0 ; i < item_specs_list.size() ; i++)
        {
          out.println("<option value = '" + item_specs_list.get(i) + "'>" + item_specs_list.get(i) + "</option>");
        }
        out.println("</select>");
        
        out.println("<input id = 'popup_add_spec_button' type = 'button' value = 'Add' onclick = 'popup_add_spec();' />");  //Add button for new specs. onclick calls popup_add_spec() from popup_jquery.js
        out.println("</div>");
        
        out.println("<div id = 'popup_specs_values_region'>");  //div where new specs are added
        out.println("</div>");
      }
      if(authorized)
      {
        out.println("<br>");
        out.println("<input type = 'button' value = 'Update' onclick = 'update_popup(" + item_id + ")'>");  //update button will update all information related to specific item. onclick calls update_popup from popup_jquery.js and takes item_id as argument
      }
    }
    catch(Exception e)
    {
      out.println(e.toString());
    }
    out.close();
  }
}
