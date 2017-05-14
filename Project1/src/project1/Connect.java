package project1;

import java.sql.Connection;
import java.sql.DriverManager;

import java.util.List;

public class Connect {
    
    public Connection connect(){
                
        String url="jdbc:oracle:thin:ITSTOCK/itst0ck@140.125.4.30:1522:xe";
        
        try{
            Connection con = DriverManager.getConnection(url);
            return con;
        }
        catch(Exception e){
            System.out.println(e.toString());
            return null;
        }
    }
    public static Connection connect_to_db(){
        String url="jdbc:oracle:thin:ITSTOCK/itst0ck@140.125.4.30:1522:xe";
        
        try{
            Connection con = DriverManager.getConnection(url);
            return con;
        }
        catch(Exception e){
            System.out.println(e.toString());
            return null;
        }
    }
    public Connection connect_to_log_db(){
        String url="jdbc:oracle:thin:super/oracl3@140.125.4.30:1522:xe";
        
        try{
            Connection con = DriverManager.getConnection(url);
            return con;
        }
        catch(Exception e){
            System.out.println(e.toString());
            return null;
        }
    }
}
