package project1;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Arrays;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

public class save_emails extends HttpServlet {
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
        
        String[] admins;
        String email_cateogry = request.getParameter("email_category");
        
        if(!(request.getParameterValues("admins[]") == null)){
            admins = request.getParameterValues("admins[]");
        }
        else{
            Queries.remove_all_notifications_of_category(email_cateogry);
            out.println("Email settings successfully saved.");
            return;
        }
        
        List<String> admins_to_add = Arrays.asList(admins);
        Queries.remove_all_notifications_of_category(email_cateogry);
        Queries.add_admins_to_notification_group(admins_to_add, email_cateogry);
        
        out.println("Email settings successfully saved.");
        
        Log log = new Log();
        HttpSession session = request.getSession();
        String description = "Email settings updated.";
        log.log(description, request, session);
        
        out.close();
    }
}
