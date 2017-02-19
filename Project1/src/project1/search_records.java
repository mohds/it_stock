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
        String item_id = request.getParameter("item_id");
        String Borrower = request.getParameter("Borrower");
        String AdminCheckerId = request.getParameter("AdminCheckerId");
        String BorrowBeforeDate = request.getParameter("BorrowBeforeDate");
        String BorrowAfterDate = request.getParameter("BorrowAfterDate");
        String ReturnBeforeDate = request.getParameter("ReturnBeforeDate");
        String ReturnAfterDate = request.getParameter("ReturnAfterDate");
        String ItemType = request.getParameter("ItemType");
        String ReceiptStatus = request.getParameter("ReceiptStatus");
        String RecordStatus = request.getParameter("RecordStatus");
        String ItemStatus = request.getParameter("ItemStatus");
        String lower_bound = request.getParameter("lower_bound");
        String upper_bound = request.getParameter("upper_bound");
        
        Records.generate_results(ReceiptId, item_id, item_label, Borrower, AdminCheckerId, BorrowBeforeDate, BorrowAfterDate, ReturnBeforeDate, ReturnAfterDate, ItemType, ReceiptStatus, RecordStatus, ItemStatus, out, lower_bound, upper_bound);
        
        Log log = new Log();
        HttpSession session = request.getSession();
        String description = "Records searched";
        log.log(description, request, session);
            
        out.close();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        
        
        out.close();
    }
}
