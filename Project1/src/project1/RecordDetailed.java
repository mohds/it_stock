package project1;

public class RecordDetailed {
    private String record_id, admin_checker, item_id, item_label, item_type, borrower, borrow_date, return_date, status, receipt_id, record_notes, receipt_notes;
    
    public String get_item_label(){
        return item_label;
    }
    public String get_admin(){
        return admin_checker;
    }
    public String get_item_id(){
        return item_id;
    }
    public String get_item_type(){
        return item_type;
    }
    public String get_record_id(){
        return record_id;
    }
    public String get_borrower(){
        return borrower;
    }
    public String get_borrow_date(){
        return borrow_date;
    }
    public String get_return_date(){
        return return_date;
    }
    public String get_status(){
        return status;
    }
    public String get_receipt_id(){
        return receipt_id;
    }
    public String get_record_notes(){
        return record_notes;
    }
    public String get_receipt_notes(){
        return receipt_notes;
    }
    
    public void set_item_label(String s){
        item_label = s;
    }
    public void set_item_id(String s){
        item_id = s;
    }
    public void set_item_type(String s){
        item_type = s;
    }
    public void set_record_id(String s){
        record_id = s;
    }
    public void set_borrower(String s){
        borrower = s;
    }
    public void set_borrow_date(String s){
        borrow_date = s;
    }
    public void set_return_date(String s){
        return_date = s;
    }
    public void set_status(String s){
        status = s;
    }
    public void set_receipt_id(String s){
        receipt_id = s;
    }
    public void set_admin(String s){
        admin_checker = s;
    }
    public void set_record_notes(String s){
        record_notes = s;
    }
    public void set_receipt_notes(String s){
        receipt_notes = s;
    }
    
    public RecordDetailed(String record_id, String admin_checker, String item_id, String item_label, String item_type, String borrower, String borrow_date, String return_date, String status, String receipt_id, String record_notes, String receipt_notes) {
        super();
        set_record_id(record_id);
        set_admin(admin_checker);
        set_item_id(item_id);
        set_item_label(item_label);
        set_item_type(item_type);
        set_borrower(borrower);
        set_borrow_date(borrow_date);
        set_return_date(return_date);
        set_status(status);
        set_receipt_id(receipt_id);
        set_record_notes(record_notes);
        set_receipt_notes(receipt_notes);
    }
}
