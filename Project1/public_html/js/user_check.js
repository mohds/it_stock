$(document).ready(function() {
	var x_timer;    
	$("#username").keyup(function (e){
		clearTimeout(x_timer);
		var user_name = $(this).val();
		x_timer = setTimeout(function(){
			check_username_ajax(user_name);
		}, 1000);
	}); 

	function check_username_ajax(username){
		$("#user-result").html('<img src="images/ajax-loader.gif" />');
		$.post('username_checker', {'username':username}, function(data) {
		  $("#user-result").html(data);
		});
	}
        
        $("#email").keyup(function (e){
		clearTimeout(x_timer);
		var email = $(this).val();
		x_timer = setTimeout(function(){
			check_email_ajax(email);
		}, 1000);
	}); 

	function check_email_ajax(email){
		$("#email-result").html('<img src="images/ajax-loader.gif" />');
		$.post('email_checker', {'email':email}, function(data) {
		  $("#email-result").html(data);
		});
	}
});