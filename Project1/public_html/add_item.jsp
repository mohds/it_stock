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
        <script src="js/add_item.js"></script>        
        
    </head>
    <body>
        <div id="MainContainer">
        <% request.getRequestDispatcher("nav_bar.html").include(request,response);%>
            <div class="ui-widget">
                <h2>Add a new Item</h2>
                <span id="message-box"></span><br>
                <label>Add Item of type: </label><input id="TypeCombo" name="type"/><button id="NewTypeButton">New</button><br>
                <h3>Enter Specs:</h3>
                <label>Label: </label><input type="text" id="label" name="label"/><span id='label-result'></span><br>
                <label>Brand: </label><input type="text" id="brand" name="brand"/><button id="NewBrandButton">New</button><br>
                <label>Location: </label><input type="text" id="location" name="location"/><button id="NewLocationButton">New</button><br>
                <label>Serial Number: </label><input type="text" id="serial_number" name="serial_number"/><span id='serial-result'></span><br>
                <label>Condition: </label><div id="condition_options"></div><br>
                <div id="ExtraSpecs"></div>
                <button type="button" id="AddItemButton">Add Item</button>
                <div id="NewTypeDialog" title="Add a new type">
                    <label>Name: </label><input type="text" name="type_name" id="type_name"/><br>                   
                    <label>Specs: </label><button id="add_spec">Add</button><br>
                    <div id="specs_list" ></div>
                    <br>
                    <button id="AddTypeButton" type="button">Add</button>                    
                </div>
                <div id="NewBrandDialog" title="Add a new brand">
                    <label>Name: </label><input type="text" id="brand_to_add" name="brand_to_add" /><br>                   
                    <br>
                    <button id="AddBrandButton" type="submit">Add</button>                    
                </div>
                <div id="NewLocationDialog" title="Add a new location">
                    <label>Name: </label><input type="text" id="location_to_add" name="location_to_add" /><br>                   
                    <br>
                    <button id="AddLocationButton" type="submit">Add</button>                    
                </div>
            </div>
        </div>
    </body>
</html>