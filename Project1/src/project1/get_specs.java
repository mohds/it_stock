package project1;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

public class get_specs extends HttpServlet {
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
        
        // get the type selected in the autocomplete box of labeled "Add Item of type:"
        String type = request.getParameter("type_selected");
        
        // get specs of chosen type and add to list
        List<Spec> specs = new ArrayList<Spec>();
        List<String> specs_names = Queries.get_specs_of_type(type);
        for(int i = 0 ; i < specs_names.size() ; i++) {
            String spec_id = Queries.get_id_from_name("specs", specs_names.get(i));
            specs.add(new Spec(specs_names.get(i), spec_id));
        }
        
        // output as JSON
        Gson gson = new Gson();
        out.println(gson.toJson(specs));
        
        // test
        // System.out.println(gson.toJson(specs));
        
        out.close();
    }
}
