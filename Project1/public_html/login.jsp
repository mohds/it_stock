<!DOCTYPE html>
<%@ page contentType="text/html;charset=windows-1256"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256"/>
        <title>login</title>
    </head>
    <body>
        <div id='MainContainer'>
            <div class='login form'>
                <form action='login' method='POST'>
                    <span>Username</span><br>
                    <input type='text' value='' name='username'><br>
                    <span>Passowrd</span><br>
                    <input type='password' value='' name='password'><br>
                    <input type='submit' value='Submit'><br>
                    <a href='#'>Forgot your password?</a><br>
                    <a href='new_user.jsp'>New User?</a>
                </form>
            </div>
        </div>
    </body>
</html>