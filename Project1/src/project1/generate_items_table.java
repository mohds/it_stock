package project1;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

// @WebServlet(name = "generate_items_table", urlPatterns = { "/generate_items_table" })
public class generate_items_table
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
    String[] specs_names = request.getParameterValues("specs_names[]");
    String[] specs_values = request.getParameterValues("specs_values[]");
    for(int i = 0 ; i < specs_names.length ; i++)
    {
      System.out.println("Name: " + specs_names[i] + " ----- Value: " + specs_values[i]);
    }
    out.close();
  }
}
