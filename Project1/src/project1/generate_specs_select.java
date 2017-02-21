package project1;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;

import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

// @WebServlet(name = "generate_specs_select", urlPatterns = { "/generate_specs_select" })
public class generate_specs_select
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
    String selected_type = request.getParameter("selected_type"); //get selected type name from request parameter
    
    db_queries queries = new db_queries();
    
    List<String> type_specs_list = queries.get_type_specs(selected_type); //list contains all relevant spec names of selected type
    
    //Select element for relevant specs
    //
    //
    
    out.println("<label>Specs: </label><select name = 'select_item_specs' id = 'select_item_specs' onmousedown=\"if(this.options.length>8){this.size=0;}\"  onchange='this.size=0;' onblur=\"this.size=0;\" size = \"0\">");
    out.println("<option value = 'Please select'>Please select</option>");
    for(int i = 0 ; i < type_specs_list.size() ; i++)
    {
      out.println("<option value = '" + type_specs_list.get(i) + "'>" + type_specs_list.get(i) + "</option>");
    }
    out.println("</select>");
    
    out.println("<input id = 'add_spec_button' type = 'button' value = 'Add' onclick = 'add_spec();' /><br><br>");  //add button, onclick, call add_spec() from get_type_specs.js
    out.close();
  }
}
