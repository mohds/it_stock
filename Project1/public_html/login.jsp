<!DOCTYPE html>
    <head>
        <%@ page contentType="text/html;charset=windows-1256"%>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256"/>
        <link href="css/main.css" rel="stylesheet" type="text/css">
        <title>login</title>
        <link href='http://fonts.googleapis.com/css?family=Open Sans' rel='stylesheet'>
    </head>
    <body>
        <div id="background">
            <img src="images/login_background_logo.jpg" class="stretch" alt="" />
        </div>
        <div id='MainContainer'>
            <div id='login-form'>
                <img src="images/logo.png">
                <form class='' action='login' method='POST'>
                    <span>Username</span><br>
                    <input class="login-input-text" type='text' value='' name='username'><br>
                    <span>Password</span><br>
                    <input class="login-input-text" type='password' value='' name='password'><br>
                    <input class="login-button" type='submit' value='Login'><br>
                    <a href='#'>Forgot your password?</a><br>
                    <a href='new_user.jsp'>New User?</a>
                </form>
            </div>
        </div>
    </body>
    
</html>