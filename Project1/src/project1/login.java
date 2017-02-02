package project1;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

public class login extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>login</title></head>");
        out.println("<body>");
        out.println("</body></html>");
        out.close();
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();        
        
        out.println("<html>");
        out.println("<head><title>login</title>");
        out.println("<link type='text/css' rel='stylesheet' href='css/main.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div id='MainContainer'>");
        String user = "";
        String password = "";
        Access access = new Access();
        
        user = request.getParameter("username"); // could be an email or username        
        password = request.getParameter("password");
        
        // sql-injection protection        
        user = access.filter_sql_injection(user);
        password = access.filter_sql_injection(password);
        
        if(access.login(user, password)){
            request.getRequestDispatcher("nav_bar.html").include(request, response);
            out.println("<h1 style=\"float:left\">Login successful</h1>");
            HttpSession session = request.getSession();
            session.setAttribute("username",user);
            
            Log log = new Log();
            log.log("Logged in", request, session);            
            
        }
        else{
            out.println("Login failed");
            request.getRequestDispatcher("login.jsp").include(request, response);
            
            HttpSession session = request.getSession();
            Log log = new Log();
            log.log("Unsuccessful login for: "+user, request, session);
        }
        out.println("</div>");
        out.println("</body></html>");
        out.close();
    }
}
