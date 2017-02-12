package project1;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;

import javax.servlet.*;
import javax.servlet.http.*;

public class tester extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=windows-1256";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>tester</title></head>");
        out.println("<body>");
        
        Access access = new Access();
        if(access.has_access("m.salloum", "search_records")){
            out.println("Has access");    
        }
        else{
            out.println("no access");
        }
        
        out.println("</body></html>");
        out.close();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>tester</title></head>");
        out.println("<body>");
        
        
        
        out.println("</body></html>");
        out.close();
    }
}
