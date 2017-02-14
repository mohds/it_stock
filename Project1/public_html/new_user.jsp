<!DOCTYPE html>
<%@ page contentType="text/html;charset=windows-1256"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256"/>
        <title>new_user</title>
        <script src="js/jquery.min.js"></script>
        <script src="js/user_check.js"></script>
    </head>
    <body>
        <div id='MainContainer'>
        <% request.getRequestDispatcher("nav_bar.html").include(request,response);%>
            <div class='new-user form'>
                <h1>New User</h1>
                <form action='add_user' method='POST'>
                    <span>Name:</span><input type='text' name='name'><br>
                    <span>Username:</span><input id='username' type='text' name='username'><span id='user-result'></span><br>
                    <span>Email:</span><input id='email' type='text' name='email'><span id='email-result'></span><br>
                    <span>Password:</span><input type='password' name='password'><br>
                    <input type='submit' value='Submit'>
                </form>
            </div>
        </div>
    </body>
</html>