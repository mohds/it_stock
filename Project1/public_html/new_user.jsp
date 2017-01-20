<!DOCTYPE html>
<%@ page contentType="text/html;charset=windows-1256"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256"/>
        <title>new_user</title>
    </head>
    <body>
        <div id='MainContainer'>
            <div class='new-user form'>
                <h1>New User</h1>
                <form action='add_user' method='POST'>
                    <span>Name:</span><input type='text' name='name'><br>
                    <span>Username:</span><input type='text' name='username'><br>
                    <span>Password:</span><input type='password' name='password'><br>
                    <input type='submit' value='Submit'>
                </form>
            </div>
        </div>
    </body>
</html>