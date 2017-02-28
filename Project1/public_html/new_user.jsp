<!DOCTYPE html>
<%@ page contentType="text/html;charset=windows-1256"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256"/>
        <title>new_user</title>
        
        <!-- Javascript -->
        <script src="js/jquery.min.js"></script>
        <script src="js/user_check.js"></script>
        
        <!-- CSS -->
        <link href="css/main.css" type="text/css" rel="stylesheet">
        <link rel="icon" href="images/logo_image.png">
        
    </head>
    <body>
        <div id='MainContainer'>
        <% request.getRequestDispatcher("nav_bar.html").include(request,response);%>
            <div id='new-user-form'>
                <h1>New User</h1>
                <form action='add_user' method='POST'>
                    <div id="new-user-row"><span>Name:</span><input type='text' name='name'></div><br>
                    <div id="new-user-row"><span>Username:</span><input id='username' type='text' name='username'><span id='user-result'></span></div><br>
                    <div id="new-user-row"><span>Email:</span><input id='email' type='text' name='email'><span id='email-result'></span></div><br>
                    <div id="new-user-row"><span>Password:</span><input type='password' name='password'></div><br>
                    <button type='submit'>Submit</button>
                </form>
            </div>
        </div>
    </body>
</html>