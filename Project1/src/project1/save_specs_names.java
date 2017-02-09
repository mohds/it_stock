package project1;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

public class save_specs_names extends HttpServlet {
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
        
        String[] specs_names = request.getParameterValues("specs_names[]");
        String[] specs_ids = request.getParameterValues("specs_ids[]");
        String type = request.getParameter("type");
        
        List<Spec> specs = new ArrayList<Spec>();
        
        for(int i = 0 ; i < specs_names.length ; i++){
            specs.add(new Spec(specs_names[i], specs_ids[i]));
        }
        
        Queries.save_specs_names(specs, type);     
        
        
        out.close();
    }
}
