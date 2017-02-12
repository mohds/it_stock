package project1;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;

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
    
    String[] ids_array = null;
    
    List<String> list_ids = new ArrayList<String>();
    
    connect_to_db connect = new connect_to_db();
    Connection con = connect.connect();
    
    out.println("<script src='js/popup_jquery.js'></script>");
    
    db_queries queries = new db_queries();
    
    List<String> clients_list = queries.get_clients_names();
    
    if(request.getParameterValues("ids_array[]") != null)
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
    
    String admin = "Wassim El Ahmar - ãÌáÓ ÇáäæÇÈ ";
    String admin_id = "1";
    String current_date = "";
    String current_time = "";
    
    Calendar c = Calendar.getInstance();
    Date today_date = c.getTime();
    String today = String.valueOf(c.get(Calendar.DATE) + "-" + String.valueOf(today_date.getMonth() + 1) + "-" + String.valueOf(c.get(Calendar.YEAR)));
    current_date = today;
    
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    
    current_time = sdf.format(c.getTime());
    
    String current_date_time = current_date + " " + current_time;
    
    out.println("<input type = 'hidden' id = 'current_date_time_id' value = '" + current_date_time +"'>");
    out.println("<input type = 'hidden' id = 'admin_id_id' value = '" + admin_id +"'>");
    
    out.println("<h3>Items Checkout</h3>");
    
    out.println("<label>Client: </label><select name = 'receipt_client_name' id = 'receipt_client_name_id' onmousedown=\"if(this.options.length>8){this.size=0;}\"  onchange='this.size=0;' onblur=\"this.size=0;\" size = \"0\">");
    for(int i = 0 ; i < clients_list.size() ; i++)
    {
      out.println("<option value = '" + clients_list.get(i) + "'>" + clients_list.get(i) + "</option>");
    }
    out.println("</select>");
    
    
    out.println("<br><br>");
    
    out.println("<label>Received by: </label><select name = 'receipt_receiver_name' id = 'receipt_receiver_name_id' onmousedown=\"if(this.options.length>8){this.size=0;}\"  onchange='this.size=0;' onblur=\"this.size=0;\" size = \"0\">");
    for(int i = 0 ; i < clients_list.size() ; i++)
    {
      out.println("<option value = '" + clients_list.get(i) + "'>" + clients_list.get(i) + "</option>");
    }
    out.println("</select>");
    
    
    out.println("<br><br>");
    
    out.println("<label>Admin: " + admin +"</label>");
    
    out.println("<br><br>");
    
    out.println("<label>Borrow Date: " + current_date +"</label>");
    
    out.println("<br><br>");
    
    out.println("<label>Borrow Time: " + current_time +"</label>");
    
    out.println("<br><br>");
    
    out.println("<label>Expected date of Return: </label>");
    out.println("<input type = 'text' class = 'datepicker' id = 'global_expected_date_id' readonly = 'true'>");
    out.println("<input type = 'button' value = 'Set all' onclick = 'set_expected_return_date()'>");
    out.println("<br><br>");
    
    out.println("<label>Country: </label>");
    out.println("<input type = 'text' id = 'receipt_country_id'>");
    out.println("<br><br>");
    
    out.println("<table border = '1'>");
    out.println("<th>Item ID</th>");
    out.println("<th>Expected date of return</th>");
    out.println("<th>Notes</th>");
    out.println("<th>Will be returned?</th>");
    for(int i = 0 ; i < list_ids.size() ; i++)
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
    
    out.println("<input type = 'button' value = 'Check Out' onclick = create_receipt()>");
    
    out.close();
  }
}
