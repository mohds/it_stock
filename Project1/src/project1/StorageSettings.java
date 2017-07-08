package project1;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;

import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

public class StorageSettings {
    public StorageSettings() {
        super();
    }
    public static void write_setting(String name, String value){
        boolean name_exists = false;
        String setting_id = "";
        String query = "SELECT SETTINGS.id FROM SETTINGS WHERE SETTINGS.name='"+ name +"'";
        Connection con = Connect.connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()){
                name_exists = true;
                setting_id = rs.getString("id");
                con.close();
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        
        if(name_exists){
            query = "UPDATE SETTINGS SET value='"+ value +"' WHERE id='"+ setting_id +"'";
            con = Connect.connect_to_db();
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
            query = "INSERT INTO SETTINGS (name, value) VALUES ('"+ name +"', '"+ value +"')";
            con = Connect.connect_to_db();
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
    
    public static String read_setting(String name){
        String value="SETTING NOT FOUND";
        String query = "SELECT SETTINGS.value FROM SETTINGS WHERE SETTINGS.name='"+ name +"'";
        Connection con = Connect.connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()){
                value = rs.getString("value");
                con.close();
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        return value;
    }
}
