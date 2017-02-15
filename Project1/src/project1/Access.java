package project1;


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;

import org.omg.CORBA.CharSeqHelper;

public class Access {
    public Access() {
        super();
    }
    
    public boolean delete_user(String username){
        boolean return_me = false;
                
        // delete user
        String query = "DELETE FROM admins WHERE admins.username='"+ username +"'";
        Connection con = connect_to_db();
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
    
    public boolean update_access_groups(String[] admins, String[] access_levels){
        boolean return_me = false;
        for(int i = 0 ; i < admins.length ; i++){
            String access_group_id = get_id_of_access_group(access_levels[i]);
            String query = "UPDATE admins SET admins.access_group_id ='"+ access_group_id +"' WHERE admins.username = '"+ admins[i] +"' ";
            Connection con = connect_to_db();
            try{
                Statement stmt = con.createStatement();
                stmt.executeUpdate(query);
                con.close();
                return_me = true;
            }
            catch(Exception e){
                return_me = false;
                System.out.println(e.toString());
                return return_me;
            }
        }
        return return_me;
    }
    public void generate_hidden_data(String[] users, String role_to_manage, PrintWriter out){
        if(users != null){
            for(int i = 0 ; i < users.length ; i++){
                out.println("<input type='hidden' name='users' value='"+ users[i] +"'>");
            }
        }
        if(role_to_manage != null){
            out.println("<input type='hidden' name='role_to_manage' value='"+ role_to_manage +"'>");
        }
    }
    public void delete_courses_of_instructors(List<String> user_ids){
        
        for(int i = 0 ; i < user_ids.size() ; i++){
            String query = "UPDATE courses SET courses.status ='Deleted' WHERE EXISTS(SELECT instructors.id FROM instructors WHERE courses.instructor_id_fk = instructors.id AND instructors.person_id_fk = '"+ user_ids.get(i) +"')";
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
    public void delete_applications_of_user_ids(List<String> user_ids){
        
        for(int i = 0 ; i < user_ids.size() ; i++){
            String query = "UPDATE applications SET applications.status ='Deleted' WHERE EXISTS(SELECT students.id FROM students WHERE applications.student_id_fk = students.id AND students.person_id_fk = '"+ user_ids.get(i) +"')";
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
    public void generate_user_applications(List<String> user_ids, PrintWriter out){
        out.println("<table>");
        out.println("<tr>");
        out.println("<th>ID</th>");
        out.println("<th>Name</th>");
        out.println("<th>Course Name</th>");
        out.println("<th>Status</th>");
        out.println("</tr>");
        for(int i = 0 ; i < user_ids.size() ; i++){
            String query = "SELECT applications.id, people.first_name, people.last_name,courses.name, applications.status FROM applications,students,people,courses WHERE students.person_id_fk = '"+user_ids.get(i)+"' AND applications.status != 'Deleted' AND people.id = students.person_id_fk AND applications.student_id_fk = students.id AND courses.id = applications.course_id_fk";
            System.out.println(query);
            Connection con = connect_to_db();
            try{
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while(rs.next()){
                    String id = rs.getString("id");
                    String name = rs.getString("first_name") + " " + rs.getString("last_name");
                    String course_name = rs.getString("name");
                    String status = rs.getString("status");
                    
                    out.println("<tr>");
                    out.println("<td>"+ id +"</td>");
                    out.println("<td>"+ name +"</td>");
                    out.println("<td>"+ course_name +"</td>");
                    out.println("<td>"+ status +"</td>");
                    out.println("</tr>");
                }
            }
            catch(Exception e){
                System.out.println(e.toString());
            }
        }
        out.println("</table>");
    }
    public void generate_instructors_courses(List<String> user_ids, PrintWriter out){
        out.println("<table>");
        out.println("<tr>");
        out.println("<th>ID</th>");
        out.println("<th>Name</th>");
        out.println("<th>Course Name</th>");
        out.println("<th>Status</th>");
        out.println("</tr>");
        for(int i = 0 ; i < user_ids.size() ; i++){
            String query = "SELECT courses.id, people.first_name, people.last_name,courses.name, courses.status FROM instructors,people,courses WHERE instructors.person_id_fk = '"+user_ids.get(i)+"' AND courses.status != 'Deleted' AND people.id = instructors.person_id_fk AND courses.instructor_id_fk = instructors.id";
            Connection con = connect_to_db();
            try{
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while(rs.next()){
                    String id = rs.getString("id");
                    String name = rs.getString("first_name") + " " + rs.getString("last_name");
                    String course_name = rs.getString("name");
                    String status = rs.getString("status");
                    
                    out.println("<tr>");
                    out.println("<td>"+ id +"</td>");
                    out.println("<td>"+ name +"</td>");
                    out.println("<td>"+ course_name +"</td>");
                    out.println("<td>"+ status +"</td>");
                    out.println("</tr>");
                }
            }
            catch(Exception e){
                System.out.println(e.toString());
            }
        }
        out.println("</table>");
    }
    public boolean users_have_applications(List<String> user_ids){
        boolean return_me = false;
        
        for(int i = 0 ; i < user_ids.size() ; i++){
            String query = "SELECT applications.id FROM applications,students WHERE students.person_id_fk = '"+ user_ids.get(i) +"' AND applications.student_id_fk = students.id AND applications.status != 'Deleted'";
            Connection con = connect_to_db();
            try{
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while(rs.next()){
                    return_me = true;
                }
            }
            catch(Exception e){
                System.out.println(e.toString());
            }
        }
        
        return return_me;
    }
    public String get_access_level(String username){
        String access_level = "Access level not found";
        String query = "SELECT access_groups.name FROM access_groups, admins WHERE admins.access_group_id = access_groups.id AND admins.username = '"+ username +"' ";
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                access_level = rs.getString("name");
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        return access_level;
    }
    public boolean users_have_courses(List<String> user_ids){
        boolean return_me = false;
        
        for(int i = 0 ; i < user_ids.size() ; i++){
            String query = "SELECT applications.id FROM applications,instructors,courses WHERE instructors.person_id_fk = '"+ user_ids.get(i) +"' AND courses.instructor_id_fk = instructors.id AND courses.status != 'Deleted' ";
            Connection con = connect_to_db();
            try{
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while(rs.next()){
                    return_me = true;
                }
            }
            catch(Exception e){
                System.out.println(e.toString());
            }
        }
        
        return return_me;
    }
    public List<String> get_user_ids_removed_from_role(List<String> future_role_users, String role){
        
        List<String> current_role_user_ids = new ArrayList<String>();
        List<String> future_role_user_ids = new ArrayList<String>();
        List<String> user_ids_removed_from_role = new ArrayList<String>();
        
        // first we get current role users
        String query = "SELECT person_id_fk FROM "+ role +"";
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                String person_id = rs.getString("person_id_fk");
                current_role_user_ids.add(person_id);
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        
        // now we get ids of role users to be
        for(int i = 0 ; i < future_role_users.size() ; i++){
            query = "SELECT id FROM people WHERE username = '"+ future_role_users.get(i) +"'";
            con = connect_to_db();
            try{
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while(rs.next()){
                    String id = rs.getString("id");
                    future_role_user_ids.add(id);
                }
            }
            catch(Exception e){
                System.out.println(e.toString());
            }
        }
        
        // now to filter out removed users from role
        for(int i = 0 ; i < current_role_user_ids.size() ; i++){
            if(!(future_role_user_ids.contains(current_role_user_ids.get(i)))){
                user_ids_removed_from_role.add(current_role_user_ids.get(i));
            }
        }
        
        return user_ids_removed_from_role;
    }
    public String get_full_name(String username){            
        String query = "SELECT first_name, last_name FROM people WHERE people.username='"+ username +"'";
        Connection con = connect_to_db();
        String full_name = "";
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                
                full_name = first_name + " " + last_name;
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        
        return full_name;
    }
    
    public List<String> get_roles(){
        List<String> roles = new ArrayList<String>();
        
        roles.add("ADMINS");
        roles.add("INSTRUCTORS");
        roles.add("STUDENTS");
        
        return roles;
    }
    
    public boolean label_exists(String label){
        boolean exists = false;
        String query = "SELECT id FROM items WHERE lower(label) = lower('"+ label +"') OR upper(label) = upper('"+ label +"')";
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                exists = true;
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        
        return exists;   
    }
    public boolean serial_exists(String serial){
        boolean exists = false;
        String query = "SELECT id FROM items WHERE lower(serial_number) = lower('"+ serial +"') OR upper(serial_number) = upper('"+ serial +"')";
        //System.out.println(query);
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                exists = true;
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        
        return exists;   
    }
    
    public boolean user_exists(String user, String column_to_check){
        boolean exists = false;
        String query = "SELECT id FROM admins WHERE admins."+ column_to_check +" = '"+ user +"'";
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                exists = true;
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        
        return exists;   
    }
    
    public boolean has_access(String user, String method){
        
        boolean return_me = false;
                
        String query = "SELECT * FROM admins, access_groups, access_groups_methods, methods WHERE admins.username = '"+ user +"' AND methods.name = '" + method  + "' AND admins.access_group_id = access_groups.id AND access_groups.id = access_groups_methods.access_group_id AND methods.id = access_groups_methods.method_id";
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                return_me = true;
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        return return_me;
    }
    
    public String filter_sql_injection(String input){
        String fixed_input = "";
        
        fixed_input = input;
        fixed_input = fixed_input.replace("\'", "");
        fixed_input = fixed_input.replace("\"", "");
        
        return fixed_input;
    }
    
    public boolean login(String user, String password){
        boolean return_me = false;
        String password_hash = "";
        String check_column = "username";
        
        Hash hash = new Hash();
        try{
            password_hash = hash.hash(password);
        }
        catch(Exception ex){
            System.out.println("Unable to get hash, error: "+ ex.toString());
        }
        
        String query = "SELECT admins.id FROM admins WHERE admins."+ check_column +" = '"+ user +"' AND admins.password_hash='"+ password_hash +"'";
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()){
                return_me = true;
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        
        return return_me;
    }
    
    public String get_user_from_email(String email){
        String user = "";
        
        String query = "SELECT people.username FROM people WHERE people.email='"+ email +"'";
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()){
                user = rs.getString("username");
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        
        return user;
    }
    
    public boolean set_access_group(String username, String access_group){
        boolean return_me = false;
        int access_group_id = 0;
        int user_id = 0;
        
        // find the id of the required access_group
        String query = "SELECT access_groups.id FROM access_groups WHERE access_groups.name = '"+ access_group +"'";
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next()){
                access_group_id = rs.getInt("id");
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
                        
        // update corresponding user
        query = "UPDATE people SET people.access_group_id_fk ="+ access_group_id +" WHERE people.username= '"+ username +"'";
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
    
    public boolean add_access_group(String access_group){
        boolean return_me = false;
        
        System.out.println("access_group: " + access_group);
        
        // find the id of the required access_group
        String query = "INSERT INTO access_groups (name) VALUES ('"+ access_group +"')";
        
        Connection con = connect_to_db();
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
    public List<String> get_admins(){
        List<String> users = new ArrayList<String>();
        // find the id of the required access_group
        String query = "SELECT admins.username FROM admins";
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                users.add(rs.getString("username"));
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        return users; 
    }
    public List<String> get_user_ids(){
        List<String> users = new ArrayList<String>();
        // find the id of the required access_group
        String query = "SELECT people.id FROM people";
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                users.add(rs.getString("id"));
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        return users; 
    }
    public List<String> get_methods(){
        List<String> methods = new ArrayList<String>();
        // find the id of the required access_group
        String query = "SELECT methods.name FROM methods";
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                methods.add(rs.getString("name"));
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        return methods; 
    }
    public List<String> get_methods_of_access_group(String access_group){
        List<String> methods_of_access_group = new ArrayList<String>();
        // find the id of the required access_group
        String query = "SELECT methods.name FROM access_groups_methods, methods, access_groups WHERE access_groups.name = '"+ access_group +"' AND access_groups_methods.access_group_id = access_groups.id AND access_groups_methods.method_id = methods.id";
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                methods_of_access_group.add(rs.getString("name"));
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        return methods_of_access_group;
    }
    public List<String> get_access_groups(){
        List<String> access_groups = new ArrayList<String>();
        
        // find the id of the required access_group
        String query = "SELECT access_groups.name FROM access_groups";
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                access_groups.add(rs.getString("name"));
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        
        return access_groups;
    }
    public void generate_users_list(PrintWriter out, HttpServletResponse response, HttpServletRequest request, String access_group){
        List<String> users = new ArrayList<String>();
        users = get_admins();
        
        for(Iterator<String> i = users.iterator(); i.hasNext(); ){
            String user = i.next();
            if(user_belongs_to_access_group(user, access_group)){
                out.println(user+"<input type='checkbox' name='users' value='"+ user +"' checked='checked'><br>");
            }
            else{
                out.println(user+"<input type='checkbox' name='users' value='"+ user +"'><br>");
            }
        }
    }
    public void generate_users(PrintWriter out, HttpServletResponse response, HttpServletRequest request, String role){
        List<String> users = new ArrayList<String>();
        users = get_admins();
        
        for(Iterator<String> i = users.iterator(); i.hasNext(); ){
            String user = i.next();
            if(user_belongs_to_role(user, role)){
                out.println(user+"<input type='checkbox' name='users' value='"+ user +"' checked='checked'><br>");
            }
            else{
                out.println(user+"<input type='checkbox' name='users' value='"+ user +"'><br>");
            }
        }
    }
    public boolean user_belongs_to_role(String user, String role){
        boolean return_me = false;
        
        String user_id = get_id_of_user(user);
        
        String query = "SELECT * FROM "+ role +" WHERE "+ role +".person_id_fk = '"+ user_id +"' ";
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next()){
                return_me = true;
            }
            else{
                return_me = false;   
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        
        return return_me;
    }
    public void generate_methods_list(PrintWriter out, HttpServletResponse response, HttpServletRequest request, String access_group){
        List<String> methods = new ArrayList<String>();
        methods = get_methods();
        
        for(Iterator<String> i = methods.iterator(); i.hasNext(); ){
            String method = i.next();
            if(method_belongs_to_access_group(method, access_group)){
                out.println(method+"<input type='checkbox' name='methods' value='"+ method +"' checked='checked'><br>");
            }
            else{
                out.println(method+"<input type='checkbox' name='methods' value='"+ method +"'><br>");
            }
        }
    }
    public boolean user_belongs_to_access_group(String user, String access_group){
        boolean return_me = false;
        
        String query = "SELECT * FROM access_groups, people WHERE access_groups.name = '"+ access_group +"' AND people.username = '"+ user +"'  AND people.access_group_id_fk = access_groups.id";
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next()){
                return_me = true;
            }
            else{
                return_me = false;   
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        
        return return_me;
    }
    public boolean method_belongs_to_access_group(String method, String access_group){
        boolean return_me = false;
        
        String query = "SELECT * FROM access_groups, methods, access_groups_methods WHERE access_groups.name = '"+ access_group +"' AND methods.name = '"+ method +"'  AND access_groups_methods.access_group_id_fk = access_groups.id AND access_groups_methods.method_id_fk = methods.id ";
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next()){
                return_me = true;
            }
            else{
                return_me = false;   
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        
        return return_me;
    }
    public void generate_management_page(PrintWriter out, HttpServletResponse response, HttpServletRequest request){
        try{
        out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>PERMISSIONS</title></head>");
        out.println("<body>");
        out.println("<form action=\"change_permissions\" method=\"POST\">");
        out.println("<h2>Change Access Group</h2>");
        out.println("Username:<input type=\"text\" name=\"username\"><br>");
        out.println("Access Group:<input type=\"text\" name=\"access_group\"><br>");
        out.println("<input value=\"Submit\" type=\"submit\">");
        out.println("</form>");
        out.println("<br><br>");
        out.println("<form action=\"add_access_group\" method=\"POST\">");
        out.println("<h2>Add Access Group</h2>");
        out.println("New Access Group:<input type=\"text\" name=\"access_group_to_add\"><br>");
        out.println("<input value=\"Submit\" type=\"submit\">");
        out.println("</form>");
        out.println("<br><br>");
        out.println("<form action=\"manage_access_group\" method=\"POST\">");
        out.println("<h2>Manage Access Groups</h2>");
            
        // we have to get the list of methods for the above corresponding chosen group
        String access_group_to_manage = request.getParameter("access_group_to_manage");
        if(access_group_to_manage == null){
            access_group_to_manage = "";            
        }
        List<String> access_group_methods = new ArrayList<String>();
        
        access_group_methods = get_methods_of_access_group(access_group_to_manage);
            
        out.println("<b>"+ access_group_to_manage +"</b>");
        // we have to get the list of access groups and
        // then list them in a <select> tag
        List<String> access_groups = new ArrayList<String>();
        access_groups = get_access_groups();        
        out.println("<select name='access_group_to_manage'>");
        out.println("<option value =''></option>");
        for(Iterator<String> i = access_groups.iterator(); i.hasNext(); ){
            String access_group = i.next();
            out.println("<option value='"+ access_group +"'>"+ access_group +"</option>");
        }
        out.println("</select>");
        out.println("<input value='Show methods' type='submit'>");
        
        
        
        // show permissions of access group in a table
        
        out.println("<table>");
        for(Iterator<String> i = access_group_methods.iterator(); i.hasNext();){
            String method = i.next();
            out.println("<tr>");
            out.println("<td>"+ method +"</td>");
            out.println("</tr>");
        }       
        out.println("</table>");
        out.println("Add a method to group:<input type='text' name='method_to_add'></input>");
        out.println("<input value=\"Submit\" type=\"submit\">");
        out.println("</form>");
        out.println("</body></html>");
        out.close();
        }
        catch(Exception e){
            System.out.println(e);   
        }
    }
    public void remove_all_access_group_users(String access_group){
        
        // get id of access_group
        String access_group_id = get_id_of_access_group(access_group);
        
        String query = "UPDATE people SET access_group_id_fk = 0 WHERE people.access_group_id_fk = '"+ access_group_id +"'";
        try{
            Connection con = connect_to_db();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);                          
            
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }
    public void remove_all_access_group_methods(String access_group){
        
        // get id of access_group
        String access_group_id = get_id_of_access_group(access_group);
        
        String query = "DELETE FROM access_groups_methods WHERE access_groups_methods.access_group_id = '"+ access_group_id +"'";
        try{
            Connection con = connect_to_db();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);                          
            
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }
    public void remove_users_from_all_roles(List<String> users){                
        if(users != null){
            for(Iterator<String> i = users.iterator(); i.hasNext();){
                String user = i.next();
                String id = get_user_id(user);
                String query = "DELETE FROM ADMINS WHERE person_id_fk = "+ id +" ";
                try{
                    Connection con = connect_to_db();
                    Statement stmt = con.createStatement();
                    stmt.executeUpdate(query);                          
                    
                    con.close();
                }
                catch(Exception e){
                    System.out.println(e.toString());
                }
                query = "DELETE FROM INSTRUCTORS WHERE person_id_fk = "+ id +" ";
                try{
                    Connection con = connect_to_db();
                    Statement stmt = con.createStatement();
                    stmt.executeUpdate(query);                          
                    
                    con.close();
                }
                catch(Exception e){
                    System.out.println(e.toString());
                } 
                query = "DELETE FROM STUDENTS WHERE person_id_fk = "+ id +" ";
                try{
                    Connection con = connect_to_db();
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
    public void remove_users_from_all_roles_by_id(List<String> user_ids){                
        if(user_ids != null){
            for(Iterator<String> i = user_ids.iterator(); i.hasNext();){
                String id = i.next();
                String query = "DELETE FROM ADMINS WHERE person_id_fk = "+ id +" ";
                try{
                    Connection con = connect_to_db();
                    Statement stmt = con.createStatement();
                    stmt.executeUpdate(query);                          
                    
                    con.close();
                }
                catch(Exception e){
                    System.out.println(e.toString());
                }
                query = "DELETE FROM INSTRUCTORS WHERE person_id_fk = "+ id +" ";
                try{
                    Connection con = connect_to_db();
                    Statement stmt = con.createStatement();
                    stmt.executeUpdate(query);                          
                    
                    con.close();
                }
                catch(Exception e){
                    System.out.println(e.toString());
                } 
                query = "DELETE FROM STUDENTS WHERE person_id_fk = "+ id +" ";
                try{
                    Connection con = connect_to_db();
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
    public void remove_all_role_users(String role){
                
        String query = "DELETE FROM "+ role +" WHERE 1=1";
        try{
            Connection con = connect_to_db();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);                          
            
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }
    public void remove_role_users_by_id(String role, List<String> user_ids_to_remove){
                
        for(int i = 0 ; i < user_ids_to_remove.size() ; i++){        
            String query = "DELETE FROM "+ role +" WHERE 1=1 AND person_id_fk = '"+ user_ids_to_remove.get(i) +"' ";
            try{
                Connection con = connect_to_db();
                Statement stmt = con.createStatement();
                stmt.executeUpdate(query);                          
                
                con.close();
            }
            catch(Exception e){
                System.out.println(e.toString());
            }
        }
    }
    public void add_users_to_access_group(List<String> users_to_add, String access_group){
        // first prepare required variables
        String access_group_id_fk = "";
        List<String> methods_id_fk = new ArrayList<String>();
        
        // get access_group id
        access_group_id_fk = get_id_of_access_group(access_group);
        
        
        for(Iterator<String> i = users_to_add.iterator(); i.hasNext();){
            String user_to_add = i.next();
            String query = "UPDATE people SET people.access_group_id_fk = '"+ access_group_id_fk +"' WHERE people.username = '"+ user_to_add +"'";
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
    public void add_methods_to_access_group(List<String> methods_to_add, String access_group){
        // first prepare required variables
        String access_group_id_fk = "";
        List<String> methods_id_fk = new ArrayList<String>();
        
        // get access_group id
        access_group_id_fk = get_id_of_access_group(access_group);
        
        // get ids of methods to add
        methods_id_fk = get_ids_of_methods(methods_to_add);
        for(Iterator<String> i = methods_id_fk.iterator(); i.hasNext();){
            String method_id_fk = i.next();
            String query = "INSERT INTO access_groups_methods (access_group_id, method_id) VALUES('"+ access_group_id_fk +"', '"+ method_id_fk +"')";
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
    public void add_users_to_role(List<String> users_to_add, String role){
        // first prepare required variables
        String access_group_id_fk = "";
        List<String> users_id_fk = new ArrayList<String>();
        
        // we should get the list of already existing users in role for duplication avoidance
        List<String> users_already_in_role = new ArrayList<String>();
        String query = "SELECT person_id_fk FROM "+ role +" WHERE 1=1";
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                String user_id = "";
                user_id = rs.getString("person_id_fk");
                users_already_in_role.add(user_id);
            }            
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        
        // get ids of methods to add
        users_id_fk = get_ids_of_users(users_to_add);
        for(Iterator<String> i = users_id_fk.iterator(); i.hasNext();){
            String user_id_fk = i.next();
            if(!users_already_in_role.contains(user_id_fk)){
                query = "INSERT INTO "+ role +" (person_id_fk) VALUES('"+ user_id_fk +"')";
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
    public void add_method(String method_to_add){
        String query = "INSERT INTO methods (name) VALUES('"+ method_to_add +"')";
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
    private List<String> get_ids_of_methods(List<String> methods){
        List<String> methods_ids = new ArrayList<String>();
        
        for(Iterator<String> i = methods.iterator(); i.hasNext(); ){
            String method = i.next();
            methods_ids.add(get_id_of_method(method));
        }
        
        return methods_ids;
    }
    private List<String> get_ids_of_users(List<String> users){
        List<String> user_ids = new ArrayList<String>();
        
        for(Iterator<String> i = users.iterator(); i.hasNext(); ){
            String user = i.next();
            user_ids.add(get_id_of_user(user));
        }
        
        return user_ids;
    }
    private String get_id_of_user(String user){
        String user_id = "";
        String query = "SELECT people.id FROM people WHERE people.username = '"+ user +"'";
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next()){
                user_id = rs.getString("id");
            }
            else{
                
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        return user_id;
    }
    private String get_id_of_method(String method){
        String method_id = "";
        String query = "SELECT methods.id FROM methods WHERE methods.name = '"+ method +"'";
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next()){
                method_id = rs.getString("id");
            }
            else{
                
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        return method_id;
    }
    private String get_id_of_access_group(String access_group){
        String access_id = "";
        String query = "SELECT access_groups.id FROM access_groups WHERE access_groups.name = '"+ access_group +"'";
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next()){
                access_id = rs.getString("id");
            }
            else{
                
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        return access_id;
    }
    public boolean add_user(String name, String user, String password_hash, String email){
        boolean return_me = false;
        
        if(user.isEmpty() || name.isEmpty()){
            return return_me;
        }
        
        // now add user with corresponding mailbox
        String query = "INSERT INTO admins (name, username, password_hash, access_group_id, email, notification) VALUES('"+ name +"', '"+ user +"','"+ password_hash +"', '4', '"+ email +"', 'NO')";
        
        // query check
        //System.out.println("Query: " + query);
        
        Connection con = connect_to_db();
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
    private String get_user_id(String user){
        String user_id = "";
        String query = "SELECT PEOPLE.ID FROM PEOPLE WHERE PEOPLE.username='"+ user +"'";
        Connection con = connect_to_db();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
          
            if(rs.next()){
                user_id = rs.getString("id");
            }      
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        return user_id;
    }
    public boolean confirm_added_user(String user){
        boolean return_me = false;
        
        String query = "SELECT admins.name, admins.username, admins.password_hash FROM admins WHERE admins.username='"+ user +"'";
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
    public String password_hash(String password){
        String password_hash = "";
        Hash hash = new Hash();
        try{
            password_hash = hash.hash(password);
        }
        catch(Exception ex){
               System.out.println("Could not hash password!");
        }
        return password_hash;
    }
    private Connection connect_to_db(){
        
        Connect conn = new Connect();
        return conn.connect();
        
    }
}
