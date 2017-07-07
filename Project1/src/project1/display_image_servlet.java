package project1;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

public class display_image_servlet
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
    ServletContext cntx= request.getServletContext();
    // Get the absolute path of the image
    String filename = cntx.getRealPath("Images/button.png");
    // retrieve mimeType dynamically
    String mime = cntx.getMimeType(filename);
    if (mime == null) 
    {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      return;
    }
    
    //servlet responds with an outputstream displaying an image (image name sent in the request
    //
    
    response.setContentType(mime);
    String fileName = request.getParameter("image"); 
    String user = "it.stock";
    String pass ="it$t0cK*543";
    String server_ip = "140.125.2.102";
    String sharedFolder="IT/IT Support/IT STOCK/Item Images";

    String path="smb://"+ server_ip +"/"+sharedFolder+"/" + fileName;
    NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("",user, pass);
    
    SmbFile file = new SmbFile(path,auth);
    SmbFileInputStream fis = new SmbFileInputStream(file);
    response.setContentLength((int)file.length());
    OutputStream out = response.getOutputStream();

    // Copy the contents of the file to the output stream
     byte[] buf = new byte[1024];
     int count = 0;
     while ((count = fis.read(buf)) >= 0) 
     {
       out.write(buf, 0, count);
    }
  out.close();
  fis.close();
  }
}
