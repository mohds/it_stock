package project1;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
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
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class upload_image
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
    
    String item_id = "";
    String fileName = "";
    out.println("<html>");
    out.println("<head><title>upload_image</title></head>");
    out.println("<body>");
    out.println("<p>The servlet has received a POST. This is the reply.</p>");
    out.println("</body></html>");
    
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
                String user = "it.stock";
                String pass ="it$t0cK*543";
                String server_ip = "140.125.2.102";
                String sharedFolder="IT/IT Support/IT STOCK/Item Images";
                
                
                
                fileName = item.getName(); 
                
                String path="smb://"+ server_ip +"/"+sharedFolder+"/" + fileName;
                NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("",user, pass);
                SmbFile smbFile = new SmbFile(path,auth);
                SmbFileOutputStream smbfos = new SmbFileOutputStream(smbFile);
                smbfos.write(item.get());
                smbfos.close();
              }
              else
              {
                item_id = item.getString();
              }
            }
          
          String sql_add_image_to_db = "UPDATE ITEMS SET ITEMS.IMAGE = '" + fileName +"' WHERE ITEMS.ID = '" + item_id + "'";
          Statement stat_add_image_to_db = con.createStatement();
          stat_add_image_to_db.executeUpdate(sql_add_image_to_db);
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
  }
}
