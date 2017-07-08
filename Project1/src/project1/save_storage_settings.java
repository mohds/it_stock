package project1;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

public class save_storage_settings extends HttpServlet {
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
        
        String receipts_pending = "";
        String receipts_done = "";
        String images_folder = "";
        String storage_hostname = "";
        String storage_username = "";
        String storage_password = "";
        receipts_pending = request.getParameter("receipts_pending");
        receipts_done = request.getParameter("receipts_done");
        images_folder = request.getParameter("images_folder");
        storage_hostname = request.getParameter("storage_hostname");
        storage_username = request.getParameter("storage_username");
        storage_password = request.getParameter("storage_password");
        
        HttpSession session = request.getSession();
        Access access = new Access();
        String user = (String)session.getAttribute("username");
        String method = "save_storage_settings";
        if(user == null){
            out.println("Please login first.");
        }
        else if(access.has_access(user, method)){        
            StorageSettings.write_setting("receipts_pending", receipts_pending);
            StorageSettings.write_setting("receipts_done", receipts_done);
            StorageSettings.write_setting("images_folder", images_folder);
            StorageSettings.write_setting("storage_hostname", storage_hostname);
            StorageSettings.write_setting("storage_username", storage_username);
            StorageSettings.write_setting("storage_password", storage_password);
            
            out.println("Storage settings saved successfully.");
            
            Log log = new Log();
            String description = "Settings Edited";
            log.log(description, request, session);
        }
        else{
            out.println("You do not have permission to do that");   
        }
        out.close();
    }
}
