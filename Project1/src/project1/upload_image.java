package project1;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import java.util.Iterator;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;

import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import org.apache.commons.io.FileUtils;


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
              
              String user = "it.stock";
              String pass ="it$t0cK*543";
              String server_ip = "140.125.2.102";
              String sharedFolder="IT/IT Support/IT STOCK/Item Images";
              
              
              FileItem item = (FileItem) iterator.next();
              String fileName = item.getName(); 
              
              String path="smb://"+ server_ip +"/"+sharedFolder+"/" + fileName;
              NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("",user, pass);
              SmbFile smbFile = new SmbFile(path,auth);
              SmbFileOutputStream smbfos = new SmbFileOutputStream(smbFile);
              smbfos.write(item.get());
              smbfos.close();
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    out.close();
  }
}
