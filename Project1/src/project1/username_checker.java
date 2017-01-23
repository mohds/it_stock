package project1;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;


public class username_checker extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        
        Access access = new Access();
        String user = request.getParameter("email");
        if(access.user_exists(user, "email")){
            out.print("<img src='images/not-available.png'>");
        }
        else{
            out.print("<img src='images/available.png'>");
        }
        
        out.println("<html>");
        out.println("<head><title>username_checker</title></head>");
        out.println("<body>");
        out.println("</body></html>");
        out.close();
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        
        Access access = new Access();
        String user = request.getParameter("username");
        if(access.user_exists(user, "username")){
            out.print("<img src='images/not-available.png'>");
        }
        else{
            out.print("<img src='images/available.png'>");
        }
        
        out.println("<html>");
        out.println("<head><title>username_checker</title></head>");
        out.println("<body>");
        out.println("</body></html>");
        out.close();
    }
}
