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
    String[] records_items_id = null; 
    String[] records_expected_date_of_return = null; 
    String[] records_notes = null;
    String[] records_returning = null; 
    int receipt_id = 0;
    
    String client_name = request.getParameter("client_name");
    int client_id = queries.get_client_id_from_name(client_name);
    String receipt_notes = request.getParameter("receipt_notes");
    String current_date_time = request.getParameter("current_date_time");
    String admin_id = request.getParameter("admin_id");
    String global_expected_date = request.getParameter("global_expected_date");
    String receipt_country = request.getParameter("receipt_country");
    
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
    
    String sql_insert_receipt = "INSERT INTO RECEIPTS(CLIENT_ID, NOTES, STATUS, COUNTRY) VALUES('" + client_id + "','" + receipt_notes + "','0','" + receipt_country + "')";
    System.out.println(sql_insert_receipt);
    PreparedStatement  stat_receipt;
    String generatedColumns[] = { "ID" };
    try
    {
      stat_receipt = con.prepareStatement(sql_insert_receipt, generatedColumns);
      stat_receipt.executeUpdate();
      ResultSet rs_receipt = stat_receipt.getGeneratedKeys();
      rs_receipt.next();
      receipt_id = rs_receipt.getInt(1);
    }
    catch(Exception e)
    {
      out.println(e.toString());
    }
    
    try
    {
      Statement stat_insert_record = con.createStatement();
      for(int i = 0 ; i < records_items_id.length ; i++)
      {
        SimpleDateFormat dateFormat_stamp = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Date parsedDate = dateFormat_stamp.parse(current_date_time);
        Timestamp current_timestamp_temp = new java.sql.Timestamp(parsedDate.getTime());
        String current_timestamp = dateFormat_stamp.format(current_timestamp_temp);
        System.out.println(current_timestamp);
        
        //Date parsedDate_global = dateFormat.parse(global_expected_date);
        //Timestamp expected_date_of_record_global_temp = new java.sql.Timestamp(parsedDate_global.getTime());
        String expected_date_of_record_global = global_expected_date;//dateFormat.format(parsedDate_global);
        
        String sql_insert_record = "INSERT INTO RECORDS(BORROW_DATETIME,ADMIN_CHECKER_ID,CLIENT_BORROWER_ID,RECEIPT_ID,ITEM_ID,EXPECTED_DATE,NOTES) VALUES(TO_DATE('" + current_timestamp + "','dd/mm/yyyy hh24:mi:ss'),'" + admin_id + "','" + client_id + "','" + receipt_id + "','" + records_items_id[i] + "',";
        if(!records_expected_date_of_return[i].equals(""))
        {
          //Date parsedDate_record = dateFormat.parse(records_expected_date_of_return[i]);
          //Timestamp expected_date_of_record_temp = new java.sql.Timestamp(parsedDate_record.getTime());
          String expected_date_of_record = records_expected_date_of_return[i];//dateFormat.format(parsedDate_record);
          sql_insert_record = sql_insert_record + "TO_DATE('" + expected_date_of_record + "','dd-mm-yyyy')";
        }
        else
        {
          
          sql_insert_record = sql_insert_record + "TO_DATE('" + expected_date_of_record_global + "','dd-mm-yyyy')";
        }
        sql_insert_record = sql_insert_record + ",'" + records_notes[i] +"')";
        System.out.println(sql_insert_record);
        stat_insert_record.executeUpdate(sql_insert_record);
        
      }
    }
    catch(Exception e)
    {
      System.out.println(e.toString());
    }
    out.close();
  }
}
