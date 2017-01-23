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
});