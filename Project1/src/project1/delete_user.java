package project1;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

public class delete_user extends HttpServlet {
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
        Access access = new Access();
        access.delete_user(username);
        
        out.println("User " + username + " has been deleted successfully.");
        
        Log log = new Log();
        HttpSession session = request.getSession();
        String description = "User " + username + " deleted.";
        log.log(description, request, session);
        
        out.close();
    }
}
