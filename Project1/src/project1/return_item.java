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
        // session ready for use
        // commented until required
        //HttpSession session = request.getSession();
        //String admin_receiver = (String)session.getAttribute("username");
        String admin_receiver = "m.salloum"; // static until session is implemented
        if(Records.return_item(record_id, client_returner, out, admin_receiver, new_location)){
            // send email
            String content = "";
            String subject = "IT STOCK - CHECK IN";
            content += "Admin: " + admin_receiver + " has received an item<br>";
            content += "Record ID: " + record_id + "<br>";
            content += "Returner: " + client_returner + "<br>";
            content += "New location: " + new_location + "<br>";
            content += "Regards,<br>";
            SendEmail.send_email(content, subject);
        }
        else{
            // send error flag to client side
            out.println("ERROR");
        }
        
        out.close();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        out.close();
    }
}
