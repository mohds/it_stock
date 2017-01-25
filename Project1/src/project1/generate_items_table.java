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
    db_queries queries = new db_queries();
    String type = request.getParameter("type");
    String brand = request.getParameter("brand");
    String location = request.getParameter("location");
    String condition = request.getParameter("condition");
    String label = request.getParameter("label");
    String sn = request.getParameter("sn");
    String availability_available = request.getParameter("availability_available");
    String availability_out = request.getParameter("availability_out");
    String[] specs_names = null;
    String[] specs_values = null;
    if(request.getParameterValues("specs_names[]") != null)
    {
      specs_names = request.getParameterValues("specs_names[]");
      specs_values = request.getParameterValues("specs_values[]");
    }
    String sql_master = "SELECT ITEMS.ID,TYPES.NAME AS Type,BRANDS.NAME AS Brand,LOCATIONS.NAME AS Location,ITEM_CONDITIONS.NAME AS Condition,ITEMS.LABEL,ITEMS.SERIAL_NUMBER FROM ITEMS,TYPES,BRANDS,LOCATIONS,ITEM_CONDITIONS WHERE ITEMS.TYPE_ID = TYPES.ID AND ITEMS.BRAND_ID = BRANDS.ID AND ITEMS.LOCATION_ID = LOCATIONS.ID AND ITEMS.CONDITION_ID = ITEM_CONDITIONS.ID AND DELETED = '0'";
    
    if(!type.equals("any"))
    {
      int type_id = queries.get_type_id_from_name(type);
      sql_master = sql_master + " AND TYPES.ID = '" + type_id + "'";
    }
    if(!brand.equals("any"))
    {
      int brand_id = queries.get_brand_id_from_name(brand);
      sql_master = sql_master + " AND BRANDS.ID = '" + brand_id + "'";
    }
    if(!location.equals("any"))
    {
      int location_id = queries.get_location_id_from_name(location);
      sql_master = sql_master + " AND LOCATIONS.ID = '" + location_id + "'";
    }
    if(!condition.equals("any"))
    {
      int condition_id = queries.get_condition_id_from_name(condition);
      sql_master = sql_master + " AND CONDITIONS.ID = '" + condition_id + "'";
    }
    if(!label.equals(""))
    {
      sql_master = sql_master + " AND ITEMS.LABEL LIKE '%" + label + "%'";
    }
    if(!sn.equals(""))
    {
      sql_master = sql_master + " AND ITEMS.SERIAL_NUMBER LIKE '%" + sn + "%'";
    }
    if(availability_available != null && availability_available.equals("1"))
    {
      sql_master = sql_master + " AND ITEMS.AVAILABILITY = '1'";
    }
    else if(availability_out != null && availability_out.equals("1"))
    {
      sql_master = sql_master + " AND ITEMS.AVAILABILITY = '0'";
    }
    if(specs_names != null)
    {
      sql_master = sql_master + " AND ITEMS.ID = ITEMSPECVALUES.ITEM_ID";
      for(int i = 0; i < specs_names.length ; i++)
      {
        if(!specs_values[i].equals(""))
        {
          String[] parts1 = specs_names[i].split("spec_");
          String[] parts2 = parts1[1].split("_");
          String spec = parts2[0];
          int spec_id = queries.get_spec_id_from_name(spec);
          sql_master = sql_master + " AND ITEMSPECVALUES.SPEC_ID = '" + spec_id + "' AND ITEMSPECVALUES.VALUE LIKE '%" + specs_values[i] + "%'";
        }
      }
    }
    System.out.println(sql_master);  
    /*for(int i = 0 ; i < specs_names.length ; i++)
    {
      System.out.println("Name: " + specs_names[i] + " ----- Value: " + specs_values[i]  + " ----- Type: " + type + " ----- Brand: " + brand + " ----- Location: " + location + " ----- Condition: " + condition + " ----- Label: " + label + " ----- availability_all: " + availability_all + " ----- availability_available: " + availability_available + " ----- availability_out: " + availability_out);
    }*/
    out.close();
  }
}
