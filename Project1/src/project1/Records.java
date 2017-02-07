package project1;

import com.google.gson.Gson;

import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Records {
    @SuppressWarnings("oracle.jdeveloper.java.semantic-warning")
    public Records() {
        super();
    }
    
    public static void return_item(String record_id, String client_returner, PrintWriter out, String admin_receiver){
        
        String current_datetime = "";
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        current_datetime = dateFormat.format(date);
        
        String client_returner_id = Queries.get_id_from_name("clients", client_returner);
        String admin_receiver_id = Queries.get_id_from_username(admin_receiver);
        
        String query = "UPDATE records SET records.return_datetime = TO_TIMESTAMP('"+ current_datetime +"','DD/MM/YYYY HH24:MI:SS.FF'), records.client_returner_id = '"+ client_returner_id +"', records.admin_receiver_id = '"+ admin_receiver_id +"' WHERE records.id = '"+ record_id +"' ";
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        
        // now you need to update receipt status accordingly if all records have been returned
        // first we get the receipt ID
        String receipt_id = get_receipt_id_from_record_id(record_id);
        
        if(receipt_closed(receipt_id)){
            // update corresponding receipt status
            query = "UPDATE receipts SET receipts.status = '1' WHERE receipts.id = '"+ receipt_id +"' ";
            con = connect_to_db();
            try{
                Statement stmt = con.createStatement();
                stmt.executeUpdate(query);
                con.close();
            }
            catch(Exception e){
                System.out.println(e.toString());
            }
        }
        else{
            // do nothing
        }
        
        // update item as available
        String item_id = "";
        query = "SELECT items.id FROM items, records WHERE records.item_id = items.id AND records.id = '"+ record_id +"' ";
        con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()){
                item_id = rs.getString("id");
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        query = "UPDATE items SET items.availability = '1' WHERE items.id = '"+ item_id +"' ";
        con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        
        // send date back to webpage
        Date date_return = new Date();
        DateFormat dateFormat_return = new SimpleDateFormat("dd/MM/yyyy");
        out.println(dateFormat_return.format(date_return));
        
    }
    
    public static boolean receipt_closed(String receipt_id){
        boolean return_me = true;
        
        String query = "SELECT * FROM records, receipts WHERE  records.return_datetime IS NULL AND records.receipt_id = '"+ receipt_id +"' AND records.receipt_id = receipts.id";
        // System.out.println(query);
        
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()){
                return_me = false;
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        
        return return_me;
    }
    
    public static String get_receipt_id_from_record_id(String record_id){
        
        String receipt_id = "Receipt not found.";
        String query = "SELECT receipts.id FROM receipts, records WHERE records.id='"+ record_id +"' AND records.receipt_id = receipts.id ";
        // System.out.println(query);
        
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()){
                receipt_id = rs.getString("id");
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        
        return receipt_id;
    }
    
    public static String get_borrower_of_record(String record_id){
        
        String borrower = "Borrower not found.";
        String query = "SELECT clients.name FROM clients, records WHERE records.id = '"+ record_id +"' AND clients.id = records.client_borrower_id";
        // System.out.println(query);
        
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                borrower = rs.getString("name");
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        
        return borrower;
    }
    
    public static void generate_record_details(String record_id, PrintWriter out){
        
        // in the following query the admin returned is the one who checked and delivered the item to the client
        // the client returned is the one who borrowed the item
        String query = "SELECT items.id AS item_id, items.label, types.name AS type, clients.name AS client, admins.name AS admin, admins.username, TO_CHAR(borrow_datetime, 'DD/MM/YYYY') AS borrow_datetime, TO_CHAR(return_datetime, 'DD/MM/YYYY') AS return_datetime, receipts.id AS receipt_id FROM items, types, admins, clients, receipts, records WHERE records.id = "+ record_id +" AND items.type_id = types.id AND records.receipt_id = receipts.id AND records.admin_checker_id = admins.id AND records.client_borrower_id = clients.id AND records.item_id = items.id";
        // System.out.println(query);
        
        List<RecordDetailed> record = new ArrayList<RecordDetailed>();
        
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                String item_id = rs.getString("item_id");
                String admin_checker = rs.getString("admin");
                String borrower = rs.getString("client");
                String item_label = rs.getString("label");
                String item_type = rs.getString("type");
                String return_date = rs.getString("return_datetime");
                String borrow_date = rs.getString("borrow_datetime");
                String status = "status"; // rs.getString("status");
                String receipt_id = rs.getString("receipt_id");
                
                // make status human friendaly
                if(status.contains("0")){
                    status = "Pending";
                }
                else if(status.contains("1")){
                    status = "Done";
                }
                
                // make return date human friendly
                if(return_date == null){
                    return_date = "Pending";
                }
                else{
                    // do nothing
                }
                
                record.add(new RecordDetailed(record_id, admin_checker, item_id, item_label, item_type, borrower, borrow_date, return_date, status, receipt_id));
                
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        
        Gson gson = new Gson();
        out.println(gson.toJson(record));
    }
    
    public static void generate_results(String ReceiptId, String label, String Borrower, String AdminCheckerId, String BorrowBeforeDate, String BorrowAfterDate, String ReturnBeforeDate, String ReturnAfterDate, String ItemType, String ReceiptStatus, String ItemStatus, PrintWriter out){
        String query = "SELECT records.id, items.label, types.name AS type, clients.name AS client, TO_CHAR(borrow_datetime, 'DD/MM/YYYY') AS borrow_datetime, TO_CHAR(return_datetime, 'DD/MM/YYYY') AS return_datetime, receipts.status FROM records, clients, receipts, items, admins, types WHERE 1=1 ";
        
        if(!(ReceiptId.length() == 0)){
            query += "AND Receipts.id LIKE '%"+ ReceiptId +"%' ";
        }
        if(!(label.length() == 0)){
            query += "AND items.label LIKE '%"+ label +"%' ";
        }
        if(!(Borrower.length() == 0)){
            query += "AND clients.name LIKE '%"+ Borrower +"%' ";
        }
        if(!(AdminCheckerId.length() == 0)){
            query += "AND admins.name LIKE '%"+ AdminCheckerId +"%' ";
        }
        if(!(BorrowBeforeDate.length() == 0)){
            try{
                query += "AND records.borrow_datetime <  TO_DATE('"+ BorrowBeforeDate +"','DD/MM/yyyy') ";
            }
            catch (Exception e){
                System.out.println(e.toString());
            }
        }
        if(!(BorrowAfterDate.length() == 0)){
            try{
                query += "AND records.borrow_datetime >  TO_DATE('"+ BorrowAfterDate +"','DD/MM/yyyy') ";
            }
            catch (Exception e){
                System.out.println(e.toString());
            }
        }
        if(!(ReturnBeforeDate.length() == 0)){
            try{
                query += "AND records.return_datetime <  TO_DATE('"+ ReturnBeforeDate +"','DD/MM/yyyy') ";
            }
            catch (Exception e){
                System.out.println(e.toString());
            }
        }
        if(!(ReturnAfterDate.length() == 0)){
            try{
                query += "AND records.return_datetime >  TO_DATE('"+ ReturnAfterDate +"','DD/MM/yyyy') ";
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
            query += "AND items.availability LIKE '%"+ ItemStatus +"%' ";
        }
        
        // add foreign keys
        query += "AND receipts.id = records.receipt_id AND receipts.client_id = clients.id AND clients.id = records.client_borrower_id AND admins.id = records.admin_checker_id AND items.id = records.item_id AND types.id = items.type_id ";        
        
        // add deleted check
        query += " AND ITEMS.deleted = '0'";
        
        // query check
        // System.out.println(query);
        
        List<Record> record_list = new ArrayList<Record>();
        
        // query the db
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){                
                String record_id = rs.getString("id");
                String borrower = rs.getString("client");
                String item_label = rs.getString("label");
                String item_type = rs.getString("type");
                String return_date = rs.getString("return_datetime");
                String borrow_date = rs.getString("borrow_datetime");
                String status = rs.getString("status");
                
                // make status human friendaly
                if(status.contains("0")){
                    status = "Pending";
                }
                else if(status.contains("1")){
                    status = "Done";
                }
                
                // make return date human friendly
                if(return_date == null){
                    return_date = "Pending";
                }
                else{
                    // do nothing
                }
                
                record_list.add(new Record(record_id, item_label, item_type, borrower, borrow_date, return_date, status));
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        Gson gson = new Gson();
        out.println(gson.toJson(record_list));
    }
    
    private static Connection connect_to_db(){
        return Connect.connect_to_db();
    }
}