package project1;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class add_item extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=windows-1256";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        
        out.close();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        
        boolean successful_add = false;
        boolean unsuccessful_add = false;
        String type = "";
        String brand = "";
        String location = "";
        String label = "";
        String serial_number = "";
        String condition = "";
        String[] specs_names = null; // sorted with respect to specs_values
        String[] specs_values = null; // sorted with respect to specs_names
        String model = "";
        String keyword = "";
        String notes = "";
        String count = "";
        String invoice_number = "";
        String warranty_start_date = "";
        String warranty_end_date = "";
        String item_image_name = "";
        String invoice_image_name = "";        
        
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        
        if (isMultipart) {
            // Create a factory for disk-based file items
            FileItemFactory factory = new DiskFileItemFactory();
        
            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);
        
            try {
                // Parse the request
                List /* FileItem */ items = upload.parseRequest(request);
                Iterator iterator = items.iterator();
                while (iterator.hasNext())
                {
                  FileItem item = (FileItem) iterator.next();
                  if (!item.isFormField())
                  {
                      
                    String user = StorageSettings.read_setting("storage_username");
                    String pass = StorageSettings.read_setting("storage_password");
                    String server_ip = StorageSettings.read_setting("storage_hostname");
                    String sharedFolder = StorageSettings.read_setting("images_folder");
                  
                    String fileName = item.getName();
                    
                    String path="smb://"+ server_ip +"/"+sharedFolder+"/" + fileName;
                    NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("",user, pass);
                    SmbFile smbFile = new SmbFile(path,auth);
                    SmbFileOutputStream smbfos = new SmbFileOutputStream(smbFile);
                    smbfos.write(item.get());
                    smbfos.close();
                    
                    String name = item.getFieldName();
                    if(name.equals("InvoiceImage")){
                        invoice_image_name = fileName;
                    }
                    else if(name.equals("ItemImage")){
                        item_image_name = fileName;
                    }
                  }
                  else
                  {
                    String name = item.getFieldName();
                    if(name.equals("type"))
                    {
                        type = item.getString();
                    }
                    else if(name.equals("brand"))
                    {
                        brand = item.getString();
                    }
                    else if(name.equals("location"))
                    {
                        location = item.getString();
                    }
                    else if(name.equals("label"))
                    {
                        label = item.getString();
                    }
                    else if(name.equals("serial_number"))
                    {
                        serial_number = item.getString();
                    }
                    else if(name.equals("condition"))
                    {
                        condition = item.getString();
                    }
                    else if(name.equals("specs_names"))
                    {
                        String spec_names_ = item.getString();
                        specs_names = spec_names_.split(",");
                    }
                    else if(name.equals("specs_values"))
                    {
                        String spec_values_ = item.getString();
                        spec_values_ = "BLANK"  + spec_values_  + "BLANK";
                        specs_values = spec_values_.split(",");
                    }
                    else if(name.equals("model"))
                    {
                        model = item.getString();
                    }
                    else if(name.equals("keyword"))
                    {
                        keyword = item.getString();
                    }
                    else if(name.equals("notes"))
                    {
                        notes = item.getString();
                    }
                    else if(name.equals("count"))
                    {
                        count = item.getString();
                    }
                    else if(name.equals("invoice_number"))
                    {
                      invoice_number = item.getString();
                    }
                    else if(name.equals("warranty_start_date"))
                    {
                      warranty_start_date = item.getString();
                    }
                    else if(name.equals("warranty_end_date"))
                    {
                      warranty_end_date = item.getString();
                    }
                  }
                }
              
            }
            catch(Exception ex){
                System.out.println(ex.toString());
            }
                
            int count_ = 0;
            int added_count = 0;
            try{
                count_ = Integer.parseInt(count);
            }
            catch (Exception ex){
                System.out.println(ex.toString());
            }
            if(count_ > 0 && count_ < 101){
                for(int i = 0 ; i < count_ ; i++){
                    if(Queries.add_item(label, location, brand, type, serial_number, condition, specs_names, specs_values, model, keyword, notes, invoice_number, warranty_start_date, warranty_end_date, item_image_name, invoice_image_name)){
                        
                        Log log = new Log();
                        HttpSession session = request.getSession();
                        String description = "Added item " + label + " of type " + type;
                        log.log(description, request, session); 
                        successful_add = true;
                        added_count++;
                    }
                    else{
                        //out.println("Cannot add item "+ label +". Check input.");
                        unsuccessful_add = true;
                    }
                }
            }
            else{
                out.println("Cannot add item "+ label +". Count error.");
            }
            if(successful_add){
                out.println("Item added successfully. Count: (" + added_count + "/" + count + ")");
            }
            if(unsuccessful_add){
                out.println("Item add error. Check input.");
            }
            out.close();
        }
    }
}
