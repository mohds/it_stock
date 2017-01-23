<!DOCTYPE html>
<%@ page contentType="text/html;charset=windows-1256"%>
<%@ page import="project1.Item" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256"/>
        <link type="text/css" rel="stylesheet" href="css/main.css">
        <link type="text/css" rel="stylesheet" href="css/jquery-ui.css">
        <link type="text/css" rel="stylesheet" href="css/jquery-style.css">
        <style>
            .custom-combobox {
            position: relative;
            display: inline-block;
            }
            .custom-combobox-toggle {
            position: absolute;
            top: 0;
            bottom: 0;
            margin-left: -1px;
            padding: 0;
            }
            .custom-combobox-input {
            margin: 0;
            padding: 5px 10px;
            }
        </style>  
        <script src="js/jquery.min.js"></script>
        <script src="js/jquery-1.12.4.js"></script>
        <script src="js/jquery-ui.js"></script>
        <script src="js/autocomplete.js"></script>        
    </head>
    <body>
        <div id="MainContainer">
            <div class="ui-widget">
                <label>Add Item of type:</label>
                <select id="combobox">
                    <option value="">Select one...</option>
                    <% 
                        Item item = new Item();
                        item.generate_combo_options("types", out);
                    %>
                </select>
                <br>
                <h3>Enter Specs:</h3>
                <label>Add Item of type:</label>
                <select id="combobox">
                    <option value="">Select one...</option>
                    <% 
                        item.generate_combo_options("types", out);
                    %>
                </select>
            </div>
                
        </div>
    </body>
</html>