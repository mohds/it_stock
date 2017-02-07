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
    $("#SearchButton").on("click", function(){
        search();
    });
    $("#AddNewClientButton").on("click", function(){
        add_client();
    });
    $("#AddClient_ReturnDialog").on("click", function(){        
        document.getElementById("NewClientName").value = "";
        $("#NewClientDialog").dialog("open");        
        $("#NewClientName").autocomplete({
            source: "get_clients",
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
        show: {
            effect: "slide",
            duration: 200
        },
        hide: {
            effect: "slide",
            duration: 200
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
});

function add_client(){
    var client = document.getElementById("NewClientName").value;
    
    $.post('add_client', {client: client}, 
        function(returnedData){
            
    }, 'json');
    
    $("#NewClientDialog").dialog("close");
    document.getElementById("NewClientName").value = "";
    
}

function return_item(record_id, row_id){
    
    var client_returner = document.getElementById("ClientReturner_ReturnDialog").value;
    
    $.get('return_item', {record_id: record_id, client_returner: client_returner}, function(returnedData){
       document.getElementById(row_id).cells[5].innerHTML = returnedData; 
    });
    $("#ReturnDialog").dialog("close");
    document.getElementById(row_id).setAttribute("class", "done");
    
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
    
}

function view_receipt(receipt_id){
    clear_all_input();
    document.getElementById("ReceiptId").value = receipt_id;
    search();
    $("#ViewDetailsDialog").dialog("close");        
    clear_details_dialog();
}
function view_type_history(item_type){
    clear_all_input();
    document.getElementById("ItemType").value = item_type;
    search();
    $("#ViewDetailsDialog").dialog("close");        
    clear_details_dialog(); 
}
function view_item_history(item_label){
    clear_all_input();
    document.getElementById("ItemLabel").value = item_label;
    search();
    $("#ViewDetailsDialog").dialog("close");        
    clear_details_dialog();    
}
function view_borrower_history(borrower){
    clear_all_input();
    document.getElementById("Borrower").value = borrower;
    search();
    $("#ViewDetailsDialog").dialog("close");        
    clear_details_dialog();    
}
function view_admin_history(admin){
    clear_all_input();
    document.getElementById("AdminCheckerId").value = admin;
    search();
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
    select = document.getElementById("ItemStatus");
    select.selectedIndex = select.options[0];
}

function view_more(record_id){
    
    $.getJSON('get_record_details', {record_id: record_id}, 
        function(returnedData){
            document.getElementById("ItemId_dialog").innerHTML =" <a onclick=\"view_item_history('" + returnedData[0].item_label + "')\">" + returnedData[0].item_id + "<\/a>";
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

function search(){
    var ReceiptId = document.getElementById("ReceiptId").value;
    var item_label = document.getElementById("ItemLabel").value;
    var Borrower = document.getElementById("Borrower").value;
    var AdminCheckerId = document.getElementById("AdminCheckerId").value;
    var BorrowBeforeDate = document.getElementById("BorrowBeforeDate").value;
    var BorrowAfterDate = document.getElementById("BorrowAfterDate").value;
    var ReturnBeforeDate = document.getElementById("ReturnBeforeDate").value;
    var ReturnAfterDate = document.getElementById("ReturnAfterDate").value;
    var ItemType = document.getElementById("ItemType").value;
    var ReceiptStatus = document.getElementById("ReceiptStatus").value;
    var ItemStatus = document.getElementById("ItemStatus").value;
        
    $.getJSON('search_records', {ReceiptId: ReceiptId, item_label: item_label, Borrower: Borrower, AdminCheckerId: AdminCheckerId, BorrowBeforeDate: BorrowBeforeDate, BorrowAfterDate: BorrowAfterDate, ReturnBeforeDate: ReturnBeforeDate, ReturnAfterDate: ReturnAfterDate, ItemType: ItemType, ReceiptStatus: ReceiptStatus, ItemStatus: ItemStatus},
        function(returnedData){                    
            // remove all rows except first
            $("#ResultsTable").find("tr:gt(0)").remove();            
            var html_code = "";
            for(var i = 0 ; i < returnedData.length ; i++){
                if(returnedData[i].return_date == "Pending"){
                    html_code += "<tr id=\"row_"+ i +"\" class=\"pending\"><td>"+ returnedData[i].record_id +"<\/td><td>"+ returnedData[i].item_label +"<\/td><td>"+ returnedData[i].item_type +"<\/td><td>"+ returnedData[i].borrower +"<\/td><td>"+ returnedData[i].borrow_date +"<\/td><td><button onClick=\"view_return('"+ returnedData[i].record_id +"', '"+ returnedData[i].item_label +"', 'row_"+ i +"')\">Return<\/button><\/td><td><button id=\"record_"+ returnedData[i].record_id +"\" onClick=\"view_more("+ returnedData[i].record_id +")\">View More<\/button><\/td><\/tr>";
                }
                else{
                    html_code += "<tr id=\"row_"+ i +"\" class=\"done\"><td>"+ returnedData[i].record_id +"<\/td><td>"+ returnedData[i].item_label +"<\/td><td>"+ returnedData[i].item_type +"<\/td><td>"+ returnedData[i].borrower +"<\/td><td>"+ returnedData[i].borrow_date +"<\/td><td>"+ returnedData[i].return_date +"<\/td><td><button id=\"record_"+ returnedData[i].record_id +"\" onClick=\"view_more("+ returnedData[i].record_id +")\">View More<\/button><\/td><\/tr>";
                }
            }
            $("#ResultsTable").append(html_code);
        }
    );
}