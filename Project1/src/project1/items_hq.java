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

// @WebServlet(name = "items_hq", urlPatterns = { "/items_hq" })
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
      out.println("<head><title>items_hq</title></head>");
      
      out.println("<script src='js/get_type_specs.js'></script>");
      out.println("<script src=js/jquery-1.12.4.js></script>");
      out.println("<script src='js/jquery-ui.js'></script>");
      out.println("<script src='js/items_hq_general.js'></script>");
      out.println("<link type='text/css' rel='stylesheet' href='css/items_hq.css'>");
      
      out.println("<body>");
      db_queries queries = new db_queries();
      
      List<String> types_list = queries.get_types_names();
      
      List<String> brands_list = queries.get_brands_names();
      
      List<String> locations_list = queries.get_locations_names();
      
      List<String> conditions_list = queries.get_item_conditions();
      
      out.println("<h2>Search for an item</h2>");
      out.println("<br><br>");
      
      
      out.println("<label>Type: </label><select name = 'select_item_type' id = 'select_item_type_id' onchange = 'type_value()' onmousedown=\"if(this.options.length>8){this.size=0;}\"  onchange='this.size=0;' onblur=\"this.size=0;\" size = \"0\">");
      out.println("<option value = 'any'>Any</option>");
      for(int i = 0 ; i < types_list.size() ; i++)
      {
        out.println("<option value = '" + types_list.get(i) + "'>" + types_list.get(i) + "</option>");
      }
      out.println("</select>");
      
      out.println("<br><br>");
      
      out.println("<label>Brand: </label><select name = 'select_item_brand' id = 'select_item_brand_id' onmousedown=\"if(this.options.length>8){this.size=0;}\"  onchange='this.size=0;' onblur=\"this.size=0;\" size = \"0\">");
      out.println("<option value = 'any'>Any</option>");
      for(int i = 0 ; i < brands_list.size() ; i++)
      {
        out.println("<option value = '" + brands_list.get(i) + "'>" + brands_list.get(i) + "</option>");
      }
      out.println("</select>");
      
      out.println("<br><br>");
    
      out.println("<label>Location: </label><select name = 'select_item_location' id = 'select_item_location_id' onmousedown=\"if(this.options.length>8){this.size=0;}\"  onchange='this.size=0;' onblur=\"this.size=0;\" size = \"0\">");
      out.println("<option value = 'any'>Any</option>");
      for(int i = 0 ; i < locations_list.size() ; i++)
      {
        out.println("<option value = '" + locations_list.get(i) + "'>" + locations_list.get(i) + "</option>");
      }
      out.println("</select>");
      
      out.println("<br><br>");
      
      out.println("<label>Condition: </label><select name = 'select_item_condition' id = 'select_item_condition_id' onmousedown=\"if(this.options.length>8){this.size=0;}\"  onchange='this.size=0;' onblur=\"this.size=0;\" size = \"0\">");
      out.println("<option value = 'any'>Any</option>");
      for(int i = 0 ; i < conditions_list.size() ; i++)
      {
        out.println("<option value = '" + conditions_list.get(i) + "'>" + conditions_list.get(i) + "</option>");
      }
      out.println("</select>");
      
      out.println("<br><br>");
      
      out.println("<label>Label: </label><input type = 'text' id = 'input_item_label'>");
      
      out.println("<br><br>");
      
      out.println("<label>Serial Number: </label><input type = 'text' id = 'input_item_sn'>");
    
      out.println("<br><br>");   
      
      out.println("<h3>Search Options</h3>");
      
      out.println("<div id = 'div_availability_radios'>");
      out.println("<label>Availability: </label>");
      out.println("<input type = 'radio' name = 'availability_option' id = 'availability_option_all_id' value = 'all' checked>All");
      out.println("<input type = 'radio' name = 'availability_option' id = 'availability_option_available_id' value = 'available'>Available");
      out.println("<input type = 'radio' name = 'availability_option' id = 'availability_option_out_id' value = 'out'>Out");
      out.println("</div>");
      
      out.println("<br><br>");
      
      out.println("<div id = specs_region>");
      out.println("</div>");
      
      out.println("<br><input id = 'search_button' type = 'button' value = 'Search' onclick = 'send_specs();' />");
    
      out.println("<h3 id = 'h3_specs_values_region'>Specs</h3><br>");
      
      out.println("<div id = 'specs_values_region'>");
      out.println("</div>");
      
      out.println("</body></html>");
      out.close();
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

