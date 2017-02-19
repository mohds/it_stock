package project1;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

public class get_records_ids_of_receipt extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=windows-1256";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();        
        String record_id = request.getParameter("record_id");
        String receipt_id = Records.get_receipt_id_from_record_id(record_id);
        List<String> records_ids = new ArrayList<String>();
        records_ids = Records.get_record_ids_from_receipt(receipt_id);
        Gson gson = new Gson();
        out.println(gson.toJson(records_ids));
        out.close();
    }
}
