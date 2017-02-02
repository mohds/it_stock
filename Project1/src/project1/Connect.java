package project1;

import java.sql.Connection;
import java.sql.DriverManager;

import java.util.List;

public class Connect {
    
    public Connection connect(){
                
        String url="jdbc:oracle:thin:C##ITSTOCK/itst0ck@10.10.10.5:1521:orcl";
        
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
        String url="jdbc:oracle:thin:C##ITSTOCK/itst0ck@10.10.10.5:1521:orcl";
        
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
        String url="jdbc:oracle:thin:C##super/oracl3@10.10.10.5:1521:orcl";
        
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
