package project1;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.*;
import javax.servlet.http.*;

public class popup_update
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
    
    //get values to update from request parameters
    //
    //
    
    String item_id = request.getParameter("item_id");
    String popup_brand = request.getParameter("popup_brand");
    String popup_location = request.getParameter("popup_location");
    String popup_condition = request.getParameter("popup_condition");
    String popup_label = request.getParameter("popup_label");
    String popup_sn = request.getParameter("popup_sn");
    String popup_notes = request.getParameter("popup_notes");
    String[] popup_specs_names = null;  //will contain names of input text elements of specs (not new specs)
    String[] popup_specs_values = null; //will contain values of input text elements of specs (not new specs)
    String [] popup_new_specs_names = null; //will contain names of input text elements of added specs (new specs)
    String [] popup_new_specs_values = null;  //will contain values of input text elements of added specs (new specs)
    
    //ids below will be used in update queries
    //
    //
    
    int popup_brand_id = queries.get_brand_id_from_name(popup_brand); 
    int popup_location_id = queries.get_location_id_from_name(popup_location);
    int popup_condition_id = queries.get_condition_id_from_name(popup_condition);
    
    if(request.getParameterValues("popup_specs_names[]") != null) //if there are specs (not new specs)
    {
      popup_specs_names = request.getParameterValues("popup_specs_names[]");  //get ids of input text elements of specs (not new)
      popup_specs_values = request.getParameterValues("popup_specs_values[]");  //get values of input text elements of specs (not new)
    }
    
    if(request.getParameterValues("popup_new_specs_names[]") != null) //if there are added specs (new specs)
    {
      popup_new_specs_names = request.getParameterValues("popup_new_specs_names[]");  //get ids of input text elements of added specs (new)
      popup_new_specs_values = request.getParameterValues("popup_new_specs_values[]");  //get values of input text elements of added specs (new)
    }
    
    //sql_update_general will update general information in ITEMS table
    String sql_update_general = "UPDATE ITEMS SET BRAND_ID = '" + popup_brand_id + "', LOCATION_ID = '" + popup_location_id + "', CONDITION_ID = '" + popup_condition_id + "', LABEL = '" + popup_label +"', SERIAL_NUMBER = '" + popup_sn + "', NOTES = '" + popup_notes + "' WHERE ID = '" + item_id + "'";
    try
    {
      Statement stat_update_general = con.createStatement();  
      stat_update_general.executeUpdate(sql_update_general);  //execute update
    }
    catch(Exception e)
    {
      out.println(e.toString());
    }
    
    if(popup_specs_names != null) //if there are specs(not new)
    {
      try
      {
        Statement stat_update_existing_specs = con.createStatement();
        for(int i = 0; i < popup_specs_names.length ; i++)  //for every existing spec input text element
        {
          String[] parts1 = popup_specs_names[i].split("spec_");  
          String[] parts2 = parts1[1].split("_");
          String spec = parts2[0];  //parse spec name from the input text element
          int spec_id = queries.get_spec_id_from_name(spec);  //get id of spec
          if(popup_specs_values[i].equals(""))  //if the value is null
          {
            String sql_delete_value = "DELETE FROM ITEMSPECVALUES WHERE ITEM_ID = '" + item_id + "' AND SPEC_ID = '" + spec_id + "'"; //delete record: it is assumed that the user is removing this spec value
            stat_update_existing_specs.executeUpdate(sql_delete_value); //execute
          }
          else  //if value is not null
          {
            String sql_update_existing_specs = "UPDATE ITEMSPECVALUES SET VALUE = '" +  popup_specs_values[i] + "'" + " WHERE SPEC_ID = '" + spec_id +"' AND ITEM_ID = '" + item_id +"'"; //update this spec value
            stat_update_existing_specs.executeUpdate(sql_update_existing_specs);  //execute
          }
        }
      }
      catch(Exception e)
      {
        out.println(e.toString());
      }
    }
      
    if(popup_new_specs_names != null) //if there are new specs(new)
    {
      try
      {
        Statement stat_insert_new_specs = con.createStatement();
        for(int i = 0; i < popup_new_specs_names.length ; i++)  //for every new spec input text element
        {
          if(!popup_new_specs_values[i].equals("")) //if value is not null
          {
            String[] parts1 = popup_new_specs_names[i].split("popup_spec_");
            String[] parts2 = parts1[1].split("_");
            String spec = parts2[0];  //parse spec name from the input text element
            int spec_id = queries.get_spec_id_from_name(spec);  //get spec id
            String sql_insert_new_specs = "INSERT INTO ITEMSPECVALUES (\"VALUE\",\"ITEM_ID\",\"SPEC_ID\") VALUES ('" + popup_new_specs_values[i] + "','" + item_id + "','" + spec_id +"')";  //insert spec value
            System.out.println(sql_insert_new_specs);
            stat_insert_new_specs.executeUpdate(sql_insert_new_specs);  //execute
          }
        }
      }
      catch(Exception e)
      {
        System.out.println(e.toString());
      }
    }
  }
}
