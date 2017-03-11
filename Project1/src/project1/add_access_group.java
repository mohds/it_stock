package project1;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

public class add_access_group extends HttpServlet {
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
        
        String access_group = request.getParameter("access_group");
        Access access = new Access();
        
        
        HttpSession session = request.getSession();
        String user = (String)session.getAttribute("username");
        String method = "add_access_group"; // this method name is to be added in the manage_access.jsp page
        if(user == null){
            out.println("<a href=\"login.jsp\">Login first ></a>");
        }
        else if(!access.has_access(user, method)){
            out.println("You do not have permission to do that");
        }
        else{
            access.add_access_group(access_group);
            Log log = new Log();
            log.log("Access Group: " + access_group + " added.", request, session);
            out.println("Access Group : " + access_group + " was added successfully.");
        }

        
        out.close();
    }
}
