$(document).ready(function(){
    
    // autocomplete boxes
    $("#ReceiptId").autocomplete({
        source: "get_receipt_ids",
        minLength: 1
    });
    $("#Borrower").autocomplete({
        source: "get_clients",
        minLength: 1
    });    
    $("#AdminCheckerId").autocomplete({
        source: "get_admins",
        minLength: 1
    });    
    $("#ItemType").autocomplete({
        source: "get_types",
        minLength: 1
    });
    $("#ItemLabel").autocomplete({
        source: "get_item_labels",
        minLength: 1
    });
       
    // date boxes
    $("#BorrowBeforeDate").datepicker({
        changeYear: true,
        changeMonth: true,
        dateFormat: "dd/mm/yy"
    });
    $("#BorrowAfterDate").datepicker({
        changeYear: true,
        changeMonth: true,
        dateFormat: "dd/mm/yy"
    });
    $("#ReturnBeforeDate").datepicker({
        changeYear: true,
        changeMonth: true,
        dateFormat: "dd/mm/yy"
    });
    $("#ReturnAfterDate").datepicker({
        changeYear: true,
        changeMonth: true,
        dateFormat: "dd/mm/yy"
    });
    
    // buttons
    $("#ExportButton").on("click", function(){
        var html = document.querySelector("#ResultsTable").outerHTML;
	export_table_to_csv(html, "Result.csv");
    });
    $("#NextButton").on("click", function(){
        lower_bound += 15;
        upper_bound += 15;
        search(lower_bound, upper_bound);
    });
    $("#PreviousButton").on("click", function(){
        lower_bound -= 15;
        upper_bound -= 15;
        search(lower_bound, upper_bound);
    });
    $("#SearchButton").on("click", function(){
        lower_bound = 0;
        upper_bound = 15;
        search(lower_bound, upper_bound);
    });
    $("#AddNewClientButton").on("click", function(){
        add_client();
    });
    $("#AddNewLocationButton").on("click", function(){
        add_location();
    });
    $("#AddClient_ReturnDialog").on("click", function(){        
        document.getElementById("NewClientName").value = "";
        $("#NewClientDialog").dialog("open");        
        $("#NewClientName").autocomplete({
            source: "get_clients",
            minLength: 1
        });
    });
    $("#AddLocation_ReturnDialog").on("click", function(){        
        document.getElementById("NewLocationName").value = "";
        $("#NewLocationDialog").dialog("open");        
        $("#NewLocationName").autocomplete({
            source: "get_locations",
            minLength: 1
        });
    });
    
    // popups
    $("#ViewDetailsDialog").dialog({
        autoOpen: false,
        show: {
            effect: "slide",
            duration: 200
        },
        hide: {
            effect: "slide",
            duration: 200
        }
    });
    $("#ItemDetailsDialog").dialog({
        autoOpen: false,
        show: {
            effect: "slide",
            duration: 200
        },
        hide: {
            effect: "slide",
            duration: 200
        }
    });
    $("#ReturnDialog").dialog({
        autoOpen: false,
        width: 400,
        show: {
            effect: "slide",
            duration: 200
        },
        hide: {
            effect: "slide",
            duration: 300
        }
    });
    $("#NewClientDialog").dialog({
        autoOpen: false,
        show: {
            effect: "slide",
            duration: 200
        },
        hide: {
            effect: "slide",
            duration: 200
        }
    });
    $("#NewLocationDialog").dialog({
        autoOpen: false,
        show: {
            effect: "slide",
            duration: 200
        },
        hide: {
            effect: "slide",
            duration: 200
        }
    });
    
    check_item_id_search();
    
});

function download_csv(csv, filename) {
    var csvFile;
    var downloadLink;

    // CSV FILE
    csvFile = new Blob([csv], {type: "text/csv"});

    // Download link
    downloadLink = document.createElement("a");

    // File name
    downloadLink.download = filename;

    // We have to create a link to the file
    downloadLink.href = window.URL.createObjectURL(csvFile);

    // Make sure that the link is not displayed
    downloadLink.style.display = "none";

    // Add the link to your DOM
    document.body.appendChild(downloadLink);

    // Lanzamos
    downloadLink.click();
}

function export_table_to_csv(html, filename) {
	var csv = [];
	var rows = document.querySelectorAll("#ResultsTable tr");
	
    for (var i = 0; i < rows.length; i++) {
		var row = [], cols = rows[i].querySelectorAll("td, th");
		
        for (var j = 0; j < cols.length; j++)
            row.push(cols[j].innerText);
        
        csv.push(row.join(","));		
    }
    var csv_fixed = csv.join("\n");

    // Download CSV
    download_csv(csv_fixed, filename);
}

function getQueryParams(qs) {
    qs = qs.split('+').join(' ');
    var params = {},
        tokens,
        re = /[?&]?([^=]+)=([^&]*)/g;

    while (tokens = re.exec(qs)) {
        params[decodeURIComponent(tokens[1])] = decodeURIComponent(tokens[2]);
    }
    return params;
}

function check_item_id_search(){
    var query = getQueryParams(document.location.search);
    if(query.item_id > 0){
    
        $.post('get_receipt_id_of_item_id', {item_id: query.item_id}, function(returnedData){
            document.getElementById("ReceiptId").value = returnedData;
            search(lower_bound, upper_bound);
        });
        
        
    }
}

// define global variables
// variables to be used in upper and lower bounds of number of records
var upper_bound = 15;
var lower_bound = 0;

function print_table(){
    if(document.getElementById("AllResults").checked){
        search(0, 10000);
        
        setTimeout(function (){
            PrintElem("RightContainer");
        }, 1000);
        
        search(lower_bound, upper_bound);
    }
    else{
        PrintElem("RightContainer");
    }
}
function PrintElem(elem) {

    var mywindow = window.open('', 'PRINT', 'height=400,width=600');
        
    mywindow.document.head.innerHTML = "<title><\/title>" +
    "<style> " +
    ".hide_in_print{" +
    "display:none;" +
    "} " +
    ".pending{" +
    "background-color:#FFC0CB;" +
    "} " +
    ".done{" +
    "background-color:#33CC56;" +
    "} " +
    "table td {" +
    "color: black;" +
    "text-align: center;" +
    "text-decoration: none;" +
    "border: 1px solid #999" +
    "padding: 2px 2px;" +
    "} " +
    "table th {" +
    "color: black;" +
    "text-align: center;" +
    "text-decoration: none;" +
    "border: 1px solid #999" +
    "padding: 2px 2px;" +
    "} " +
    "table{" +
    "border:solid 1px;" +
    "border-collapse: collapse;" +
    "}" +
    "<\/style>";
    mywindow.document.body.innerHTML = document.getElementById(elem).innerHTML;

    mywindow.document.close(); // necessary for IE >= 10
    mywindow.focus(); // necessary for IE >= 10*/

    mywindow.print();
    mywindow.close();

    return true;

}

function add_location(){
    var location = document.getElementById("NewLocationName").value;
    
    $.post('add_location', {location: location}, 
        function(returnedData){
            
    }, 'json');
    
    $("#NewLocationDialog").dialog("close");
    document.getElementById("NewLocationName").value = "";
}
function add_client(){
    var client = document.getElementById("NewClientName").value;
    
    $.post('add_client', {client: client}, 
        function(returnedData){
            
    }, 'json');
    
    $("#NewClientDialog").dialog("close");
    document.getElementById("NewClientName").value = "";
    
}
function get_date(){
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth()+1; //January is 0!
    var yyyy = today.getFullYear();
    
    if(dd<10) {
        dd='0'+dd
    } 
    
    if(mm<10) {
        mm='0'+mm
    } 
    
    today = dd+'/'+mm+'/'+yyyy;
    return today;
}
function return_item(record_id, row_id){
    
    start_loading();
    
    var client_returner = document.getElementById("ClientReturner_ReturnDialog").value;
    var new_location = document.getElementById("NewLocation_ReturnDialog").value;
    
    if(document.getElementById("ReturnReceipt").checked){
        document.getElementById("message-box").innerHTML = "Returning Receipt. Please wait.";
        $("#ReturnDialog").dialog("close");
        $.get('return_receipt_from_record_id', {record_id: record_id, client_returner: client_returner, new_location: new_location}, function(returnedData){
            if(returnedData.includes("ERROR")){
                document.getElementById("message-box").innerHTML = "Failed to return receipt.";
            }
            else{
                $.getJSON('get_records_ids_of_receipt', {record_id: record_id}, function(returnedData){
                    for(var i = 0 ; i < returnedData.length ; i++){
                        document.getElementById("row_" + returnedData[i]).cells[6].innerHTML = get_date();            
                        document.getElementById("row_" + returnedData[i]).setAttribute("class", "done");
                        document.getElementById("message-box").innerHTML = "Receipt has been successfully returned."; 
                    }
                    stop_loading();
                });
            }
        });
    }
    else {
        document.getElementById("message-box").innerHTML = "Returning Record " + record_id + ". Please wait.";
        $("#ReturnDialog").dialog("close");
        $.get('return_item', {record_id: record_id, client_returner: client_returner, new_location: new_location}, function(returnedData){
            if(returnedData.includes("ERROR")){
                // do nothing
            }
            else{
                document.getElementById("message-box").innerHTML = "Record " + record_id + " has been successfully returned.";
                document.getElementById(row_id).cells[6].innerHTML = returnedData;            
                document.getElementById(row_id).setAttribute("class", "done");
            }
            stop_loading();
        });
    }
}
function view_return(record_id, item_label, row_id){
    
    // set item label in dialog
    document.getElementById("ReturnTitle").innerHTML = "Return Item: " + item_label;
    
    // suggest borrower as returner for user
    $.getJSON('get_client_borrower', {record_id: record_id}, function(returnedData){
        document.getElementById("ClientReturner_ReturnDialog").value = returnedData;
    });
    
    // add button
    document.getElementById("ConfirmReturnButton").innerHTML = "<button onClick=\"return_item('"+ record_id +"', '"+ row_id +"')\">Confirm<\/button>";
    
    // open dialog
    $("#ReturnDialog").dialog("open");
    
    $("#ClientReturner_ReturnDialog").autocomplete({
        source: "get_clients",
        minLength: 1
    });
    $("#NewLocation_ReturnDialog").autocomplete({
        source: "get_locations",
        minLength: 1
    });
    
}

function view_item_by_id(item_id){
    clear_all_input();
    document.getElementById("ItemId").value = item_id;
    search(lower_bound, upper_bound);
    $("#ViewDetailsDialog").dialog("close");        
    clear_details_dialog();
}
function view_receipt(receipt_id){
    clear_all_input();
    document.getElementById("ReceiptId").value = receipt_id;
    search(lower_bound, upper_bound);
    $("#ViewDetailsDialog").dialog("close");        
    clear_details_dialog();
}
function view_type_history(item_type){
    clear_all_input();
    document.getElementById("ItemType").value = item_type;
    search(lower_bound, upper_bound);
    $("#ViewDetailsDialog").dialog("close");        
    clear_details_dialog(); 
}
function view_item_history(item_label){
    clear_all_input();
    document.getElementById("ItemLabel").value = item_label;
    search(lower_bound, upper_bound);
    $("#ViewDetailsDialog").dialog("close");        
    clear_details_dialog();    
}
function view_borrower_history(borrower){
    clear_all_input();
    document.getElementById("Borrower").value = borrower;
    search(lower_bound, upper_bound);
    $("#ViewDetailsDialog").dialog("close");        
    clear_details_dialog();    
}
function view_admin_history(admin){
    clear_all_input();
    document.getElementById("AdminCheckerId").value = admin;
    search(lower_bound, upper_bound);
    $("#ViewDetailsDialog").dialog("close");        
    clear_details_dialog();    
}

function clear_details_dialog(){
    document.getElementById("ItemId_dialog").innerHTML = "";
    document.getElementById("ItemLabel_dialog").innerHTML = "";
    document.getElementById("ItemType_dialog").innerHTML = "";
    document.getElementById("Borrower_dialog").innerHTML = "";
    document.getElementById("AdminChecker_dialog").innerHTML = "";
    document.getElementById("BorrowDate_dialog").innerHTML = "";
    document.getElementById("ReturnDate_dialog").innerHTML = "";
    document.getElementById("ReceiptId_dialog").innerHTML = "";
}

function clear_all_input(){
    document.getElementById("ReceiptId").value = "";
    document.getElementById("ItemId").value = "";
    document.getElementById("ItemLabel").value = "";
    document.getElementById("Borrower").value = "";
    document.getElementById("AdminCheckerId").value = "";
    document.getElementById("BorrowBeforeDate").value = "";
    document.getElementById("BorrowAfterDate").value = "";
    document.getElementById("ReturnBeforeDate").value = "";
    document.getElementById("ReturnAfterDate").value = "";
    document.getElementById("ItemType").value = "";
    
    var select = document.getElementById("ReceiptStatus");
    select.selectedIndex = select.options[0];
    select = document.getElementById("RecordStatus");
    select.selectedIndex = select.options[0];
    select = document.getElementById("ItemStatus");
    select.selectedIndex = select.options[0];
}

function view_more(record_id){
    
    $.getJSON('get_record_details', {record_id: record_id}, 
        function(returnedData){
            document.getElementById("ItemId_dialog").innerHTML =" <a onclick=\"view_item_by_id('" + returnedData[0].item_id + "')\">" + returnedData[0].item_id + "<\/a>";
            document.getElementById("ItemLabel_dialog").innerHTML =" " + "<a onclick=\"view_item_history('"+ returnedData[0].item_label +"')\">"+ returnedData[0].item_label +"<\/a>";
            document.getElementById("ItemType_dialog").innerHTML =" " + "<a onclick=\"view_type_history('"+ returnedData[0].item_type +"')\">"+ returnedData[0].item_type +"<\/a>";
            document.getElementById("Borrower_dialog").innerHTML =" " + "<a onclick=\"view_borrower_history('"+ returnedData[0].borrower +"')\">"+ returnedData[0].borrower +"<\/a>";
            document.getElementById("AdminChecker_dialog").innerHTML =" " + "<a onclick=\"view_admin_history('"+ returnedData[0].admin_checker +"')\">"+ returnedData[0].admin_checker +"<\/a>";
            document.getElementById("BorrowDate_dialog").innerHTML =" " + returnedData[0].borrow_date;
            document.getElementById("ReturnDate_dialog").innerHTML =" " + returnedData[0].return_date;
            document.getElementById("ReceiptId_dialog").innerHTML =" " + "<a onclick=\"view_receipt('"+ returnedData[0].receipt_id +"')\">"+ returnedData[0].receipt_id +"<\/a>";
            
    });    
    $("#ViewDetailsDialog").dialog("open");
}

function search(lower_bound, upper_bound){
    
    start_loading();
    
    var ReceiptId = document.getElementById("ReceiptId").value;
    var item_id = document.getElementById("ItemId").value;
    var item_label = document.getElementById("ItemLabel").value;
    var Borrower = document.getElementById("Borrower").value;
    var AdminCheckerId = document.getElementById("AdminCheckerId").value;
    var BorrowBeforeDate = document.getElementById("BorrowBeforeDate").value;
    var BorrowAfterDate = document.getElementById("BorrowAfterDate").value;
    var ReturnBeforeDate = document.getElementById("ReturnBeforeDate").value;
    var ReturnAfterDate = document.getElementById("ReturnAfterDate").value;
    var ItemType = document.getElementById("ItemType").value;
    var ReceiptStatus = document.getElementById("ReceiptStatus").value;
    var RecordStatus = document.getElementById("RecordStatus").value;
    var ItemStatus = document.getElementById("ItemStatus").value;
        
    $.getJSON('search_records', {ReceiptId: ReceiptId, item_id: item_id, item_label: item_label, Borrower: Borrower, AdminCheckerId: AdminCheckerId, BorrowBeforeDate: BorrowBeforeDate, BorrowAfterDate: BorrowAfterDate, ReturnBeforeDate: ReturnBeforeDate, ReturnAfterDate: ReturnAfterDate, ItemType: ItemType, ReceiptStatus: ReceiptStatus, RecordStatus: RecordStatus, ItemStatus: ItemStatus, lower_bound: lower_bound, upper_bound: upper_bound},
        function(returnedData){                    
            // remove all rows except first
            $("#ResultsTable").find("tr:gt(0)").remove();            
            var html_code = "";
            for(var i = 0 ; i < returnedData.length ; i++){
                if(returnedData[i].return_date == "Pending"){
                    html_code += "<tr id=\"row_"+ returnedData[i].record_id +"\" class=\"pending\"><td>"+ returnedData[i].record_id +"<\/td><td>"+ returnedData[i].item_id +"<\/td><td>"+ returnedData[i].item_label +"<\/td><td>"+ returnedData[i].item_type +"<\/td><td>"+ returnedData[i].borrower +"<\/td><td>"+ returnedData[i].borrow_date +"<\/td><td><button onClick=\"view_return('"+ returnedData[i].record_id +"', '"+ returnedData[i].item_label +"', 'row_"+ returnedData[i].record_id +"')\">Return<\/button><\/td><td class=\"hide_in_print\"><button id=\"record_"+ returnedData[i].record_id +"\" onClick=\"view_more("+ returnedData[i].record_id +")\">View More<\/button><\/td><\/tr>";
                }
                else{
                    html_code += "<tr id=\"row_"+ returnedData[i].record_id +"\" class=\"done\"><td>"+ returnedData[i].record_id +"<\/td><td>"+ returnedData[i].item_id +"<\/td><td>"+ returnedData[i].item_label +"<\/td><td>"+ returnedData[i].item_type +"<\/td><td>"+ returnedData[i].borrower +"<\/td><td>"+ returnedData[i].borrow_date +"<\/td><td>"+ returnedData[i].return_date +"<\/td><td class=\"hide_in_print\"><button id=\"record_"+ returnedData[i].record_id +"\" onClick=\"view_more("+ returnedData[i].record_id +")\">View More<\/button><\/td><\/tr>";
                }
            }
            $("#ResultsTable").append(html_code);
            stop_loading();
        }
    );
    
}
function start_loading(){
  document.getElementById("div_loading").style.display = "block";
}
function stop_loading(){
  document.getElementById("div_loading").style.display = "none";
}
