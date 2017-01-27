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
    
    public static boolean add_item(String label, String location, String brand, String type, String serial_number, String condition, String[] specs_names, String[] specs_values){
        
        boolean return_me = false;
        
        String location_id = get_id_from_name("locations", location);
        String brand_id = get_id_from_name("brands", brand);
        String type_id = get_id_from_name("types", type);
        String condition_id = get_id_from_name("item_conditions", condition);
        
        // now add item
        String query = "INSERT INTO items (label, location_id, brand_id, type_id, serial_number, condition_id) VALUES('"+ label +"','"+ location_id +"','"+ brand_id +"','"+ type_id +"','"+ serial_number +"','"+ condition_id +"')";  
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
        
        // wait a second
        try{
            Thread.sleep(1000);
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        
        // we will now get the added item's id
        // not the best practice
        // migrate to PL SQL in updates
        String item_id = "Item ID not found.";        
        query = "SELECT id FROM items ORDER BY id DESC FETCH FIRST 1 ROWS ONLY";
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
            String spec_id = get_id_from_name("specs", specs_names[i]);
            query = "INSERT INTO itemspecvalues (value, spec_id, item_id) VALUES('"+ specs_values[i] +"','"+ spec_id +"','"+ item_id +"')";  
            // query check
            // System.out.println(query);              
            con = connect_to_db();
            try{
                Statement stmt = con.createStatement();
                stmt.executeUpdate(query);
                return_me = true;
                con.close();
            }
            catch(Exception e){
                System.out.println(e.toString());
            }
        }
        
        return return_me;
    }
    
    public static String get_id_from_name(String table, String name){
        String id = "ID not found for table " + table + " and name " + name;
        
        name = name.replace("+", " ");
        
        String query = "SELECT id FROM "+ table +" WHERE name = '"+ name +"' ";
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
