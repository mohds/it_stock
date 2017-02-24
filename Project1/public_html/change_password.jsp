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
        <script src="js/change_password.js"></script>
        
    </head>
    <body>
        <div id="MainContainer">
            <% request.getRequestDispatcher("nav_bar.html").include(request,response);%>
            <div id="message-box"></div>
            <div id="NewPasswordForm">
                <div class="NewPasswordRow"><label>Old password: </label><input id="old_password" type="password"></div>
                <div class="NewPasswordRow"><label>New password: </label><input id="first_password" type="password"></div>
                <div class="NewPasswordRow"><label>Confirm password: </label><input id="second_password" type="password"></div>
                <div class="NewPasswordRow"><button id="save_password_button">Save</button></div>
            </div>
        </div>
    </body>
</html>