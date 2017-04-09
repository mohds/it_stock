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

public class items_hq extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=windows-1256";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
      response.setContentType(CONTENT_TYPE);
      PrintWriter out = response.getWriter();
      
      connect_to_db connect = new connect_to_db();
      Connection con = connect.connect();
      
      
      out.println("<html>");
      out.println("<head><title>items_hq</title>");
      out.println("<link rel='icon' href='images/logo_image.png'></head>");
      request.getRequestDispatcher("nav_bar.html").include(request,response);
      
      //javascript and css links
      //
      //
      
      out.println("<script src='js/get_type_specs.js'></script>");  
      out.println("<script src=js/jquery-1.12.4.js></script>");
      out.println("<script src='js/jquery-ui.js'></script>");
      out.println("<script src='js/items_hq_general.js'></script>");
      out.println("<script src = 'js/jquery-ui-v1.11.4.js'></script>");
      out.println("<script src = 'js/popup_jquery.js'></script>");
      out.println("<link type='text/css' rel='stylesheet' href='css/items_hq.css'>");
      out.println("<link type='text/css' rel='stylesheet' href='css/items_popup.css'>");
      out.println("<link rel = 'stylesheet' href = 'css/jquery-ui.css'>");
      out.println("<link rel = 'stylesheet' href = 'css/main.css'>");
      out.println("<body>");
      
      HttpSession session = request.getSession();
      Access access = new Access();
      String user = (String)session.getAttribute("username");
      String method_search = "view_items_hq";
      if(user == null)
      {
        out.println("<p>Login first >>> <a href='login.jsp'>Login</a></p>");
      }
      else if(!access.has_access(user, method_search))
      {
        out.println("You do not have permission to do that.");
      }
      else
      {
        db_queries queries = new db_queries();
        
        List<String> types_list = queries.get_types_names();  //list of all types names
        
        List<String> brands_list = queries.get_brands_names();  //list of all brands names
        
        List<String> locations_list = queries.get_locations_names();  //list of all locations names
        
        List<String> conditions_list = queries.get_item_conditions(); //list of all item conditions names
        
        boolean authorized_checkout = false;
        
        out.println("<div id=\"search_items_container\">"); // div to wrap page's panels        
        
        String method_checkout = "checkout_item";
        if(access.has_access(user, method_checkout))
        {
          authorized_checkout = true;
        }
        
        out.println("<div class='MainPanel' id='search_input_and_buttons'>"); // search_input_and_button
        out.println("<div id = 'sticky'>"); //STICKY
        out.println("<h2>Search for an item</h2>");
        
        out.println("<label>Item ID: </label><input class = 'search_inputs' type = 'text' id = 'input_item_id'>");  //input text element for item ID
        out.println("<br><br>");
        
        //Select element for type
        //onchange, call type_value() from get_type_specs.js
        //once type is selected, a select element containing relevant specs of selected type is generated along with a 'Add' Button
        
        out.println("<label>Type: </label><select class = 'search_inputs' name = 'select_item_type' id = 'select_item_type_id' onchange = 'type_value()' onmousedown=\"if(this.options.length>8){this.size=0;} sort_select_hq_type()\"  onchange='this.size=0;' onblur=\"this.size=0;\" size = \"0\">");
        out.println("<option value = 'any'>Any</option>");
        for(int i = 0 ; i < types_list.size() ; i++)
        {
          out.println("<option value = '" + types_list.get(i) + "'>" + types_list.get(i) + "</option>");
        }
        out.println("</select>");
        
        out.println("<br><br>");
        
        //Select element for brand
        //
        //
        String test = "select_item_brand_id";
        out.println("<label>Brand: </label><select class = 'search_inputs' name = 'select_item_brand' id = 'select_item_brand_id' onmousedown=\"if(this.options.length>8){this.size=0; } sort_select_hq_brand()\"  onchange='this.size=0;' onblur=\"this.size=0;\" size = \"0\">");
        out.println("<option value = 'any'>Any</option>");
        for(int i = 0 ; i < brands_list.size() ; i++)
        {
          out.println("<option value = '" + brands_list.get(i) + "'>" + brands_list.get(i) + "</option>");
        }
        out.println("</select>");
        
        out.println("<br><br>");
      
        //Select element for location
        //
        //
        
        out.println("<label>Location: </label><select class = 'search_inputs' name = 'select_item_location' id = 'select_item_location_id' onmousedown=\"if(this.options.length>8){this.size=0;} sort_select_hq_location()\"  onchange='this.size=0;' onblur=\"this.size=0;\" size = \"0\">");
        out.println("<option value = 'any'>Any</option>");
        for(int i = 0 ; i < locations_list.size() ; i++)
        {
          out.println("<option value = '" + locations_list.get(i) + "'>" + locations_list.get(i) + "</option>");
        }
        out.println("</select>");
        
        out.println("<br><br>");
        
        //Select element for condition
        //
        //
        
        out.println("<label>Condition: </label><select class = 'search_inputs' name = 'select_item_condition' id = 'select_item_condition_id' onmousedown=\"if(this.options.length>8){this.size=0;} sort_select_hq_condition()\"  onchange='this.size=0;' onblur=\"this.size=0;\" size = \"0\">");
        out.println("<option value = 'any'>Any</option>");
        for(int i = 0 ; i < conditions_list.size() ; i++)
        {
          out.println("<option value = '" + conditions_list.get(i) + "'>" + conditions_list.get(i) + "</option>");
        }
        out.println("</select>");
        
        out.println("<br><br>");
        
        out.println("<label>Label: </label><input class = 'search_inputs' type = 'text' id = 'input_item_label'>"); //input text element for label
        
        out.println("<br><br>");
        
        out.println("<label>Serial Number: </label><input class = 'search_inputs' type = 'text' id = 'input_item_sn'>");  //input text element for serial number
        
        out.println("<br><br>");
        
        out.println("<label>Model: </label><input class = 'search_inputs' type = 'text' id = 'input_item_model'>");  //input text element for serial number
        
        out.println("<br><br>");
        
        out.println("<label>Keyword: </label><input class = 'search_inputs' type = 'text' id = 'input_item_keyword'>");  //input text element for serial number
      
        out.println("<br><br>");   

        //radio buttons to choose availability option (all/only available items, only out items
        //
        //
        
        out.println("<div id = 'div_availability_radios'>");  //radios
        out.println("<label>Availability: </label>");
        out.println("<input type = 'radio' name = 'availability_option' id = 'availability_option_all_id' value = 'all' checked>All");
        out.println("<input type = 'radio' name = 'availability_option' id = 'availability_option_available_id' value = 'available'>Available");
        out.println("<input type = 'radio' name = 'availability_option' id = 'availability_option_out_id' value = 'out'>Out");
        out.println("</div>");  //radios
        
        out.println("<h3 id = 'h3_specs_values_region'>Specs</h3>");
        
        out.println("<div id = specs_region>"); //div for the select element that contains the specs of selected type
        out.println("</div>");  //select element that contains specs of element
        
        out.println("<div id = 'specs_values_region'>");  //div for the added specs input text elements
        out.println("</div>");  //div for the added specs input text elements
        
        out.println("</div>");  //sticky
        out.println("<br>"); // nazel el buttons
        out.println("<div id = 'search_buttons'>");
        out.println("<button id = 'search_button' onclick = 'send_specs();'>Search</button>"); //search button calls send_specs() from items_hq_general.js  
        out.println("<button id = 'reset_button' onclick = 'reset_search_options();'>Reset</button>"); 
        out.println("</div>");
        
        out.println("<br>"); // nazel el cart
          
        if(authorized_checkout)
        {
            out.println("<div id = 'items_in_cart_region'>"); //items in cart
            out.println("<h3>Items in cart</h3>");
            out.println("<table border = '1' id = 'items_in_cart_table'>");
            out.println("<th>Item ID</th>");
            out.println("<th>Type</th>");
            out.println("<th>Action</th>");
            out.println("</table>");
            out.println("<br><button id = 'checkout_button' onclick = 'show_receipt();'>Checkout</button>");
            out.println("</div>");  //items in cart
        }
          
        out.println("</div>"); // search_input_and_buttons (search items input and buttons wrap)
        
        
        
        //out.println("<h3 id = 'h3_search_results_id'>Search Results</h3><br>");
        
        out.println("<div class='MainPanel' id = 'results_region'>"); //results table
        out.println("<div id = 'results_table_div'>");
        out.println("<table class=\"fixed_headers\" id = 'items_search_results_table'>");
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
        out.println("</table>");
        out.println("</div>");  
        out.println("</div>");  //results table
        out.println("</div>");  // search_items_container (panels wrapping div)
        
        out.println("<div id = 'dialog' title = 'Specs'>"); //IMPORTANT FOR JQUERY POPUP
        out.println("<div id = 'specs_content_region' title = 'Specs'>"); //FOR POPUP CONTENT
        out.println("</div>");
        out.println("<div id = 'receipt_content_region' title = 'Receipt'>"); //FOR POPUP CONTENT
        out.println("</div>");
        out.println("</div>");
        
        ///////////// new client handler //////////////    
        out.println("<div id=\"NewClientPopup\" title=\"New Client\">");
        out.println("<label>Name:</label><br>");
        out.println("<input type=\"text\" value=\"\" id=\"NewClientName\"><br>");
        out.println("<button id=\"SaveNewClientButton\" onClick=\"save_new_client()\">Save</button>");
        out.println("</div>");
        
        out.println("<div id=\"NewLocationPopup\" title=\"New Location\">");
        out.println("<label>Name:</label><br>");
        out.println("<input type=\"text\" value=\"\" id=\"NewLocationName\"><br>");
        out.println("<button id=\"SaveNewLocationtButton\" onClick=\"save_new_location()\">Save</button>");
        out.println("</div>");
        
        out.println("<div id=\"NewRemoteLocationPopup\" title=\"New Remote Location\">");
        out.println("<label>Name:</label><br>");
        out.println("<input type=\"text\" value=\"\" id=\"NewRemoteLocationName\"><br>");
        out.println("<button id=\"SaveNewRemoteLocationtButton\" onClick=\"save_new_remote_location()\">Save</button>");
        out.println("</div>");
        
        out.println("</body></html>");
        out.close();
      }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>items_hq</title></head>");
        out.println("<body>");
        out.println("<p>The servlet has received a POST. This is the reply.</p>");
        out.println("</body></html>");
        out.close();
    }
                
}

