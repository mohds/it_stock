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
        <script src="js/jquery.printElement.min.js"></script>
        <script src="js/search_records.js"></script>
               
        <!-- JAVA -->
        <%@ page import="project1.Access" %>
        <link rel="icon" href="images/logo_image.png">
        
    </head>
    <body>
        <div id="MainContainer">
            <% request.getRequestDispatcher("nav_bar.html").include(request,response);%>
            <% 
                Access access = new Access();
                String user = (String)session.getAttribute("username");
                String method = "search_records";
                if(user == null){
                    out.println("Login first. <a href=\"login.jsp\">login ></a>");
                }
                else if(!access.has_access(user, method)){
                    out.println("You do not have permission to do that.");
                }
                else{
                // closed after form document
            %>
            <h2>Search records</h2>
            <span id="message-box"></span></br>
            <div class="container" id="LeftContainer">
                <label>Receipt ID: </label><input type="text" id="ReceiptId" /><br>
                <label>Item ID: </label><input type="text" id="ItemId"/></br>
                <label>Item Label: </label><input type="text" id="ItemLabel" /><br>
                <label>Borrower: </label><input type="text" id="Borrower" /><br>
                <label>IT admin: </label><input type="text" id="AdminCheckerId" /><br>
                <label>Borrowed Before: </label><input type="text" id="BorrowBeforeDate" /><br>
                <label>Borrowed After: </label><input type="text" id="BorrowAfterDate" /><br>
                <label>Returned Before: </label><input type="text" id="ReturnBeforeDate" /><br>
                <label>Returned After: </label><input type="text" id="ReturnAfterDate" /><br>
                <label>Item type: </label><input type="text" id="ItemType" /><br>
                <label>Receipt status:</label>
                <select id="ReceiptStatus">
                    <option value="" selected>All</option>
                    <option value="0">Open</option>
                    <option value="1">Closed</option>
                </select>
                <br>
                <label>Record Status:</label>
                <select id="RecordStatus" >
                    <option value="" selected>All</option>
                    <option value="0">Open</option>
                    <option value="1">Closed</option>
                </select>
                <br>
                <label>Item availability:</label>
                <select id="ItemStatus" >
                    <option value="" selected>All</option>
                    <option value="1">Available</option>
                    <option value="0">Not available</option>
                </select>                
                <br>
                <div id="control-buttons">
                    <button onclick="clear_all_input()">Clear</button>
                    <button type="button" id="SearchButton">Search</button></br>
                </div>
            </div>
            <div class="container" id="RightContainer">                
                <!--<img id="div_loading" src="images/ajax-loader-2.gif">-->
                <div class="scrollable-table">
                    <table id="ResultsTable">
                        <tr>
                            <th>Record ID</th>
                            <th>Item ID</th>
                            <th>Item Label</th>
                            <th>Item Type</th>
                            <th>Borrower</th>
                            <th>Borrow Date</th>
                            <th>Return Date</th>
                            <th class="hide_in_print">Details</th>
                        </tr>
                    </table>
                </div>
                </br>
                <button id="PreviousButton" class="hide_in_print">< Previous</button><button id="NextButton" class="hide_in_print">Next ></button>
                <div class="hide_in_print" id="export-buttons">
                    Print all Results<input type="checkbox" id="AllResults">
                    <button onClick="print_table()">Print Table</button>
                    <button id="ExportButton">Export</button>
                </div>
            </div>
            
            <div id="ViewDetailsDialog" title="Item Details">
                <label>Item ID:</label><span id="ItemId_dialog"></span><br>
                <label>Item Label:</label><span id="ItemLabel_dialog"></span><br>
                <label>Item Type:</label><span id="ItemType_dialog"></span><br>
                <label>Borrower:</label><span id="Borrower_dialog"></span><br>
                <label>IT Admin:</label><span id="AdminChecker_dialog"></span><br>
                <label>Borrowed On:</label><span id="BorrowDate_dialog"></span><br>
                <label>Returned On:</label><span id="ReturnDate_dialog"></span><br>
                <label>Receipt ID:</label><span id="ReceiptId_dialog"></span><br>
            </div>
            <div id="ItemDetailsDialog" title="Item Details">
                <label>ID:</label><span id="ItemId_ItemDialog"></span><br>
                <label>Label:</label><span id="ItemLabel_ItemDialog"></span><br>
                <label>Type:</label><span id="ItemType_ItemDialog"></span><br>
                <label>Location:</label><span id="Borrower_ItemDialog"></span><br>
                <label>Brand:</label><span id="Admin_ItemDialog"></span><br>
                <label>Serial No.:</label><span id="BorrowDate_ItemDialog"></span><br>
                <label>Condition:</label><span id="ReturnDate_ItemDialog"></span><br>
                <label>Availability:</label><span id="ReceiptId_ItemDialog"></span><br>
                <div id="ItemSpecs"></div>
            </div>
            <div id="ReturnDialog" title="Return Item">
                <span id="ReturnTitle"></span></br>
                <label>Returned By: </label><input type="text" id="ClientReturner_ReturnDialog"><button id="AddClient_ReturnDialog">New</button></br>
                <label>To Location: </label><input type="text" id="NewLocation_ReturnDialog"><button id="AddLocation_ReturnDialog">New</button></br>
                <label>Return Receipt: </label><input type="checkbox" id="ReturnReceipt"></br>
                <span id="ConfirmReturnButton"></span>
            </div>
            <div id="NewClientDialog" title="New Client">
                <label>Client name: </label><input type="text" id="NewClientName"></br>
                <button id="AddNewClientButton">Add</button>
            </div>
            <div id="NewLocationDialog" title="New Location">
                <label>Location name: </label><input type="text" id="NewLocationName"></br>
                <button id="AddNewLocationButton">Add</button>
            </div>
            <%}%>
        </div>
    </body>
</html>