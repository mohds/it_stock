package project1;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

public class add_remote_location
  extends HttpServlet
{
  private static final String CONTENT_TYPE = "text/html; charset=windows-1256";

  public void init(ServletConfig config)
    throws ServletException
  {
    super.init(config);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter();
    
    String location = request.getParameter("location");
    Queries.insert_into_table("remote_locations", location);
    
    out.println(location);
    
    Log log = new Log();
    HttpSession session = request.getSession();
    String description = "Added remote location " + location;
    log.log(description, request, session);
    
    out.close();
  }
}
