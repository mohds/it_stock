package project1;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

public class get_storage_settings extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=windows-1256";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        
        HttpSession session = request.getSession();
        Access access = new Access();
        String user = (String)session.getAttribute("username");
        String method = "get_storage_settings";        
        if(user == null){
            out.println("Please login first!");
        }
        else if(!access.has_access(user, method)){
            out.println("You do not have permission to do that!");
        }
        else{
            List<String> settings = new ArrayList<String>();
            settings.add(StorageSettings.read_setting("receipts_pending"));
            settings.add(StorageSettings.read_setting("receipts_done"));
            settings.add(StorageSettings.read_setting("images_folder"));
            settings.add(StorageSettings.read_setting("storage_hostname"));
            settings.add(StorageSettings.read_setting("storage_username"));
            settings.add(StorageSettings.read_setting("storage_password"));
            Gson gson = new Gson();
            out.println(gson.toJson(settings));
        }
        
        out.close();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        out.close();
    }
}
