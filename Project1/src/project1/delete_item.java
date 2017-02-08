package project1;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;

import java.sql.Statement;

import javax.servlet.*;
import javax.servlet.http.*;

public class delete_item
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
    
    String item_id = request.getParameter("item_id");
    
    String sql_delete_item = "UPDATE  ITEMS SET DELETED = '1' WHERE ID = '" + item_id + "'";
    try
    {
      Statement stat = con.createStatement();
      stat.executeUpdate(sql_delete_item);
    }
    catch(Exception e)
    {
      out.println(e.toString());
    }
    
    out.close();
  }
}
