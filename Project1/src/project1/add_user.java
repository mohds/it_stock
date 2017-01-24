package project1;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;
// mohammad
public class add_user extends HttpServlet {
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
        out.println("<head><title>add_user</title></head>");
        out.println("<body>");
        out.println("</body></html>");
        out.close();
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        String name = "";
        String user = "";
        String password = "";
        
        name = request.getParameter("name");
        user = request.getParameter("username");
        password = request.getParameter("password");
        
        Access access = new Access();
        
        if(access.user_exists(user, "username")){
            out.println("Username already exists!");
            request.getRequestDispatcher("new_user.jsp").include(request, response);
        }
        else{
            String password_hash = "-1";
            password_hash = access.password_hash(password);
            
            access.add_user(name, user, password_hash);
            
            HttpSession session = request.getSession(false);
            Log log = new Log();
            log.log("New user added ("+user+")", request, session);
            
            out.println("<html>");
            out.println("<head><title>User added</title>");
            out.println("<link rel='stylesheet' type='text/css' href='css/main.css'>");
            out.println("</head>");            
            out.println("<body>");
            out.println("<div id='MainContainer'>");
            out.println("<div class='confirmation table'>");
            if(access.confirm_added_user(user)){
                out.println("<h1>User successfully added</h1>");                
                out.println("<table>");
                out.println("<tr>");
                out.println("<th>Name: </th>");
                out.println("<td>"+ name +"</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<th>Username: </th>");
                out.println("<td>"+ user +"</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<th>Password Hash: </th>");
                out.println("<td>"+ password_hash +"</td>");
                out.println("</tr>");
                out.println("");
                out.println("</table><br>");
            }
            else{
                out.println("<h1>Failed to add user</h1>");
                out.println("<a href='new_user.jsp'>< return</a><br>");
            }
            out.println("</div>");
            
            out.println("<a href='login.jsp'>Login</a>");
            out.println("</div>");
            out.println("</body></html>");
            out.close();
        }
    }
}
