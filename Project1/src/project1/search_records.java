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
        
        String ReceiptId = request.getParameter("ReceiptId");
        String item_label = request.getParameter("item_label");
        String Borrower = request.getParameter("Borrower");
        String AdminCheckerId = request.getParameter("AdminCheckerId");
        String BorrowBeforeDate = request.getParameter("BorrowBeforeDate");
        String BorrowAfterDate = request.getParameter("BorrowAfterDate");
        String ReturnBeforeDate = request.getParameter("ReturnBeforeDate");
        String ReturnAfterDate = request.getParameter("ReturnAfterDate");
        String ItemType = request.getParameter("ItemType");
        String ReceiptStatus = request.getParameter("ReceiptStatus");
        String ItemStatus = request.getParameter("ItemStatus");
        
        Records.generate_results(ReceiptId, item_label, Borrower, AdminCheckerId, BorrowBeforeDate, BorrowAfterDate, ReturnBeforeDate, ReturnAfterDate, ItemType, ReceiptStatus, ItemStatus, out);
                
        out.close();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        
        
        out.close();
    }
}
