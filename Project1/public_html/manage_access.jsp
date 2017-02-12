<!DOCTYPE html>
<%@ page contentType="text/html;charset=windows-1256"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256"/>
		
        <!-- CSS -->
        <link type="text/css" rel="stylesheet" href="css/main.css">
        <link type="text/css" rel="stylesheet" href="css/jquery-ui.css">
        <link type="text/css" rel="stylesheet" href="css/jquery-style.css">
        
        <!-- javascript -->
        <script src="js/jquery.min.js"></script>
        <script src="js/jquery-1.12.4.js"></script>
        <script src="js/jquery-ui.js"></script>
        <script src="js/manage_access.js"></script>
	
        <!-- java -->
        <%@ page import="java.util.ArrayList" %>
        <%@ page import="java.util.List" %>
        <%@ page import="project1.Access" %>
        
    </head>
    <body>
        <div id="MainContainer">
        <% request.getRequestDispatcher("nav_bar.html").include(request,response);%>
            
            <span id="message-box"></span>
            <div id="management-container">
                <div id="management-table">
                    <h1>Manage Users</h1>
                    <% 
                        List<String> admins = new ArrayList<String>();
                        Access access = new Access();
                        List<String> access_groups = new ArrayList<String>();
                        
                        access_groups = access.get_access_groups();
                        
                        out.println("<table id=\"management_table\">");
                        out.println("<th>Admin</th><th>Access Level</th>");
                        admins = access.get_admins();
                        for(int i = 0 ; i < admins.size() ; i++){
                            String access_level = "No access level";
                            String admin = admins.get(i);
                            access_level = access.get_access_level(admin);
                            
                            out.println("<tr>");
                            out.println("<td class=\"admins\">"+ admin +"</td>");
                            out.println("<td>");
                            out.println("<select class=\"access_levels\" id=\"admin_"+ admin +"\">");
                            for(int j = 0 ; j < access_groups.size(); j++){
                                String current_access_group = access_groups.get(j);
                                if(current_access_group.contains(access_level)){
                                    out.println("<option selected>"+ current_access_group +"</option>");
                                }
                                else {
                                    out.println("<option>"+ current_access_group +"</option>");
                                }
                            }
                            out.println("</select>");
                            out.println("</td>");                        
                            out.println("</tr>");
                        }
                        out.println("</table>");
                        out.println("<button id=\"SaveAccessGroups\">Save</button>");
                    %>
                </div>
                <div id="methods-container">
                    <h1>Manage Methods</h1>
                    <table id="methods-table">
                        <tr id="access-group-row">
                            <label>Access Group: </label><select id="access-groups" onchange="check_boxes()"><option value=""></option></select>
                        </tr>
                        <tr id="methods-row">
                        </tr>                        
                    </table>
                    <button id="AddMethod">Add Method</button><button id="SaveMethods">Save</button>
                </div>
            </div>
            <div id="NewMethodDialog" title="New Method">
                <input type="text" id="NewMethod" />
                <button id="AddNewMethod">Add</button>
            </div>
        </div>
    </body>
</html>