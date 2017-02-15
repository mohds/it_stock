check_login_button();

function check_login_button(){
    $.post('check_login', {}, function(returnedData){
        document.getElementById("login").innerHTML = returnedData;
    });
}