package project1;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.Statement;

import java.sql.Timestamp;

import java.text.SimpleDateFormat;

import java.util.Date;

import javax.servlet.*;
import javax.servlet.http.*;

public class create_receipt_and_records
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
    
    connect_to_db connect= new connect_to_db();
    Connection con = connect.connect();
    
    db_queries queries = new db_queries();
    String[] records_items_id = null; //contains ids of items in receipt 
    String[] records_expected_date_of_return = null;  //contains each item expected date of return
    String[] records_notes = null;  //contains record notes
    String[] records_returning = null;  //contains each item whether returning or not
    int receipt_id = 0;
    
    String client_name = request.getParameter("client_name");
    String receiver_name = request.getParameter("receiver_name");
    int client_id = queries.get_client_id_from_name(client_name);
    int receiver_id = queries.get_client_id_from_name(receiver_name);
    String receipt_notes = request.getParameter("receipt_notes");
    String current_date_time = request.getParameter("current_date_time");
    String admin_id = request.getParameter("admin_id");
    String global_expected_date = request.getParameter("global_expected_date");
    String receipt_country = request.getParameter("receipt_country");
    
    HttpSession session = request.getSession();
    String user = (String)session.getAttribute("username");
    String email_content = "";
    String email_subject = "IT STOCK - CHECK OUT";
    
    email_content = email_content + "Admin " + user + " has checked out the following item/s to the favor of " + client_name + " and handed to " + receiver_name + ".<br><br>";
    
    
    if(request.getParameterValues("records_items_id[]") != null)
    {
      records_items_id = request.getParameterValues("records_items_id[]");
    }
    
    if(request.getParameterValues("records_expected_dates_of_return[]") != null)
    {
      records_expected_date_of_return = request.getParameterValues("records_expected_dates_of_return[]");
    }
    
    if(request.getParameterValues("records_notes[]") != null)
    {
      records_notes = request.getParameterValues("records_notes[]");
    }
    
    if(request.getParameterValues("records_returning[]") != null)
    {
      records_returning = request.getParameterValues("records_returning[]");
    }
    
    //first create receipt
    //
    //
    String sql_insert_receipt = "INSERT INTO RECEIPTS(CLIENT_ID,RECEIVER_ID, NOTES, STATUS, COUNTRY) VALUES('" + client_id + "','" + receiver_id + "','" + receipt_notes + "','0','" + receipt_country + "')";
    PreparedStatement  stat_receipt;
    String generatedColumns[] = { "ID" };
    try
    {
      stat_receipt = con.prepareStatement(sql_insert_receipt, generatedColumns);
      stat_receipt.executeUpdate();
      ResultSet rs_receipt = stat_receipt.getGeneratedKeys();
      rs_receipt.next();
      receipt_id = rs_receipt.getInt(1);  //get receipt id (just created)
    }
    catch(Exception e)
    {
      out.println(e.toString());
    }
    
    try
    {
      Statement stat_insert_record = con.createStatement();
      Statement stat_get_item_info = con.createStatement();
      
      //Now for each item, create a new record
      //
      //
      
      for(int i = 0 ; i < records_items_id.length ; i++)
      {
        int current_location_id = queries.get_remote_location_id_from_name(receipt_country);
        String sql_update_item_location = "UPDATE ITEMS SET CURRENT_LOCATION_ID = '" + current_location_id + "' WHERE ITEMS.ID = '" + records_items_id[i] + "'";
        Statement stat_update_item_location = con.createStatement();
        stat_update_item_location.executeUpdate(sql_update_item_location);
        
        SimpleDateFormat dateFormat_stamp = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Date parsedDate = dateFormat_stamp.parse(current_date_time);
        Timestamp current_timestamp_temp = new java.sql.Timestamp(parsedDate.getTime());
        String current_timestamp = dateFormat_stamp.format(current_timestamp_temp);
        
        String sql_insert_record = "INSERT INTO RECORDS(BORROW_DATETIME,ADMIN_CHECKER_ID,CLIENT_BORROWER_ID,RECEIPT_ID,ITEM_ID,EXPECTED_DATE,NOTES,RETURNING) VALUES(TO_DATE('" + current_timestamp + "','dd/mm/yyyy hh24:mi:ss'),'" + admin_id + "','" + client_id + "','" + receipt_id + "','" + records_items_id[i] + "',";
        if(!records_expected_date_of_return[i].equals(""))
        {
          String expected_date_of_record = records_expected_date_of_return[i];//dateFormat.format(parsedDate_record);
          sql_insert_record = sql_insert_record + "TO_DATE('" + expected_date_of_record + "','dd-mm-yyyy')";
        }
        else
        {
          
          sql_insert_record = sql_insert_record + "TO_DATE('" + global_expected_date + "','dd-mm-yyyy')";
        }
        sql_insert_record = sql_insert_record + ",'" + records_notes[i] +"','" + records_returning[i] + "')";
        stat_insert_record.executeUpdate(sql_insert_record);
        
        String sql_get_item_info = "SELECT TYPES.NAME, BRANDS.NAME, ITEMS.LABEL, ITEMS.SERIAL_NUMBER FROM ITEMS,TYPES,BRANDS WHERE ITEMS.TYPE_ID = TYPES.ID AND ITEMS.BRAND_ID = BRANDS.ID AND ITEMS.ID = '" + records_items_id[i] + "'";
        ResultSet rs_get_item_info = stat_get_item_info.executeQuery(sql_get_item_info);
        rs_get_item_info.next();
        
        email_content = email_content + "Item ID: " + records_items_id[i] + "<br>";
        
        if(rs_get_item_info.getString(1) != null && !rs_get_item_info.getString(1).equals("null"))
        {
          email_content = email_content + "Type: " + rs_get_item_info.getString(1) + "<br>";
        }
        if(rs_get_item_info.getString(2) != null && !rs_get_item_info.getString(2).equals("null"))
        {
          email_content = email_content + "Brand: " + rs_get_item_info.getString(2) + "<br>";
        }
        if(rs_get_item_info.getString(3) != null && !rs_get_item_info.getString(3).equals("null"))
        {
          email_content = email_content + "Label: " + rs_get_item_info.getString(3) + "<br>";
        }
        if(rs_get_item_info.getString(4) != null && !rs_get_item_info.getString(4).equals("null"))
        {
          email_content = email_content + "Serial Number: " + rs_get_item_info.getString(4) + "<br>";
        }
        email_content = email_content + "<br>";
      }
      if(receipt_country != null && !receipt_country.equals("null") && !receipt_country.equals(""))
      {
        email_content = email_content + "Country: " + receipt_country + "<br><br>";
      }
      if(global_expected_date != null && !global_expected_date.equals("null") && !global_expected_date.equals(""))
      {
        email_content = email_content + "General expected date of items return: " + global_expected_date + "<br><br>";
      }
      email_content = email_content + "Receipt ID: " + receipt_id;
      SendEmail.send_email(email_content, email_subject);

    }
    catch(Exception e)
    {
      System.out.println(e.toString());
    }
    out.close();
    
    Log log = new Log();  //create log
    String description = "Created receipt of ID " + receipt_id + " for " + client_name + " and handed to " + receiver_name;
    log.log(description, request, session);
  }
}
