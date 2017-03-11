$(document).ready(function(){

    // popups
    $("#NewMethodDialog").dialog({
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
    $("#AddAccessGroupDialog").dialog({
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
    $("#ChangePasswordDialog").dialog({
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
    
    // buttons
    $("#SaveAccessGroups").on("click", function() {
        save_access_groups();
    });
    $("#SaveMethods").on("click", function() {
        save_methods();
    });
    $("#AddMethod").on( "click", function() {
      $( "#NewMethodDialog" ).dialog( "open" );
    });
    $("#AddNewMethod").on("click", function() {
        add_new_method();
    });
    $("#SaveEmails").on("click", function() {
        save_emails();
    });
    $("#AddAccessGroup").on("click", function() {
        $("#AddAccessGroupDialog").dialog("open");
    });
    $("#AddNewAccessGroup").on("click", function(){
        add_access_group();
    });
    $("#ChangePasswordButton").on("click", function(){
        save_password();
    });
    
    populate_methods_container();
    populate_user_emails_row();
    
});

function change_password(username){
    $("#ChangePasswordDialog").dialog("open");
    document.getElementById("temp_user_storage").value = username;
}
function save_password(){
    var username = document.getElementById("temp_user_storage").value;
    var new_password = document.getElementById("NewPassword").value;
    $.post('force_change_password', {username: username, new_password: new_password}, function(returnedData){
        document.getElementById("message-box").innerHTML = returnedData;
        $("#ChangePasswordDialog").dialog("close");
    });
}

function add_access_group(){
    document.getElementById("message-box").innerHTML = "Adding new access group.";
    var access_group = document.getElementById("NewAccessGroup").value;
    $.post('add_access_group', {access_group: access_group}, function(returnedData){
        document.getElementById("message-box").innerHTML = returnedData;
        $("#AddAccessGroupDialog").dialog("close");
        document.getElementById("NewAccessGroup").value = "";
    });
}

function delete_user(username, row_id){
    document.getElementById("message-box").innerHTML = "Deleting " + username + ". Please wait.";
    
    $.post('delete_user', {username: username}, function(returnedData){
        document.getElementById("message-box").innerHTML = returnedData;
        $("#"+ row_id +"").remove();
    });
    
}

function add_new_method(){
    var method = document.getElementById("NewMethod").value;
    $.post('add_new_method', {method: method}, function(returnedData){
        $("#methods-row").append("<label>"+ returnedData +"<\/label><input type=\"checkbox\" class=\"methods_checkbox\" id=\"" + method + "_id\" value=\""+ method +"\" ><\/br>");
    });
    
    document.getElementById("NewMethod").value = "";
    $("#NewMethodDialog").dialog("close");
}

function save_methods(){
    document.getElementById("message-box").innerHTML = "Saving methods please wait.";
    start_loading();
    var access_group = document.getElementById("access-groups").value;
    var methods = []; 
    var inputElements = document.getElementsByClassName('methods_checkbox');
    for(var i=0; inputElements[i]; i++){
          if(inputElements[i].checked){                
               methods.push(inputElements[i].value);
          }
    }
    
    $.post('save_methods', {access_group: access_group, methods: methods }, function(returnedData){
        document.getElementById("message-box").innerHTML = returnedData;
        stop_loading();
    });
    
}

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

function populate_methods_container(){
    populate_access_group_row();
    populate_methods_row();
}
function populate_access_group_row(){    
    $.getJSON('get_access_groups', {}, function(returnedData){
        for(var i = 0 ; i < returnedData.length ; i++){
            $("#access-groups").append($("<option><\/option>")
                .attr("value", returnedData[i])
                .text(returnedData[i])
            );
        }
    });
}
function populate_methods_row(){
    $.getJSON('get_methods', {}, function(returnedData){
        for(var i = 0 ; i < returnedData.length ; i++){
            $("#methods-row").append("<label>"+ returnedData[i] +" <\/label><input type=\"checkbox\" class=\"methods_checkbox\" id=\""+ returnedData[i] +"_id\" value=\""+ returnedData[i] +"\" ><\/br>");
        }
    });
}
function uncheck_all_boxes(){
    var checkboxes = document.getElementsByClassName("methods_checkbox");
    for(var i = 0 ; i < checkboxes.length ; i++){
        checkboxes[i].checked = false;
    }
}
function check_boxes(){
    uncheck_all_boxes();
    var selected_access_group = document.getElementById("access-groups").value;
    $.getJSON('get_methods_of_access_group', {selected_access_group: selected_access_group}, function(returnedData){
        for(var i = 0 ; i < returnedData.length ; i++){
            document.getElementById(returnedData[i] + "_id").checked = true;
        }
    });
}


function save_emails(){
    document.getElementById("message-box").innerHTML = "Saving email settings. Please wait.";
    var email_category = document.getElementById("email-categories").value;
    var admins = []; 
    var inputElements = document.getElementsByClassName('emails_checkbox');
    for(var i=0; inputElements[i]; i++){
          if(inputElements[i].checked){                
               admins.push(inputElements[i].value);
          }
    }
    
    $.post('save_emails', {email_category: email_category, admins: admins }, function(returnedData){
        document.getElementById("message-box").innerHTML = returnedData;
    });
    
}
function populate_user_emails_row(){
    $.getJSON('get_all_admins', {}, function(returnedData){
        for(var i = 0 ; i < returnedData.length ; i++){
            $("#user-emails-row").append("<label>"+ returnedData[i] +" <\/label><input type=\"checkbox\" class=\"emails_checkbox\" id=\""+ returnedData[i] +"_id\" value=\""+ returnedData[i] +"\" ><\/br>");
        }
    });
}

function uncheck_all_email_boxes(){
    var checkboxes = document.getElementsByClassName("emails_checkbox");
    for(var i = 0 ; i < checkboxes.length ; i++){
        checkboxes[i].checked = false;
    }
}
function check_email_boxes(){
    uncheck_all_email_boxes();
    var selected_email_category = document.getElementById("email-categories").value;
    $.getJSON('get_admins_of_category', {selected_email_category: selected_email_category}, function(returnedData){
        for(var i = 0 ; i < returnedData.length ; i++){
            document.getElementById(returnedData[i] + "_id").checked = true;
        }
    });
}
function start_loading(){
  document.getElementById("div_loading").style.display = "block";
}
function stop_loading(){
  document.getElementById("div_loading").style.display = "none";
}