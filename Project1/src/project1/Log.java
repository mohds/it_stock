package project1;

import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.ResultSet;
import java.sql.Statement;

import java.text.DateFormat;

import java.text.SimpleDateFormat;

import java.util.Date;

import javax.servlet.*;
import javax.servlet.http.*;

public class Log {
    public Log() {
        super();
    }
    
    public void log(String description, HttpServletRequest request, HttpSession session){
        String ip = request.getRemoteAddr();
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String time = dateFormat.format(date);
        String username = (String)session.getAttribute("username");
        
        String query = "INSERT INTO IT_STOCK_LOGS (DESCRIPTION, DATETIME, IP, USERNAME) VALUES ('"+ description +"',  TO_TIMESTAMP('"+ time +"','DD/MM/YYYY HH24:MI:SS.FF'), '"+ ip +"', '"+ username +"')";
        
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
    
    public void generate_results_table(String username, String ip, String description, String date_before, String date_after, String bottom_limit, String top_limit, PrintWriter out){
        String query = "SELECT id,username, date_time, ip, description FROM LOGS WHERE 1=1 ";
        
        if(!(username.length() == 0)){
            query += "AND username LIKE '%"+ username +"%' ";
        }
        if(!(ip.length() == 0)){
            query += "AND ip LIKE '%"+ ip +"%' ";
        }
        if(!(description.length() == 0)){
            query += "AND description LIKE '%"+ description +"%' ";
        }        
        if(!(date_before.length() == 0)){
            try{
                query += "AND date_time <  TO_DATE('"+ date_before +"','MM/DD/yyyy') ";
                
            }
            catch (Exception e){
                    System.out.println(e.toString());
            }
        }
        if(!(date_after.length() == 0)){
            try{
                query += "AND date_time > TO_DATE('"+ date_after +"','MM/DD/yyyy') ";
                
            }
            catch (Exception e){
                    System.out.println(e.toString());
            }
        }
        query += " ORDER BY id DESC";
        
        // add limits to query
        query = "SELECT * FROM(SELECT id,username, date_time, ip, description, ROWNUM rn FROM (" + query + ")) WHERE rn >= "+ bottom_limit +" AND rn < "+ top_limit +"";
        
        // query check
        // System.out.println(query);        
        
        out.println("<table>");
        out.println("<tr>");
        out.println("<th>ID</th>");
        out.println("<th>Username</th>");
        out.println("<th>Datetime</th>");
        out.println("<th>IP</th>");
        out.println("<th>Description</th>"); 
        out.println("</tr>");
        
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                String id = rs.getString("id");
                String username_ = rs.getString("username");
                String date_time = rs.getString("date_time");
                String ip_ = rs.getString("ip");
                String description_ = rs.getString("description");
                
                out.println("<tr>");
                out.println("<td>"+ id +"</td>");
                out.println("<td>"+ username_ +"</td>");
                out.println("<td>"+ date_time +"</td>");
                out.println("<td>"+ ip_ +"</td>");
                out.println("<td>"+ description_ +"</td>");
                out.println("</tr>");
                
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }    
        
        out.println("</table>");
                
    }
    
    private Connection connect_to_db(){
        Connect connect = new Connect();
        return connect.connect_to_log_db();
    }    
}
