<!DOCTYPE html>
<%@ page contentType="text/html;charset=windows-1256"%>
<%@ page import="project1.Item" %>
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
        <script src="js/autocomplete.js"></script>        
        
    </head>
    <body>
        <div id="MainContainer">
            <div class="ui-widget">
                <label>Add Item of type: </label><input id="TypeCombo" name="type"><button id="NewTypeButton">New</button><br>
                <h3>Enter Specs:</h3>
                <label>Brand: </label><input type="text" name="brand"><button>New</button><br>
                <label>Location: </label><input type="text" name="location"><button>New</button><br>                
                <div id="ExtraSpecs"></div>
                <div id="NewTypeDialog" title="Add a new type">
                    <label>Name: </label><input type="text" name="type_name" /><br>                   
                    <br>
                    <button type="submit">Add</button>                    
                </div>
            </div>
        </div>
    </body>
</html>