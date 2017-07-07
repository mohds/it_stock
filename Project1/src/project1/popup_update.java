package project1;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.Iterator;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class popup_update
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
    
    connect_to_db connect = new connect_to_db();
    Connection con = connect.connect();
    
    db_queries queries = new db_queries();
    
    //get values to update from request parameters
    //
    //
    
    String item_id = ""; //request.getParameter("item_id");
    String popup_brand = ""; //request.getParameter("popup_brand");
    String popup_model = ""; //request.getParameter("popup_model");
    String popup_location = ""; //request.getParameter("popup_location");
    String popup_condition = ""; //request.getParameter("popup_condition");
    String popup_label = ""; //request.getParameter("popup_label");
    String popup_keyword = ""; //request.getParameter("popup_keyword");
    String popup_sn = ""; //request.getParameter("popup_sn");
    String popup_notes = ""; //request.getParameter("popup_notes");
    String popup_invoice_number = ""; //request.getParameter("popup_invoice_number");
    String popup_warranty_start_date = ""; //request.getParameter("popup_warranty_start_date");
    String popup_warranty_end_date = ""; //request.getParameter("popup_warranty_end_date");
    String[] popup_specs_names = null; //null;  //will contain names of input text elements of specs (not new specs)
    String[] popup_specs_values = null; //null; //will contain values of input text elements of specs (not new specs)
    String [] popup_new_specs_names = null;//null; //will contain names of input text elements of added specs (new specs)
    String [] popup_new_specs_values = null; //null;  //will contain values of input text elements of added specs (new specs)
    String item_image_name = "";
    String invoice_image_name = "";
    
    
    boolean isMultipart = ServletFileUpload.isMultipartContent(request);
    
    if (isMultipart) 
    {
        // Create a factory for disk-based file items
        FileItemFactory factory = new DiskFileItemFactory();
    
        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
    
        try {
            // Parse the request
            List /* FileItem */ items = upload.parseRequest(request);
            Iterator iterator = items.iterator(); //iteratior contains all html elements received
            while (iterator.hasNext())
            {
              FileItem item = (FileItem) iterator.next(); //item is a single html element
              if (!item.isFormField())
              {
                //credentials for the IT Stock user, used to access the folder in which item images and invoice images are stored
                //
                //
                String user = "it.stock";
                String pass ="it$t0cK*543";
                String server_ip = "140.125.2.102";
                String sharedFolder="IT/IT Support/IT STOCK/Item Images";
              
                String fileName = item.getName(); //fileName is the name of the file currently being processed
                
                String path="smb://"+ server_ip +"/"+sharedFolder+"/" + fileName; //final path of the image
                NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("",user, pass);  //authentication
                
                //write the file(image) to the final location
                //
                //
                
                SmbFile smbFile = new SmbFile(path,auth); 
                SmbFileOutputStream smbfos = new SmbFileOutputStream(smbFile);
                smbfos.write(item.get());
                smbfos.close();
                
                //variable name, below, will contain the name of the variable sent withing the request
                //according to the request variable name, the variables in this class will be assigned
                //
                String name = item.getFieldName(); 
                if(name.equals("invoice_image"))
                {
                    invoice_image_name = fileName;
                }
                else if(name.equals("item_image"))
                {
                    item_image_name = fileName;
                }
              }
              else
              {
                String name = item.getFieldName();
                if(name.equals("item_id"))
                {
                    item_id = item.getString();
                }
                else if(name.equals("popup_brand"))
                {
                    popup_brand = item.getString();
                }
                else if(name.equals("popup_model"))
                {
                    popup_model = item.getString();
                }
                else if(name.equals("popup_location"))
                {
                    popup_location = item.getString();
                }
                else if(name.equals("popup_condition"))
                {
                    popup_condition = item.getString();
                }
                else if(name.equals("popup_label"))
                {
                    popup_label = item.getString();
                }
                else if(name.equals("popup_keyword"))
                {
                    popup_keyword = item.getString();
                }
                else if(name.equals("popup_sn"))
                {
                    popup_sn = item.getString();
                }
                else if(name.equals("popup_notes"))
                {
                    popup_notes = item.getString();
                }
                else if(name.equals("popup_invoice_number"))
                {
                    popup_invoice_number = item.getString();
                }
                else if(name.equals("popup_warranty_start_date"))
                {
                    popup_warranty_start_date = item.getString();
                }
                else if(name.equals("popup_warranty_end_date"))
                {
                    popup_warranty_end_date = item.getString();
                }
                
                //for variables that are lists, we need to split them on ","
                //
                //
                else if(name.equals("popup_specs_names"))
                {
                    String spec_names_ = item.getString();
                    popup_specs_names = spec_names_.split(",");
                }
                else if(name.equals("popup_specs_values"))
                {
                    String spec_values_ = item.getString();
                    popup_specs_values = spec_values_.split(",");
                }
                else if(name.equals("popup_new_specs_names"))
                {
                    String new_spec_names_ = item.getString();
                    popup_new_specs_names = new_spec_names_.split(",");
                }
                else if(name.equals("popup_new_specs_values"))
                {
                    String new_spec_values_ = item.getString();
                    popup_new_specs_values = new_spec_values_.split(",");
                }
              }
            }
        }
        
        
        catch(Exception ex)
          {
            System.out.println(ex.toString());
        }
    }
    
    
    //ids below will be used in update queries
    //
    //
    
    int popup_brand_id = queries.get_brand_id_from_name(popup_brand); 
    int popup_location_id = queries.get_location_id_from_name(popup_location);
    int popup_condition_id = queries.get_condition_id_from_name(popup_condition);
    
    //sql_update_general will update general information in ITEMS table
    String sql_update_general = "UPDATE ITEMS SET BRAND_ID = '" + popup_brand_id + "', MODEL = '" + popup_model + "', LOCATION_ID = '" + popup_location_id + "', CONDITION_ID = '" + popup_condition_id + "', LABEL = '" + popup_label + "', KEYWORD = '" + popup_keyword + "', SERIAL_NUMBER = '" + popup_sn + "', NOTES = '" + popup_notes + "', WARRANTY_START_DATE = TO_DATE('" + popup_warranty_start_date + "','dd-mm-yyyy'), WARRANTY_END_DATE = TO_DATE('" + popup_warranty_end_date + "','dd-mm-yyyy'), IMAGE = '" + item_image_name + "' WHERE ID = '" + item_id + "'";
    try
    {
      Statement stat_update_general = con.createStatement();  
      stat_update_general.executeUpdate(sql_update_general);  //execute update
    }
    catch(Exception e)
    {
      out.println(e.toString());
    }
    String sql_get_invoice_id = "SELECT ITEMS.INVOICE_FK FROM ITEMS WHERE ITEMS.ID = '" + item_id + "'";  //sql query to get invoice ID from item ID
    try
    {
      //get invoice id
      //
      
      Statement stat_get_invoice_id = con.createStatement();
      ResultSet rs_get_invoice_id = stat_get_invoice_id.executeQuery(sql_get_invoice_id);
      rs_get_invoice_id.next();
      int invoice_id = rs_get_invoice_id.getInt(1);
      
      //update invoice information
      String sql_update_invoice = "UPDATE INVOICES SET INVOICES.INVOICE_NUMBER = '" + popup_invoice_number + "'" + " WHERE INVOICES.ID = '" + invoice_id + "', INVOICES.IMAGE = '" + invoice_image_name + "'";
      Statement stat_update_invoice = con.createStatement();
      stat_update_invoice.executeUpdate(sql_update_invoice);
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
            stat_update_existing_specs.executeUpdate(sql_update_existing_specs);  //execute.
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
            stat_insert_new_specs.executeUpdate(sql_insert_new_specs);  //execute
          }
        }
      }
      catch(Exception e)
      {
        System.out.println(e.toString());
      }
    }
    
    HttpSession session = request.getSession();
    
    Log log = new Log();
    String description = "Edited item of ID " + item_id;
    log.log(description, request, session);
    
  }
}
