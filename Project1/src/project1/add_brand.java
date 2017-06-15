package project1;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

public class add_brand extends HttpServlet {
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
        Access access = new Access();
        String user = (String)session.getAttribute("username");
        String method = "add_brand";        
        if(user == null){
            out.println("Please login first!");
        }
        else if(!access.has_access(user, method)){
            out.println("You do not have permission to do that!");
        }
        else{
            String brand = request.getParameter("brand");
            if(brand.length() > 0){
                Queries.insert_into_table("brands", brand);
            }
            Log log = new Log();
            String description = "Added brand " + brand;
            log.log(description, request, session);
        }
        
        out.close();
    }
}
