package project1;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

public class log_search extends HttpServlet {
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
        out.println("<head><title>log_search</title></head>");
        out.println("<body>");
        out.println("</body></html>");
        out.close();
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        
        String username = request.getParameter("username");
        String ip = request.getParameter("ip");
        String description = request.getParameter("description");
        String date_before = request.getParameter("date_before");
        String date_after = request.getParameter("date_after");
        String bottom_limit = request.getParameter("bottom_limit");
        String top_limit = request.getParameter("top_limit");
        
        HttpSession session = request.getSession();
        Access access = new Access();
        String user = (String)session.getAttribute("user-email");
        String method = "view_logs";
        
        if(user == null){
            out.println("Please login first!");
        }
        else if(!access.has_access(user, method)){
            out.println("You do not have permission to do that!");
        }
        else{        
            Log log = new Log();
            log.generate_results_table(username, ip, description, date_before, date_after, bottom_limit, top_limit, out);        
        }
        out.close();
    }
}
