<!DOCTYPE html>
<%@ page contentType="text/html;charset=windows-1256"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256"/>
        
        <!-- CSS -->
        <link type="text/css" rel="stylesheet" href="css/main.css">
        
    </head>
    <body>
        <div id="MainContainer">
        <% request.getRequestDispatcher("nav_bar.html").include(request,response);%>
        <div id="index-table">
            <table>
                <tr>
                    <td><a href="add_item.jsp">Add Item</a></td>
                    <td><a href="items_hq">Search Items</a></td>
                </tr>
                <tr>
                    <td><a href="new_user.jsp">New User</a></td>
                    <td><a href="manage_access.jsp">Manage access</a></td>
                </tr>
                <tr>
                    <td><a href="logs.jsp">Logs</a></td>
                    <td><a href="logout">Logout</a></td>
                </tr>
            </table>
        </div>
        </div>
    </body>
</html>