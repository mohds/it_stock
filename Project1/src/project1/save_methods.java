package project1;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

public class save_methods extends HttpServlet {
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
        
        String[] methods;
        String access_group = request.getParameter("access_group");
        Access access = new Access();
        
        if(!(request.getParameterValues("methods[]") == null)){
            methods = request.getParameterValues("methods[]");
        }
        else{
            access.remove_all_access_group_methods(access_group);
            out.println("Methods successfully saved.");
            return;
        }
        
        List<String> methods_to_add = Arrays.asList(methods);
        access.remove_all_access_group_methods(access_group);
        access.add_methods_to_access_group(methods_to_add, access_group);
        
        out.println("Methods successfully saved.");
        
        Log log = new Log();
        HttpSession session = request.getSession();
        String description = "Access methods have been updated.";
        log.log(description, request, session);
        
        out.close();
    }
}
