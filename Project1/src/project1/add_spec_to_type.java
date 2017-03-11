package project1;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

public class add_spec_to_type extends HttpServlet {
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
        
        String type = request.getParameter("type");
        String spec = request.getParameter("spec");
        
        Access access = new Access();
        HttpSession session = request.getSession();
        String user = (String)session.getAttribute("username");
        String method = "add_spec_to_type"; // this method name is to be added in the manage_access.jsp page
        if(user == null){
            // user not logged in
            out.println("<a href=\"login.jsp\">Login first ></a>");
        }
        else if(!access.has_access(user, method)){
            // user has no access
            out.println("You do not have permission to do that.");
        }
        else{
            if(Queries.add_spec_to_type(type, spec)){
                out.println("Spec " + spec + " added to type " + type + " successfully.");
                Log log = new Log();
                String description = "Added spec " + spec + " to type " + type ;
                log.log(description, request, session); // user is taken from session
            }
        }
        out.close();
    }
}
