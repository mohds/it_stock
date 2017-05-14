package project1;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
    response.setContentType(CONTENT_TYPE);
    try
    {
       String fileName = request.getParameter("image"); 
        String user = "it.stock";
        String pass ="it$t0cK*543";
        String server_ip = "140.125.2.102";
        String sharedFolder="IT/IT Support/IT STOCK/Item Images";

        String path="smb://"+ server_ip +"/"+sharedFolder+"/" + fileName;
        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("",user, pass);
      
       SmbFileInputStream fis = new SmbFileInputStream(new SmbFile(path,auth));
      
       BufferedInputStream bis = new BufferedInputStream(fis);             
       response.setContentType(CONTENT_TYPE);
       BufferedOutputStream output = new BufferedOutputStream(response.getOutputStream());
       for (int data; (data = bis.read()) > -1;) {
         output.write(data);
       }             
    }
    catch(IOException e){

    }finally{
        // close the streams
    }
  }
}
