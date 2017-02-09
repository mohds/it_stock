$(document).ready(function(){

    // buttons
    $("#SaveAccessGroups").on("click", function() {
        save_access_groups();
    });
    
});

function save_access_groups(){
    
    $("#message-box").html("Saving user access. Please wait.");
    
    var admins = [];
    var access_levels = [];
    
    var admins_temp = document.getElementsByClassName("admins");
    for(var i = 0 ; i < admins_temp.length ; i++){
        admins.push(admins_temp[i].innerText);
    }
    var access_levels_temp = document.getElementsByClassName("access_levels");
    for(var i = 0 ; i < access_levels_temp.length ; i++){
        access_levels.push(access_levels_temp[i].value);
    }
    
    $.post('save_access_groups', {admins: admins, access_levels: access_levels}, function(returnedData){
        $("#message-box").html(returnedData);
    });
}