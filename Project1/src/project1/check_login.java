package project1;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

public class check_login extends HttpServlet {
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
        
        HttpSession session = request.getSession();
        String user = (String)session.getAttribute("username");
        
        if(user == null){
            out.println("<a href=\"login.jsp\"><img class=\"nav-images\" src=\"images/lock.png\">Login</a>");
        }
        else{
            out.println("<a href=\"logout\"><img class=\"nav-images\" src=\"images/lock.png\">Logout</a>");
        }
        
        out.close();
    }
}
