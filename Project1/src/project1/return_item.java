package project1;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

public class return_item extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=windows-1256";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        
        String record_id = request.getParameter("record_id");
        String client_returner = request.getParameter("client_returner");
        String new_location = request.getParameter("new_location");
        
        HttpSession session = request.getSession();
        String admin_receiver = (String)session.getAttribute("username");
        
        Access access = new Access();
        String user = (String)session.getAttribute("username");
        String method = "return_item"; // this method name is to be added in the manage_access.jsp page
        if(user == null){
            out.println("<a href=\"login.jsp\">Login first.</a>");
        }
        else if(!access.has_access(user, method)){
            out.println("You do not have permission to do that.");
        }
        else{
            if(Records.return_item(record_id, client_returner, out, admin_receiver, new_location)){
                // send email
                String content = "";
                String subject = "IT STOCK - CHECK IN";
                content += "Admin: " + admin_receiver + " has received an item<br>";
                content += "Record ID: " + record_id + "<br>";
                content += "Returner: " + client_returner + "<br>";
                content += "New location: " + new_location + "<br><br>";
                content += "Regards,";
                SendEmail.send_email(content, subject);
            }
            else{
                // send error flag to client side
                out.println("ERROR");
            }
            
            Log log = new Log();
            // HttpSession session = request.getSession();
            String description = "Record " + record_id + " checked in";
            log.log(description, request, session);
        }

        
        
        out.close();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        out.close();
    }
}
