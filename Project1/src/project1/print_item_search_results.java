package project1;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.ResultSet;

import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

public class print_item_search_results
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
    
    String[] result_ids = null;  //results of items
    String title = request.getParameter("title");
    if(request.getParameterValues("items_ids[]") != null)
    {
      result_ids = request.getParameterValues("items_ids[]");
    }
    out.println("<html>");
    out.println("<head><title>print_item_search_results</title></head>");
    out.println("<body>");
    out.println("<h3>" + title + "</h3>");
    
    out.println("<table border = '1'  style=\"background-color:#FFFFCC\" width=\"0%\" cellpadding=\"3\" cellspacing=\"3\">");
    
    out.println("<th>ID</th>");
    out.println("<th>Type</th>");
    out.println("<th>Brand</th>");
    out.println("<th>Location</th>");
    out.println("<th>Condition</th>");
    out.println("<th>Label</th>");
    out.println("<th>Serial Number</th>");
    out.println("<th>Availability</th>");
    
    try
    {
      Statement stat = con.createStatement();
      for(int k = 0; k < result_ids.length ; k++)
      {
        String sql= "SELECT ITEMS.ID, TYPES.NAME, BRANDS.NAME, LOCATIONS.NAME, ITEM_CONDITIONS.NAME, ITEMS.LABEL,ITEMS.SERIAL_NUMBER, ITEMS.AVAILABILITY FROM ITEMS, TYPES, BRANDS, LOCATIONS, ITEM_CONDITIONS WHERE ITEMS.TYPE_ID = TYPES.ID AND ITEMS.BRAND_ID = BRANDS.ID AND ITEMS.LOCATION_ID = LOCATIONS.ID AND ITEMS.CONDITION_ID = ITEM_CONDITIONS.ID AND ITEMS.ID = '" + result_ids[k] + "'";
        ResultSet rs = stat.executeQuery(sql);
        rs.next();
        out.println("<tr>");
        out.println("<td align = 'center'>" + rs.getString(1) + "</td>");
        out.println("<td align = 'center' >" + rs.getString(2) + "</td>");
        out.println("<td align = 'center'>" + rs.getString(3) + "</td>");
        out.println("<td align = 'center'>" + rs.getString(4) + "</td>");
        out.println("<td align = 'center'>" + rs.getString(5) + "</td>");
        if(rs.getString(6) != null && !rs.getString(6).equals("null"))
        {
          out.println("<td align = 'center'>" + rs.getString(6) + "</td>");
        }
        else
        {
          out.println("<td align = 'center'>-</td>");
        }
        if(rs.getString(7) != null && !rs.getString(7).equals("null"))
        {
          out.println("<td align = 'center'>" + rs.getString(7) + "</td>");
        }
        else
        {
          out.println("<td align = 'center'>-</td>");
        }
        if(rs.getString(8).equals("1"))
        {
          out.println("<td align = 'center'>Available</td>");
        }
        else
        {
          out.println("<td align = 'center'>Out</td>");
        }
        out.println("</tr>");
      }
    }
    catch(Exception e)
    {
      out.println(e.toString());
    }
    out.println("</body></html>");
    out.close();
  }
}
