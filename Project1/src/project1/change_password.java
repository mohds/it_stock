package project1;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

public class change_password extends HttpServlet {
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
        
        Access access = new Access();
        HttpSession session = request.getSession();
        String user = (String)session.getAttribute("username");
        if(user == null){
            // user not logged in
        }
        else{
            String old_password = request.getParameter("old_password");
            String first_password = request.getParameter("first_password"); // new password entered in first password field
            String second_password = request.getParameter("second_password"); // new password re-typed     
            if(access.login(user, old_password)){
                if(first_password.contains(second_password) && second_password.contains(first_password)){
                    access.update_password(user, first_password);
                    out.println("New password save successfully.");
                }
                else{
                    out.println("Password mismatch.");
                }
            }
            else{
                out.println("The old password you entered is incorrect.");
            }
        }
        out.close();
    }
}
