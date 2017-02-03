package project1;

public class Records {
    public Records() {
        super();
    }
    
    public static void generate_results(String ReceiptId, String Borrower, String Receiver, String BeforeDate, String AfterDate, String ItemType, String ReceiptStatus, String ItemStatus){
        String query = "SELECT records.id, clients.name, records.borrow_datetime, records.return_datetime, receipts.status FROM items, records, clients, admins, types WHERE 1=1 ";
        
        if(!(ReceiptId.length() == 0)){
            query += "AND Receipts.id LIKE '%"+ ReceiptId +"%' ";
        }
        if(!(Borrower.length() == 0)){
            query += "AND clients.name LIKE '%"+ Borrower +"%' ";
        }
        if(!(Receiver.length() == 0)){
            query += "AND admins.name LIKE '%"+ Receiver +"%' ";
        }
        if(!(BeforeDate.length() == 0)){
            try{
                query += "AND date_time <  TO_DATE('"+ BeforeDate +"','MM/DD/yyyy') ";
            }
            catch (Exception e){
                System.out.println(e.toString());
            }
        }
        if(!(AfterDate.length() == 0)){
            try{
                query += "AND date_time >  TO_DATE('"+ AfterDate +"','MM/DD/yyyy') ";
            }
            catch (Exception e){
                System.out.println(e.toString());
            }
        }
        if(!(ItemType.length() == 0)){
            query += "AND types.name LIKE '%"+ ItemType +"%' ";
        }
        if(!(ReceiptStatus.length() == 0)){
            query += "AND receipts.status LIKE '%"+ ReceiptStatus +"%' ";
        }
        if(!(ItemStatus.length() == 0)){
            query += "AND items.status LIKE '%"+ ItemStatus +"%' ";
        }
        
        // add foreign keys
        query += "AND receipts.id = records.receipt_id AND clients.id = records.client_borrower_id AND admins.id = admin_checker_id AND items.id = records.item_id AND types.id = items.type_id ";        
        
        // query check
        System.out.println(query);

    }
}
