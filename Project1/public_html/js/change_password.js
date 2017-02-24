$(document).ready(function(){

    $("#save_password_button").on("click", function(){
        save_password();
    });

});

function save_password(){
    
    var old_password = document.getElementById("old_password").value;
    var first_password = document.getElementById("first_password").value;
    var second_password = document.getElementById("second_password").value;
    
    $.post('change_password', {old_password: old_password, first_password: first_password, second_password: second_password}, function(returnedData){
        document.getElementById("message-box").innerHTML = returnedData;
    });
    
}