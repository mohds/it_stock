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
        out.close();
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();        
        String user = "";
        String password = "";
        Access access = new Access();
        
        user = request.getParameter("username"); // could be an email or username        
        password = request.getParameter("password");
        
        // sql-injection protection        
        user = access.filter_sql_injection(user);
        password = access.filter_sql_injection(password);
        
        if(access.login(user, password)){
            request.getRequestDispatcher("index.jsp").include(request, response);
            HttpSession session = request.getSession();
            session.setAttribute("username",user);
            session.setAttribute("id", Queries.get_id_from_username(user));
            Log log = new Log();
            log.log("Logged in", request, session);            
            
        }
        else{
            out.println("login failed.");
            request.getRequestDispatcher("login.jsp").include(request, response);
            
            HttpSession session = request.getSession();
            Log log = new Log();
            log.log("Unsuccessful login for: "+user, request, session);
        }
        
        out.close();
    }
}
