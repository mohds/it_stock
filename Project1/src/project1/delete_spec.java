package project1;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

public class delete_spec extends HttpServlet {
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
        
        String spec_id = request.getParameter("spec_id");
        String type = request.getParameter("type");
        HttpSession session = request.getSession();
        Access access = new Access();
        String user = (String)session.getAttribute("username");
        String method = "delete_spec";        
        if(user == null){
            out.println("Please login first!");
        }
        else if(!access.has_access(user, method)){
            out.println("You do not have permission to do that!");
        }
        else{        
            if(Queries.remove_spec_from_type(spec_id, type)){
                out.print("Spec was deleted successfully.");
                Log log = new Log();
                log.log("Deleted spec of id " + spec_id, request, session);
            }
            else{
                out.print("Can not delete spec.");
            }
        }
        
        out.close();
    }
}
