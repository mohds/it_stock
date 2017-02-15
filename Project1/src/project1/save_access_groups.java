package project1;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

public class save_access_groups extends HttpServlet {
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
        
        String[] admins = request.getParameterValues("admins[]");
        String[] access_levels = request.getParameterValues("access_levels[]");
        Access access = new Access();
        
        if(access.update_access_groups(admins, access_levels)){
            out.println("User access levels have been successfully updated.");
        }
        else{
            out.println("Failed to save user access levels.");
        }
        
        Log log = new Log();
        HttpSession session = request.getSession();
        String description = "Access groups updated.";
        log.log(description, request, session);
        
        out.close();
    }
}
