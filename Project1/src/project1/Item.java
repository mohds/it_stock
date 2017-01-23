package project1;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.jsp.JspWriter;


public class Item {
    public Item() {
        super();
    }    
    public void generate_combo_options(String table, JspWriter out){
        String query = "SELECT "+ table +".name FROM "+ table +" ";
        //System.out.println(query);
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                String name = rs.getString("name");
                out.println("<option value=\""+ name +"\">"+ name +"</option>");
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }
    private Connection connect_to_db(){
        Connect connect = new Connect();
        return connect.connect();
    }
}
