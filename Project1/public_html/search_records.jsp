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
        <script src="js/search_records.js"></script>
                
    </head>
    <body>
        <div id="MainContainer">
            <h2>Search records</h2>
            <span id="message-box"></span>
            <div class="container" id="LeftContainer">
                <label>Receipt ID: </label><input type="text" id="ReceiptId" /><br>
                <label>Borrower: </label><input type="text" id="Borrower" /><br>
                <label>Receiver: </label><input type="text" id="Receiver" /><br>
                <label>Before Date: </label><input type="text" id="BeforeDate" /><br>
                <label>After Date: </label><input type="text" id="AfterDate" /><br>
                <label>Item type: </label><input type="text" id="ItemType" /><br>
                <label>Receipt status: </label>
                <select id="ReceiptStatus">
                    <option value="" selected>All</option>
                    <option value="open">Open</option>
                    <option value="closed">Closed</option>
                </select>
                <br>
                <label>Item status: </label>
                <select id="ItemStatus" >
                    <option value="" selected>All</option>
                    <option value="available">Available</option>
                    <option value="pending">Pending</option>
                </select>
                <br>
                <button type="button" id="SearchButton">Search</button>
            </div>
            <div class="container" id="RightContainer">
                <table id="ResultsTable">
                    <tr>
                        <th>Records ID</th>
                        <th>Borrower</th>
                        <th>Borrow Date</th>
                        <th>Return Date</th>
                        <th>Status</th>
                        <th>Details</th>
                    </tr>
                </table>
            </div>
        </div>
    </body>
</html>