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
       
    // date boxes
    $("#BeforeDate").datepicker({
        changeYear: true,
        changeMonth: true
    });
    $("#AfterDate").datepicker({
        changeYear: true,
        changeMonth: true
    });
    
    // buttons
    $("#SearchButton").on("click", function(){
        search();
    });    
    
});

function search(){
    var ReceiptId = document.getElementById("ReceiptId").value;
    var Borrower = document.getElementById("Borrower").value;
    var Receiver = document.getElementById("Receiver").value;
    var BeforeDate = document.getElementById("BeforeDate").value;
    var AfterDate = document.getElementById("AfterDate").value;
    var ItemType = document.getElementById("ItemType").value;
    var ReceiptStatus = document.getElementById("ReceiptStatus").value;
    var ItemStatus = document.getElementById("ItemStatus").value;
        
    $.post('search_records', {ReceiptId: ReceiptId, Borrower: Borrower, Receiver: Receiver, BeforeDate: BeforeDate, AfterDate: AfterDate, ItemType: ItemType, ReceiptStatus: ReceiptStatus, ItemStatus: ItemStatus},
        function(returnedData){
            
        }
    );
    
}