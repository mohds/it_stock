package project1;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

public class Queries {
    public Queries() {
        super();
    }
    
    public static boolean add_spec_to_type(String type, String spec){
        boolean return_me = false;
        
        if(spec == null){
            return return_me;
        }
        else if(type == null){
            return return_me;
        }
        
        String query = "INSERT INTO specs(name) VALUES('"+ spec +"') ";
        // System.out.println(query);
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            con.close();
        }
        catch(Exception e){
            // no need to handle this error, it is supposed to not add 
            // a spec when there is a conflict in names
            // System.out.println(e.toString());
        }
        String spec_id = "";
        query = "SELECT specs.id FROM specs WHERE specs.name = '"+ spec +"'";
        // System.out.println(query);
        con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()){
                spec_id = rs.getString("id");
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        String type_id = "";
        query = "SELECT types.id FROM types WHERE types.name = '"+ type +"'";
        // System.out.println(query);
        con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()){
                type_id = rs.getString("id");
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        
        query = "INSERT INTO specs_types(spec_id, type_id) VALUES('"+ spec_id +"', '"+ type_id +"') ";
        // System.out.println(query);
        con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            con.close();
            return_me = true;
        }
        catch(Exception e){
            System.out.println(e.toString());
        }        
        
        return return_me;
    }
    
    public static boolean update_type_name(String old_type_name, String new_type_name){
        boolean return_me = false;        
        if(new_type_name == null){
            return return_me;
        }
        else if(new_type_name.length() <= 0){
            return return_me;
        }                
        String query = "UPDATE types SET name = '"+ new_type_name +"' WHERE name = '"+ old_type_name +"' ";
        // System.out.println(query);
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            con.close();
            return_me = true;
        }
        catch(Exception e){
            System.out.println(e.toString());
            return return_me;
        }        
        return return_me;
    }
    public static boolean update_brand_name(String old_brand_name, String new_brand_name){
        boolean return_me = false;        
        if(new_brand_name == null){
            return return_me;
        }
        else if(new_brand_name.length() <= 0){
            return return_me;
        }                
        String query = "UPDATE brands SET name = '"+ new_brand_name +"' WHERE name = '"+ old_brand_name +"' ";
        // System.out.println(query);
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            con.close();
            return_me = true;
        }
        catch(Exception e){
            System.out.println(e.toString());
            return return_me;
        }        
        return return_me;
    }
    public static boolean update_location_name(String old_location_name, String new_location_name){
        boolean return_me = false;        
        if(new_location_name == null){
            return return_me;
        }
        else if(new_location_name.length() <= 0){
            return return_me;
        }                
        String query = "UPDATE locations SET name = '"+ new_location_name +"' WHERE name = '"+ old_location_name +"' ";
        // System.out.println(query);
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            con.close();
            return_me = true;
        }
        catch(Exception e){
            System.out.println(e.toString());
            return return_me;
        }        
        return return_me;
    }
    
    public static boolean remove_spec_from_type(String spec_id, String type){
        boolean return_me = false;
        String type_id = get_id_from_name("types", type);
        String query = "DELETE FROM specs_types WHERE spec_id = '"+ spec_id +"' AND type_id = '"+ type_id +"' ";
        // System.out.println(query);
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            con.close();
            return_me = true;
        }
        catch(Exception e){
            System.out.println(e.toString());
            return return_me;
        }        
        return return_me;
    }
    
    public static boolean delete_spec(String spec_id){
        // deletion of a spec is a 3 step process
        // first we delete the itemspecvalues entries
        // second we delete the specs_types entries
        // last we delete the specs entry
        
        boolean return_me = false;
        
        // itemspecvalues
        String query = "DELETE FROM itemspecvalues WHERE spec_id = '"+ spec_id +"' ";
        // System.out.println(query);
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
            return return_me;
        }
        
        // specs_types
        query = "DELETE FROM specs_types WHERE spec_id = '"+ spec_id +"' ";
        // System.out.println(query);
        con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
            return return_me;
        }
        
        // specs
        query = "DELETE FROM specs WHERE id = '"+ spec_id +"' ";
        // System.out.println(query);
        con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            con.close();
            // after this point, deletion was successful
            return_me = true;
        }
        catch(Exception e){
            System.out.println(e.toString());
            return return_me;
        }
        
        return return_me;
    }
    
    public static String get_receipt_id_of_item_id(String item_id){
        String receipt_id = "";
        
        String query = "SELECT records.receipt_id FROM records, receipts WHERE records.item_id = '"+ item_id +"' AND receipts.status = '0' AND records.receipt_id = receipts.id ";
        // System.out.println(query);
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                receipt_id = rs.getString("receipt_id");
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        
        return receipt_id;
    }
    
    public static void add_admins_to_notification_group(List<String> admins, String category){
        
        String query = "UPDATE admins SET admins.notification = '"+ category +"' WHERE 1=2 ";
        if(admins!= null){
            for(int i = 0 ; i < admins.size() ; i++){
                query += " OR admins.username='"+ admins.get(i) +"' ";
            }
        }
        // query check
        // System.out.println(query);              
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }
    
    public static void remove_all_notifications_of_category(String category){        
        String query = "UPDATE admins SET admins.notification = 'NO' WHERE admins.notification = '"+ category +"' ";
        // query check
        // System.out.println(query);              
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }
    
    public static List<String> get_usernames(String table){
        List<String> usernames = new ArrayList<String>();        
        String query = "SELECT username FROM "+ table +"";
        // System.out.println(query);
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                usernames.add(rs.getString("username"));
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        return usernames;
    }
    
    public static List<String> get_admins_of_email_cateogry(String notification_type){
        List<String> emails = new ArrayList<String>();        
        String query = "SELECT username FROM admins WHERE notification='"+ notification_type +"' ";
        // System.out.println(query);
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                emails.add(rs.getString("username"));
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        
        return emails;
    }
    
    public static List<String> get_emails(String notification_type){
        List<String> emails = new ArrayList<String>();        
        String query = "SELECT email FROM admins WHERE notification='"+ notification_type +"' ";
        // System.out.println(query);
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                emails.add(rs.getString("email"));
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        
        return emails;
    }
    
    
    public static boolean spec_exists(String spec){
        boolean return_me = false;
        
        String query = "SELECT id FROM specs where specs.name = '"+ spec +"' ";
        // System.out.println(query);
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()){
                return_me = true;
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        
        return return_me;
    }
    
    public static boolean save_specs_names(List<Spec> specs, String type){
        boolean return_me = false;
        
        for(int i = 0 ; i < specs.size() ; i++){
            Spec spec = specs.get(i);
            String spec_id = spec.get_spec_id();
            String spec_name = spec.get_spec_name();
            
            if(spec_exists(spec_name)){
                
            }
            else{
                String query = "UPDATE specs SET specs.name = '"+ spec_name +"' WHERE specs.id = '"+ spec_id +"' ";
                // query check
                // System.out.println(query);              
                Connection con = connect_to_db();
                try{
                    Statement stmt = con.createStatement();
                    stmt.executeUpdate(query);
                    con.close();
                }
                catch(Exception e){
                    System.out.println(e.toString());
                }
            }            
            
        }
        
        return return_me;
    }
    
    public static List<String> get_item_labels_filtered(String item_label){
        List<String> labels = new ArrayList<String>();
            
        String query = "SELECT items.label FROM items WHERE lower(label) LIKE lower('%"+ item_label +"%') OR upper(label) LIKE ('%"+ item_label +"%') ";
        // System.out.println(query);
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                String label = rs.getString("label");
                if(!(label.contains("!$#"))){
                    labels.add(rs.getString("label"));
                }
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        
        return labels;
    }
    public static String get_id_from_username(String username){
        String id = "";
        String query = "SELECT id FROM admins WHERE username =  '"+ username +"' ";
        // System.out.println(query);
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                id = rs.getString("id");
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        
        return id;
    }
    
    public static List<String> get_ids_filtered(String id, String table){
        List<String> ids = new ArrayList<String>();
            
        String query = "SELECT id FROM "+ table +" WHERE id LIKE '%"+ id +"%' ";
        // System.out.println(query);
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                ids.add(rs.getString("id"));
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        
        return ids;
    }
    public static void add_type(String type, String[] specs){
        
        if(specs != null){
            // deal with '+' chars
            for(int i = 0 ; i < specs.length ; i++){
                specs[i] = specs[i].replace('+', ' ');
            }
        }
        type = type.replace('+', ' ');

        String query = "INSERT INTO types(name) VALUES('"+ type +"')";
        // query check
        // System.out.println(query);              
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        
        String type_id = "type ID not found.";        
        query = "SELECT id FROM types WHERE types.name = '"+ type +"' ";
        // System.out.println(query);
        con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()){
                type_id = rs.getString("id");
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        
        if(specs != null) {
            // add specs
            for(int i = 0 ; i < specs.length ; i++){
                insert_into_table("specs", specs[i]);
            }
            
            // add to specs_types
            for(int i = 0 ; i < specs.length ; i++) {            
                String spec_id = get_id_from_name("specs", specs[i]);            
                query = "INSERT INTO specs_types(spec_id, type_id) VALUES('"+ spec_id +"','"+ type_id +"')";
                // query check
                // System.out.println(query);              
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
        }
    }
    
    
    public static void insert_into_table(String table, String name){
        
        String query = "INSERT INTO "+ table +" (name) VALUES('"+ name +"')";
        // query check
        // System.out.println(query);              
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }
    
    public static List<String> get_names(String table){
        List<String> names = new ArrayList<String>();
              
        String query = "SELECT name FROM "+ table +" ORDER BY id ASC";
        // System.out.println(query);
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                names.add(rs.getString("name"));
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        
        return names;
    }
    public static List<String> get_sorted_names(String table){
        List<String> names = new ArrayList<String>();
              
        String query = "SELECT name FROM "+ table +" ORDER BY NAME ASC";
        // System.out.println(query);
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                names.add(rs.getString("name"));
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        
        return names;
    }
    
    public static boolean add_item(String label, String location, String brand, String type, String serial_number, String condition, String[] specs_names, String[] specs_values, String model, String notes){
        
        boolean return_me = false;
        
        String location_id = get_id_from_name("locations", location);
        String brand_id = get_id_from_name("brands", brand);
        String type_id = get_id_from_name("types", type);
        String condition_id = get_id_from_name("item_conditions", condition);
        
        // check if label / serial exist
        Access access = new Access();
        if(access.label_exists(label) && label != null){
            return false;
        }
        else if(access.serial_exists(serial_number) && serial_number != null){
            return false;
        }
        
        // now add item
        String query = "INSERT INTO items (label, location_id, brand_id, type_id, serial_number, condition_id, model, notes) VALUES('"+ label +"','"+ location_id +"','"+ brand_id +"','"+ type_id +"','"+ serial_number +"','"+ condition_id +"', '"+ model +"', '"+ notes +"')";  
        // query check
        // System.out.println(query);              
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            con.close();
            return_me = true; // at this point the item have been added
        }
        catch(Exception e){
            System.out.println(query);
            System.out.println(e.toString());
            return false;
        }
        
        // wait a second
        try{
            Thread.sleep(1000);
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        
        
        if(specs_names != null){
            // we will now get the added item's id
            // not the best practice
            // migrate to PL SQL in updates
            String item_id = "Item ID not found.";        
            query = "SELECT items.id FROM items WHERE rowid=(select max(rowid) from items)";
            // System.out.println(query);
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
        
            // now add specs and their values
            for(int i = 0 ; i < specs_names.length ; i++){
                if(specs_names[i] != null && specs_values[i] != null){
                    if(specs_values[i].length() > 0){ // not to add empty fields
                        String spec_id = get_id_from_name("specs", specs_names[i]);
                        query = "INSERT INTO itemspecvalues (value, spec_id, item_id) VALUES('"+ specs_values[i] +"','"+ spec_id +"','"+ item_id +"')";  
                        // query check
                        // System.out.println(query);
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
                }
            }
        }
        else{
            return_me = true;
        }
        
        return return_me;
    }
    
    public static String get_id_from_name(String table, String name){
        String id = "not_found";
        
        name = name.replace("+", " ");
        
        String query = "SELECT id FROM "+ table +" WHERE upper(name) = upper('"+ name +"') ";
        // System.out.println(query);
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                id = rs.getString("id");
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
            System.out.println(query);
        }
        
        return id;
    }
    
    public static List<String> get_specs_of_type(String type){
        
        List<String> specs = new ArrayList<String>();
        String query = "SELECT specs.name FROM types, specs, specs_types WHERE types.name  = '"+ type +"' AND specs_types.spec_id = specs.id AND specs_types.type_id = types.id ";
        // System.out.println(query);
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                String spec = rs.getString("name");
                specs.add(spec);
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        
        return specs;
    }
    public static List<String> get_names_filtered(String search, String table){
        List<String> types = new ArrayList<String>();
        
        String query = "SELECT name FROM "+ table +" WHERE lower(name) LIKE lower('%"+ search +"%') OR upper(name) LIKE upper('%"+ search +"%') ";
        // System.out.println(query);
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                String type = rs.getString("name");
                types.add(type);
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        
        return types;
    }
    
    private static Connection connect_to_db(){
        return Connect.connect_to_db();
    }
}
