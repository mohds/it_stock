package project1;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

public class force_change_password extends HttpServlet {
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
        
        String username = request.getParameter("username");
        String new_password = request.getParameter("new_password");
        Access access = new Access();
        HttpSession session = request.getSession();
        String user = (String)session.getAttribute("username");
        String method = "reset_password"; // this method name is to be added in the manage_access.jsp page
        if(user == null){
            out.println("<a href=\"login.jsp\">Login first ></a>");
        }
        else if(!access.has_access(user, method)){
            out.println("You do not have permission to do that.");
        }
        else{
            access.update_password(username, new_password);
            out.println("Password for " + username + " has been updated successfully.");
            Log log = new Log();
            log.log("Password for " + username + " has been reset.", request, session);
        }
        out.close();
    }
}
