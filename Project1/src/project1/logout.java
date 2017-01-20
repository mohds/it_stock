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
        
        out.println("<html>");
        out.println("<head>");
        out.println("<link type='text/css' rel='stylesheet' href='css/main.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div id='MainContainer'>");
          
        request.getRequestDispatcher("link.html").include(request, response);  
          
        HttpSession session=request.getSession();
        
        Log log = new Log();
        log.log("Logged out", request, session);
        
        session.invalidate();  
          
        out.print("<h2 style=\"float:left\">You are successfully logged out!</h2>");  
          
        out.println("</div></body></html>");
          
        out.close();
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>logout</title></head>");
        out.println("<body>");
        out.println("<p>The servlet has received a POST. This is the reply.</p>");
        out.println("</body></html>");
        out.close();
    }
}
