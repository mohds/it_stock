package project1;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

public class update_brand_name extends HttpServlet {
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
        
        String old_brand_name = request.getParameter("old_brand_name");
        String new_brand_name = request.getParameter("new_brand_name");
        
        HttpSession session = request.getSession();
        Access access = new Access();
        String user = (String)session.getAttribute("username");
        String method = "update_brand_name";        
        if(user == null){
            out.println("Please login first!");
        }
        else if(!access.has_access(user, method)){
            out.println("You do not have permission to do that!");
        }
        else{
            if(Queries.update_brand_name(old_brand_name, new_brand_name)){
                out.println("Brand name updated successfully.");
                Log log = new Log();
                log.log("Brand " + old_brand_name + " change to " + new_brand_name, request, session);
            }
            else{
                out.println("Failed to update brand name.");
            }
        }        
        out.close();
    }
}
