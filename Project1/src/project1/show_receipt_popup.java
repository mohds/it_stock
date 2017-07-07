package project1;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

public class show_receipt_popup
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
    Connection con = connect.connect(); //connection object used to query database
    
    String[] ids_array = null;  //array of item ids in cart
    
    List<String> list_ids = new ArrayList<String>();  //list of item ids in cart
    
    out.println("<script src='js/popup_jquery.js'></script>");
    
    db_queries queries = new db_queries();
    
    List<String> clients_list = queries.get_clients_names();  //list contains all clients names
    List<String> remote_locations_list = queries.get_remote_locations();  //list contains all clients names
    
    if(request.getParameterValues("ids_array[]") != null) //create the list of item ids in cart from the array WITHOUT null or empty array entries
    {
      ids_array = request.getParameterValues("ids_array[]");
      for(int k = 0 ; k < ids_array.length ; k++)
      {
        if(!ids_array[k].equals(""))
        {
          list_ids.add(ids_array[k]);
        }
      }
    }
    
    HttpSession session = request.getSession(); //used to get current session information
    
    String admin = (String)session.getAttribute("username");  //ex: w.elahmar
    String admin_id = (String)session.getAttribute("id"); //ex: 32
    String admin_full_name = "";
    String current_date = "";
    String current_time = "";
    
    String sql_admin_full_name = "SELECT ADMINS.NAME FROM ADMINS WHERE ADMINS.ID = '" + admin_id +"'";  //used to get the full name of the logged in admin using the session username
    try
    {
      Statement stat_admin_full_name = con.createStatement();
      ResultSet rs_admin_full_name = stat_admin_full_name.executeQuery(sql_admin_full_name);
      rs_admin_full_name.next();
      admin_full_name = rs_admin_full_name.getString(1);
    }
    catch(Exception e)
    {
      out.println(e.toString());
    }
    
    
    Calendar c = Calendar.getInstance();  //calendar used to get current date and time
    Date today_date = c.getTime();  //date object
    
    //below we build the current date in the format dd/MM/yyy from the calendar object and the date object
    //
    
    String today = String.valueOf(c.get(Calendar.DATE) + "-" + String.valueOf(today_date.getMonth() + 1) + "-" + String.valueOf(c.get(Calendar.YEAR)));
    current_date = today;
    
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");  //dateformat used to get time in the format HH:mm:ss
    
    current_time = sdf.format(c.getTime()); //current time
    
    String current_date_time = current_date + " " + current_time; //final date and time string
    
    out.println("<input type = 'hidden' id = 'current_date_time_id' value = '" + current_date_time +"'>");  //used to send parameter current_date_time
    out.println("<input type = 'hidden' id = 'admin_id_id' value = '" + admin_id +"'>");  //used to send parameter admin_id
    out.println("<input type = 'hidden' id = 'admin_name_id' value = '" + admin +"'>"); //used to send parameter admin name
    
    
    //below we will show the receipt information
    //
    //
    out.println("<h3>Items Checkout</h3>");
    
    out.println("<label class = 'class_asterisks'>*  </label><label>Client: </label><select name = 'receipt_client_name' id = 'receipt_client_name_id' onmousedown=\"if(this.options.length>8){this.size=0;} sort_select_receipt_client()\"  onchange='this.size=0;' onblur=\"this.size=0;\" size = \"0\">");
    out.println("<option value = ''></option>");
    for(int i = 0 ; i < clients_list.size() ; i++)
    {
      out.println("<option value = '" + clients_list.get(i) + "'>" + clients_list.get(i) + "</option>");
    }
    out.println("</select>");
    
    ///////////// new client handler //////////////
    out.println("<button id=\"NewClientButton\" onClick=\"display_new_client_popup()\">New</button>");  //button that gives the ability to add a new client
    
    out.println("<br><br>");
    
    out.println("<label class = 'class_asterisks'>*  </label><label>Handed to: </label><select name = 'receipt_receiver_name' id = 'receipt_receiver_name_id' onmousedown=\"if(this.options.length>8){this.size=0;} sort_select_receipt_receiver()\"  onchange='this.size=0;' onblur=\"this.size=0;\" size = \"0\">");
    out.println("<option value = ''></option>");
    for(int i = 0 ; i < clients_list.size() ; i++)
    {
      out.println("<option value = '" + clients_list.get(i) + "'>" + clients_list.get(i) + "</option>");
    }
    out.println("</select>");
    
    
    out.println("<br><br>");
    
    out.println("<label>Admin: " + admin_full_name +"</label>");
    
    out.println("<br><br>");
    
    out.println("<label>Borrow Date: " + current_date +"</label>");
    
    out.println("<br><br>");
    
    out.println("<label>Borrow Time: " + current_time +"</label>");
    
    out.println("<br><br>");
    
    out.println("<label>Expected date of Return: </label>");
    out.println("<input type = 'text' class = 'datepicker' id = 'global_expected_date_id' readonly = 'true'>");
    out.println("<button onclick = 'set_expected_return_date()'>Set all</button>");  //calls set_expected_return_date that sets the return date of all items in receipt to the global expected return date
    out.println("<br><br>");
    
    out.println("<label class = 'class_asterisks'>*  </label><label>Country: </label>");
    out.println("<select name = 'receipt_location_name' id = 'receipt_location_id' onmousedown=\"if(this.options.length>8){this.size=0;} sort_select_receipt_country()\"  onchange='this.size=0;' onblur=\"this.size=0;\" size = \"0\">");
    out.println("<option value = ''></option>");
    for(int i = 0 ; i < remote_locations_list.size() ; i++)
    {
      out.println("<option value = '" + remote_locations_list.get(i) + "'>" + remote_locations_list.get(i) + "</option>");
    }
    out.println("</select>");
    out.println("<button id=\"NewRemoteLocationButton\" onClick=\"display_new_remote_location_popup()\">New</button>"); //button that gives the ability to add a new remote location
    out.println("<br><br>");
    
    out.println("<table id = 'receipt_table_id'>");  //generate table of records
    out.println("<th>Item ID</th>");
    out.println("<th>Expected date of return</th>");
    out.println("<th>Notes</th>");
    out.println("<th>Will be returned?</th>");
    
    for(int i = 0 ; i < list_ids.size() ; i++)  //for every item, add a record in the records table
    {
      out.println("<tr>");
      out.println("<td class = 'item_ids_class' id = 'item_" + list_ids.get(i) + "_id'>" + list_ids.get(i) + "</td>");
      out.println("<td><input type = 'text' class = 'records_expected_date_class datepicker' id = 'record_" + list_ids.get(i) + "_expected_date' readonly = 'true'></td>");
      out.println("<td><input type = 'text' class = 'records_notes_class' id = 'record_" + list_ids.get(i) + "_notes'></td>");
      out.println("<td>");
      out.println("<select class = 'records_returning_class' id = 'record_returning_select' onmousedown='if(this.options.length>8){this.size=0;}'  onchange='this.size=0;' onblur='this.size=0;' size = '0'>");
      out.println("<option value = '' ></option>");
      out.println("<option value = '1' selected>Yes</option>");
      out.println("<option value = '0'>No</option>");
      out.println("</select>");
      out.println("</td>");
      out.println("</tr>");
    }
    out.println("</table>");
    
    out.println("<br>");
    
    out.println("<label>Receipt notes: </label>");
    
    out.println("<br>");
    
    out.println("<textarea rows = '3' cols = '30' id = 'receipt_notes_id'></textarea>");
    
    out.println("<br><br>");
    
    out.println("<button onclick = create_receipt()>Check out</button>");
    out.println("<input type='checkbox' id = 'print_checkbox_id'> Print receipt<br>");
    
    
        
    out.close();
    
    Log log = new Log();  //for every added record, create a log
    
    for(int i = 0 ; i < list_ids.size() ; i++)
    {
      String description = "Started checkout process of item of ID: " + list_ids.get(i);
      log.log(description, request, session);
    }
  }
}
