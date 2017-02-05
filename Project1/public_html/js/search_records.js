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
    $("#Receiver").autocomplete({
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
    
    // popups
    $("#ViewDetailsDialog").dialog({
        autoOpen: false,
        show: {
            effect: "slide",
            duration: 400
        },
        hide: {
            effect: "slide",
            duration: 400
        }
    });
    // popups
    $("#ItemDetailsDialog").dialog({
        autoOpen: false,
        show: {
            effect: "slide",
            duration: 400
        },
        hide: {
            effect: "slide",
            duration: 400
        }
    });
});

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
    document.getElementById("Receiver").value = admin;
    search();
    $("#ViewDetailsDialog").dialog("close");        
    clear_details_dialog();    
}

function clear_details_dialog(){
    document.getElementById("ItemId_dialog").innerHTML = "";
    document.getElementById("ItemLabel_dialog").innerHTML = "";
    document.getElementById("ItemType_dialog").innerHTML = "";
    document.getElementById("Borrower_dialog").innerHTML = "";
    document.getElementById("Admin_dialog").innerHTML = "";
    document.getElementById("BorrowDate_dialog").innerHTML = "";
    document.getElementById("ReturnDate_dialog").innerHTML = "";
    document.getElementById("ReceiptId_dialog").innerHTML = "";
}

function clear_all_input(){
    document.getElementById("ReceiptId").value = "";
    document.getElementById("ItemLabel").value = "";
    document.getElementById("Borrower").value = "";
    document.getElementById("Receiver").value = "";
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
            document.getElementById("ItemType_dialog").innerHTML =" " + "<a onclick=\"view_item_details("+ returnedData[0].item_id +")\">"+ returnedData[0].item_type +"<\/a>";
            document.getElementById("Borrower_dialog").innerHTML =" " + "<a onclick=\"view_borrower_history('"+ returnedData[0].borrower +"')\">"+ returnedData[0].borrower +"<\/a>";
            document.getElementById("Admin_dialog").innerHTML =" " + "<a onclick=\"view_admin_history('"+ returnedData[0].admin +"')\">"+ returnedData[0].admin +"<\/a>";
            document.getElementById("BorrowDate_dialog").innerHTML =" " + returnedData[0].borrow_date;
            document.getElementById("ReturnDate_dialog").innerHTML =" " + returnedData[0].return_date;
            document.getElementById("ReceiptId_dialog").innerHTML =" " + "<a onclick=\"view_receipt("+ returnedData[0].receipt_id +")\">"+ returnedData[0].receipt_id +"<\/a>";
            
    });    
    $("#ViewDetailsDialog").dialog("open");
}

function search(){
    var ReceiptId = document.getElementById("ReceiptId").value;
    var item_label = document.getElementById("ItemLabel").value;
    var Borrower = document.getElementById("Borrower").value;
    var Receiver = document.getElementById("Receiver").value;
    var BorrowBeforeDate = document.getElementById("BorrowBeforeDate").value;
    var BorrowAfterDate = document.getElementById("BorrowAfterDate").value;
    var ReturnBeforeDate = document.getElementById("ReturnBeforeDate").value;
    var ReturnAfterDate = document.getElementById("ReturnAfterDate").value;
    var ItemType = document.getElementById("ItemType").value;
    var ReceiptStatus = document.getElementById("ReceiptStatus").value;
    var ItemStatus = document.getElementById("ItemStatus").value;
        
    $.getJSON('search_records', {ReceiptId: ReceiptId, item_label: item_label, Borrower: Borrower, Receiver: Receiver, BorrowBeforeDate: BorrowBeforeDate, BorrowAfterDate: BorrowAfterDate, ReturnBeforeDate: ReturnBeforeDate, ReturnAfterDate: ReturnAfterDate, ItemType: ItemType, ReceiptStatus: ReceiptStatus, ItemStatus: ItemStatus},
        function(returnedData){                    
            // remove all rows except first
            $("#ResultsTable").find("tr:gt(0)").remove();            
            var html_code = "";
            for(var i = 0 ; i < returnedData.length ; i++){
                html_code += "<tr><td>"+ returnedData[i].record_id +"<\/td><td>"+ returnedData[i].item_label +"<\/td><td>"+ returnedData[i].item_type +"<\/td><td>"+ returnedData[i].borrower +"<\/td><td>"+ returnedData[i].borrow_date +"<\/td><td>"+ returnedData[i].return_date +"<\/td><td>"+ returnedData[i].status +"<\/td><td><button id=\"record_"+ returnedData[i].record_id +"\" onClick=\"view_more("+ returnedData[i].record_id +")\">View More<\/button><\/td><\/tr>";
            }
            $("#ResultsTable").append(html_code);
        }
    );    
}