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
    public static List<String> get_types(String type_search){
        List<String> types = new ArrayList<String>();
        
        String query = "SELECT types.name FROM types WHERE lower(types.name) LIKE lower('%"+ type_search +"%') OR upper(types.name) LIKE upper('%"+ type_search +"%') ";
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
