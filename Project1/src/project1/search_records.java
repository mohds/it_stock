package project1;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

public class search_records extends HttpServlet {
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
        
        String ReceiptId = request.getParameter("ReceiptId");
        String Borrower = request.getParameter("Borrower");
        String Receiver = request.getParameter("Receiver");
        String BeforeDate = request.getParameter("BeforeDate");
        String AfterDate = request.getParameter("AfterDate");
        String ItemType = request.getParameter("ItemType");
        String ReceiptStatus = request.getParameter("ReceiptStatus");
        String ItemStatus = request.getParameter("ItemStatus");
        
        Records.generate_results(ReceiptId, Borrower, Receiver, BeforeDate, AfterDate, ItemType, ReceiptStatus, ItemStatus);
        
        out.close();
    }
}
