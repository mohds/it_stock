package project1;

public class Record {
    
    private String record_id, item_label, item_type, borrower, borrow_date, return_date, status;
    
    public String get_item_label(){
        return item_label;
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
    
    public void set_item_label(String s){
        item_label = s;
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
    
    public Record(String record_id, String item_label, String item_type, String borrower, String borrow_date, String return_date, String status) {
        super();
        set_record_id(record_id);
        set_item_label(item_label);
        set_item_type(item_type);
        set_borrower(borrower);
        set_borrow_date(borrow_date);
        set_return_date(return_date);
        set_status(status);
    }
}
