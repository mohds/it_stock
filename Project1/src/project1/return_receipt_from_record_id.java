package project1;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

public class return_receipt_from_record_id extends HttpServlet {
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
        
        String receipt_id = Records.get_receipt_id_from_record_id(record_id);
        
        List<String> record_ids = new ArrayList<String>();
        record_ids = Records.get_record_ids_from_receipt(receipt_id);
        
        HttpSession session = request.getSession();
        String admin_receiver = (String)session.getAttribute("username");
        for(int i = 0 ; i < record_ids.size() ; i++){
            if(Records.return_item(record_ids.get(i), client_returner, out, admin_receiver, new_location)){
                // send email
                String content = "";
                String subject = "IT STOCK - CHECK IN";
                content += "Admin: " + admin_receiver + " has received a whole receipt<br>";
                content += "Receipt ID: " + receipt_id + "<br>";
                content += "Returner: " + client_returner + "<br>";
                content += "New location: " + new_location + "<br><br>";
                content += "Regards,";
                SendEmail.send_email(content, subject);
                
                Log log = new Log();
                // HttpSession session = request.getSession();
                String description = "Receipt " + receipt_id + " checked in";
                log.log(description, request, session);
            }
            else{
                // send error flag to client side
                out.println("ERROR");
            }
        }
        out.close();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        out.close();
    }
}
