package project1;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

public class logout extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType("text/html");  
        PrintWriter out=response.getWriter();
        HttpSession session=request.getSession();
        
        Log log = new Log();
        log.log("Logged out", request, session);
        
        session.invalidate();  
        
        request.getRequestDispatcher("login.jsp").include(request, response);  
          
        out.close();
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        out.close();
    }
}
