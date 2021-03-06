package project1;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;

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
    String warranty_start_date = "";
    String warranty_end_date = "";
    DateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    DateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy");
    int days_left_on_warranty = 0;
    
    out.println("<link type='text/css' rel='stylesheet' href='css/items_hq.css'>"); //css link
    out.println("<script src='js/popup_jquery.js'></script>");
    
    String item_id = request.getParameter("item_id"); //get item id from request parameter
    
    //sql_get_general_info: used to query results exclusively from the items table (general information about the item)
    //sql_get_specs: used to query results that will show the values of specs assigned to this item
    //
    String sql_get_general_info  = "SELECT ITEMS.ID, TYPES.NAME, BRANDS.NAME,ITEMS.MODEL, LOCATIONS.NAME, REMOTE_LOCATIONS.NAME, ITEM_CONDITIONS.NAME, ITEMS.LABEL,ITEMS.KEYWORD,ITEMS.SERIAL_NUMBER,ITEMS.NOTES,ITEMS.WARRANTY_START_DATE,ITEMS.WARRANTY_END_DATE, INVOICES.INVOICE_NUMBER, INVOICES.IMAGE FROM ITEMS,TYPES,BRANDS,LOCATIONS,REMOTE_LOCATIONS,ITEM_CONDITIONS,INVOICES WHERE ITEMS.TYPE_ID = TYPES.ID AND ITEMS.BRAND_ID = BRANDS.ID AND ITEMS.LOCATION_ID = LOCATIONS.ID AND ITEMS.CONDITION_ID = ITEM_CONDITIONS.ID AND DELETED = '0' AND ITEMS.CURRENT_LOCATION_ID = REMOTE_LOCATIONS.ID AND ITEMS.INVOICE_FK = INVOICES.ID AND ITEMS.ID = '" + item_id + "'";
    String sql_get_specs = "SELECT SPECS.NAME, ITEMSPECVALUES.VALUE,SPECS.ID FROM SPECS,ITEMSPECVALUES WHERE ITEMSPECVALUES.SPEC_ID = SPECS.ID  AND ITEMSPECVALUES.ITEM_ID = '" + item_id + "'";

    try
    {
      //execute queries
      //
      Statement stat_general_info = con.createStatement();
      ResultSet rs_general_info = stat_general_info.executeQuery(sql_get_general_info); //results of sql_get_general_info
      Statement stat_specs = con.createStatement();
      ResultSet rs_specs = stat_specs.executeQuery(sql_get_specs);  //results of sql_get_specs
      
      boolean authorized_edit = false;  //if authorized to edit items
      
      HttpSession session = request.getSession(); //get sessopm
      Access access = new Access();
      String user = (String)session.getAttribute("username");
      
      String method_edit_item = "edit_item";
      if(access.has_access(user, method_edit_item)) //if logged in user is authorized to edit items
      {
        authorized_edit = true;
      }
      
      out.println("<h3>General Info: </h3>");
      
      //generate general info table
      //
      out.println("<table id = 'table_item_general_info_popup' border = '1'>");
      out.println("<th>Type</th>");
      out.println("<th>Brand</th>");
      out.println("<th>Model</th>");
      out.println("<th>HQ Location</th>");
      out.println("<th>Location</th>");
      out.println("<th>Condition</th>");
      while(rs_general_info.next())
      {
        type = rs_general_info.getString(2);
        
        //if there is a start and end date for warranty
        //
        if(rs_general_info.getString(12) != null && !rs_general_info.getString(12).equals("null") && rs_general_info.getString(13) != null && !rs_general_info.getString(13).equals("null"))
        {
          //parse warranty start date
          //
          warranty_start_date = rs_general_info.getString(12);
          Date start_date;
          start_date = inputFormatter.parse(warranty_start_date);
          warranty_start_date = outputFormatter.format(start_date);
          
          //parse warranty end date
          //
          warranty_end_date = rs_general_info.getString(13);
          Date end_date;
          end_date = inputFormatter.parse(warranty_end_date);
          warranty_end_date = outputFormatter.format(end_date);
          
          days_left_on_warranty = daysBetween(start_date, end_date);
        }
        out.println("<tr>");
        if(authorized_edit)  //if authorized to edit item
        {
          out.println("<td>" + rs_general_info.getString(2) + "</td>"); //item type can't be changed
          
          //in the table, generate select to select item brand and make the user able to select a new brand
          out.println("<td>");
          out.println("<select name = 'popup_select_item_brand' id = 'popup_select_item_brand_id' onmousedown=\"if(this.options.length>8){this.size=0;} sort_select_edit_item_brand()\"  onchange='this.size=0;' onblur=\"this.size=0;\" size = \"0\">");
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
          
          if(rs_general_info.getString(4) != null && !rs_general_info.getString(4).equals("null"))
          {
            out.println("<td><input type = 'text' id = 'popup_model_input_id' value = '" + rs_general_info.getString(4) + "'></td>"); //create an input text element for item label and make the current value the default value
          }
          else
          {
            out.println("<td><input type = 'text' id = 'popup_model_input_id'></td>"); //create an empty input text element for item label
          }

          //in the table, generate select to select item location and make the user able to select a new location
          out.println("<td>");
          out.println("<select name = 'popup_select_item_location' id = 'popup_select_item_location_id' onmousedown=\"if(this.options.length>8){this.size=0;} sort_select_edit_item_location()\"  onchange='this.size=0;' onblur=\"this.size=0;\" size = \"0\">");
          for(int i = 0 ; i < locations_list.size() ; i++)  //loop through all location names
          {
            String option = "<option value = '" + locations_list.get(i) + "'";  //create an option with every location name
            if(locations_list.get(i).equals(rs_general_info.getString(5)))  
            {
              option = option + " selected";  //make the current location name the selected option in the select
            }
            option = option +">" + locations_list.get(i) + "</option>";
            out.println(option);  //add option to the select
          }
          out.println("</select>");
          out.println("<br>");
          out.println("<button id=\"NewLocationButton\" onClick=\"display_new_location_popup()\">New</button>");
          out.println("</td>");
          
          if(rs_general_info.getString(6).equals("0"))
          {
            out.println("<td>" + rs_general_info.getString(5) + "</td>");
          }
          else
          {
            out.println("<td>" + rs_general_info.getString(6) + "</td>");
          }
          
          //in the table, generate select to select item condition and make the user able to select a new condition
          out.println("<td>");
          out.println("<label><select name = 'popup_select_item_condition' id = 'popup_select_item_condition_id' onmousedown=\"if(this.options.length>8){this.size=0;} sort_select_edit_item_condition()\"  onchange='this.size=0;' onblur=\"this.size=0;\" size = \"0\">");
          for(int i = 0 ; i < conditions_list.size() ; i++) //loop through all item condition names
          {
            String option = "<option value = '" + conditions_list.get(i) + "'";   //create an option with every item condition name
            if(conditions_list.get(i).equals(rs_general_info.getString(7)))
            {
              option = option + " selected";  //make the current item condition name the selected option in the select
            }
            option = option +">" + conditions_list.get(i) + "</option>";
            out.println(option);  //add option to the select
          }
          out.println("</select>");
          out.println("</td>");
          
        }
        else  //if not authorized to edit item
        {
          //generate data in table as labels (can't edit)
          //
          out.println("<td>" + rs_general_info.getString(2) + "</td>");
          out.println("<td>" + rs_general_info.getString(3) + "</td>");
          out.println("<td>" + rs_general_info.getString(4) + "</td>");
          out.println("<td>" + rs_general_info.getString(5) + "</td>");
          if(rs_general_info.getString(6).equals("0"))
          {
            out.println("<td>" + rs_general_info.getString(5) + "</td>");
          }
          else
          {
            out.println("<td>" + rs_general_info.getString(6) + "</td>");
          }
          out.println("<td>" + rs_general_info.getString(7) + "</td>");
          out.println("</tr>");
          out.println("</table>");
          out.println("</br>");
          out.println("<p>Notes: " + rs_general_info.getString(11) + ".</p>");
        }
        out.println("</tr>");
        out.println("</table>");
        out.println("</br>");
        
        out.println("<h3>Identification: </h3>");
        out.println("<table id = 'table_item_identification_popup' border = '1'>");
        out.println("<th>ID</th>");
        out.println("<th>Label</th>");
        out.println("<th>Keyword</th>");
        out.println("<th>Serial Number</th>");
        out.println("<tr>");
        if(authorized_edit)
        {
          out.println("<td>" + rs_general_info.getString(1) + "</td>"); //item id can't be changed
          if(rs_general_info.getString(8) != null && !rs_general_info.getString(8).equals("null"))
          {
            out.println("<td><input type = 'text' id = 'popup_label_input_id' value = '" + rs_general_info.getString(8) + "'></td>"); //create an input text element for item label and make the current value the default value
          }
          else
          {
            out.println("<td><input type = 'text' id = 'popup_label_input_id'></td>"); //create an empty input text element for item label
          }
          if(rs_general_info.getString(9) != null && !rs_general_info.getString(9).equals("null"))
          {
            out.println("<td><input type = 'text' id = 'popup_keyword_input_id' value = '" + rs_general_info.getString(9) + "'></td>"); //create an input text element for item label and make the current value the default value
          }
          else
          {
            out.println("<td><input type = 'text' id = 'popup_keyword_input_id'></td>"); //create an empty input text element for item label
          }
          if(rs_general_info.getString(10) != null && !rs_general_info.getString(10).equals("null"))
          {
            out.println("<td><input type = 'text' id = 'popup_sn_input_id' value = '" + rs_general_info.getString(10) + "'></td>");  //create an input text element for item serial number and make the current value the default value
          }
          else
          {
            out.println("<td><input type = 'text' id = 'popup_sn_input_id'></td>");  //create an empty input text element for item serial number
          }
        }
        else
        {
          out.println("<td>" + rs_general_info.getString(1) + "</td>");
          out.println("<td>" + rs_general_info.getString(8) + "</td>");
          out.println("<td>" + rs_general_info.getString(9) + "</td>");
          out.println("<td>" + rs_general_info.getString(10) + "</td>");
        }
        out.println("</tr>");
        out.println("</table>");
        out.println("</br>");
        
        out.println("<div id = 'popup_notes_region'>");
        out.println("<label>Notes: </label> <br>");
        if(rs_general_info.getString(11) != null && !rs_general_info.getString(11).equals("null"))
        {
          out.println("<textarea id = 'popup_notes_input_id' maxlength = '250' rows = '4' cols = '50'>" + rs_general_info.getString(11) + "</textarea>");
        }
        else
        {
          out.println("<textarea id = 'popup_notes_input_id' maxlength = '250' rows = '4' cols = '50'></textarea>");
        }
        out.println("</div>");
          
        
        out.println("<h3>Warranty: </h3>");
        out.println("<div id = 'item_warranty_region>'");
        if(authorized_edit)
        {
            out.println("<label>Invoice Number</label>");  
            if(rs_general_info.getString(14) != null && !rs_general_info.getString(14).equals("null"))
            { 
              out.println("<input type = 'text' id = 'popup_invoice_number_input_id' value = '" + rs_general_info.getString(14) + "'><br><br>");  //create an input text element for item serial number and make the current value the default value
            }
            else
            {
              out.println("<td><input type = 'text' id = 'popup_invoice_number_input_id'></td>");  //create an empty input text element for item serial number
            }
            out.println("<label>Start Date</label>");
            out.println("<input type = 'text' class = 'datepicker' id = 'warranty_start_date_id' value = '" + warranty_start_date + "' readonly = 'true'>");
            out.println("<br><br>");
            out.println("<label>End Date</label>");
            out.println("<input type = 'text' class = 'datepicker' id = 'warranty_end_date_id' value = '" + warranty_end_date + "' readonly = 'true'>");
            out.println("<br><br>");
            if(rs_general_info.getString(15) != null && !rs_general_info.getString(15).equals("null"))
            {
              out.println("<a href = 'display_image_servlet?image=" + rs_general_info.getString(15) + "' target = '_blank'>View invoice image</a><br><br>");
            }
            out.println("<label>Update Invoice Image</label><br>");
            out.println("<input id='invoice_image' type='file'>");
        }
        else
        {
            out.println("<p>Invoice Number: ");
            if(rs_general_info.getString(14) != null && !rs_general_info.getString(14).equals("null")) 
            {
                out.println(rs_general_info.getString(14));
            }
            out.println("</p>");
            out.println("<p>Start Date: " + warranty_start_date + "</p>");
            out.println("<p>End Date: " + warranty_end_date + "</p>");
        }
        out.println("<p>Days still left on warranty: " + days_left_on_warranty + "</p>");
        out.println("</div>");
        
        
        out.println("<div id = 'popup_item_specs'>");
        out.println("<h3>Specifications: </h3>");
        
        //create specs table
        //
        out.println("<table id = 'table_item_specs_popup' border = '1'>");
        out.println("<th>Spec</th>");
        out.println("<th>Value</th>");
        if(authorized_edit)
        {
          out.println("<th>Action</th>");
        }
        while(rs_specs.next())
        {
          out.println("<tr>");
          if(authorized_edit)  //if authorized to edit item
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
        out.println("</div>");
        out.println("<br>");
        
        if(authorized_edit)  //if authorized to edit current specs of an item
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
          
          out.println("<button id = 'popup_add_spec_button' onclick = 'popup_add_spec();'>Add</button>");  //Add button for new specs. onclick calls popup_add_spec() from popup_jquery.js
          out.println("</div>");
          
          out.println("<div id = 'popup_specs_values_region'>");  //div where new specs are added
          out.println("</div>");
        }
        if(authorized_edit)
        {
          out.println("<br><br>");
          out.println("<button onclick = 'update_popup(" + item_id + ")'>Update</button>");  //update button will update all information related to specific item. onclick calls update_popup from popup_jquery.js and takes item_id as argument
          out.println("<br><br>");
        }
        
        String image_name = "";
        String sql_get_image_name = "SELECT ITEMS.IMAGE FROM ITEMS WHERE ITEMS.ID = '" + item_id + "'"; //query to get image name
        try
        {
          Statement stat_get_image_name = con.createStatement();
          ResultSet rs_get_image_name = stat_get_image_name.executeQuery(sql_get_image_name);
          if(rs_get_image_name.next())
          {
            if(!rs_get_image_name.getString(1).equals("") && rs_get_image_name.getString(1) != null)  //if item image is not null
            {
              image_name = rs_get_image_name.getString(1);  //initialize image_name with the item image
            }
            else
            {
              image_name = "no_image.jpg";  //else, we'll display no_image.jpg
            }
          }
        }
        catch(Exception e)
        {
          image_name = "no_image.jpg";
        }
        
        out.println("<div id = 'div_item_image_id'>");  //item image div
        out.println("<div id = 'item_image_id'>");
        out.println("<img src = 'display_image_servlet?image=" + image_name + "'>");
        out.println("</div>");
        
        if(authorized_edit)
        {
          out.println("<div id = 'image_upload_id'>");
          out.println("<input type = 'hidden' id = 'item_id_hidden' name = 'item_id_hidden' value = '" + item_id + "'>");
          out.println("<fieldset>");
          out.println("<label for = 'fileName'>Browse</label>");
          out.println("<input id = 'fileName' type = 'file' name = 'fileName' id = 'fileUploader'/>");
          out.println("</fieldset>");
          out.println("<input type='checkbox' name = 'add_to_all_similar_checkbox' id = 'check_all_similar_checkbox_id'> Add image to all similar items");
          out.println("</div>");
        }
        out.println("</div>");
        
        Log log = new Log();
        String description = "Viewed details of item of ID " + item_id;
        log.log(description, request, session);
      }
      out.close();
    }
    catch(Exception e)
    {
      out.println(e.toString());
    }
  }
  public int daysBetween(Date d1, Date d2)
  {
               return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
       }
}
