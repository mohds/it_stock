package project1;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.*;
import javax.servlet.http.*;

public class delete_item_spec
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
    
    String item_id = request.getParameter("item_id");
    String spec_id = request.getParameter("spec_id");
    
    String sql_delete_item_spec = "DELETE FROM ITEMSPECVALUES WHERE ITEM_ID = '" + item_id + "' AND SPEC_ID = '" + spec_id + "'";
    try
    {
      Statement stat_delete_item_spec = con.createStatement();
      stat_delete_item_spec.executeUpdate(sql_delete_item_spec);
      
      HttpSession session = request.getSession();
      
      Log log = new Log();
      String description = "Deleted spec: " + queries.get_spec_name_from_id(spec_id) + " of item of ID " + item_id;
      log.log(description, request, session);
    }
    catch(Exception e)
    {
      out.println(e.toString());
    }
    out.close();
  }
}
