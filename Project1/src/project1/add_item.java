package project1;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

public class add_item extends HttpServlet {
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
        
        // get sent attributes
        String type = request.getParameter("type");
        String brand = request.getParameter("brand");
        String location = request.getParameter("location");
        String label = request.getParameter("label");
        String serial_number = request.getParameter("serial_number");
        String condition = request.getParameter("condition");
        String[] specs_names = request.getParameterValues("specs_names[]"); // sorted with respect to specs_values
        String[] specs_values = request.getParameterValues("specs_values[]"); // sorted with respect to specs_names
        
        Queries.add_item(label, location, brand, type, serial_number, condition, specs_names, specs_values);
        
        out.close();
    }
}
