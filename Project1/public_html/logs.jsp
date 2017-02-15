<!DOCTYPE HTML>
<head>
    <title>Logs</title>
    
    <link rel='stylesheet' type='text/css' href='css/Logs.css'>
    <link rel='stylesheet' type='text/css' href='css/main.css'>
    <link rel="stylesheet" href="css/jquery-ui.css">
    <link rel="stylesheet" href="css/style.css">
    
    <script src="js/jquery-1.12.4.js"></script>
    <script src="js/jquery-ui.js"></script>
    <script src="js/jquery.min.js"></script>  

    <script>
        $( function() {
            $( "#date_before" ).datepicker({
                changeYear: true,
                changeMonth: true
            });
        } );
    </script>
    <script>
        $( function() {
            $( "#date_after" ).datepicker({
                changeYear: true,
                changeMonth: true
            });
        } );
    </script>   
    <script type="text/javascript">
        $(document).ready(function() {
            var x_timer;    
            $("#search").click(function (e){
                clearTimeout(x_timer);
                var user_name = $('#username').val();
                var ip = $('#ip').val();
                var description = $('#description').val();
                var date_before = $('#date_before').val();
                var date_after = $('#date_after').val();
                var bottom_limit = $('#BottomLimit').val();
                var top_limit = $('#TopLimit').val();
                x_timer = setTimeout(function(){
                    search_log(user_name, ip, description, date_before, date_after, bottom_limit, top_limit);
                }, 1000);
            }); 
            function search_log(username, ip, description, date_before, date_after, bottom_limit, top_limit){
                $("#SearchResult").html('<img src="images/ajax-loader.gif">');
                $.post('log_search', {'username': username,'ip':ip,'description':description,'date_before':date_before,'date_after':date_after,'bottom_limit':bottom_limit,'top_limit':top_limit}, function(data) {
                  $("#SearchResult").html(data);
                });
            }
            
            $("#NextButton").click(function (e){
                clearTimeout(x_timer);
                var user_name = $('#username').val();
                var ip = $('#ip').val();
                var description = $('#description').val();
                var date_before = $('#date_before').val();
                var date_after = $('#date_after').val();
                var bottom_limit = $('#BottomLimit').val();
                var top_limit = $('#TopLimit').val();
                
                bottom_limit = parseInt(bottom_limit) + 50;
                top_limit = parseInt(top_limit) + 50;
                $('#BottomLimit').val(bottom_limit)
                $('#TopLimit').val(top_limit);
                
                x_time = setTimeout(function(){
                    search_log(user_name, ip, description, date_before, date_after, bottom_limit, top_limit);
                }, 1000);
            });
            $("#PreviousButton").click(function (e){
                clearTimeout(x_timer);
                var user_name = $('#username').val();
                var ip = $('#ip').val();
                var description = $('#description').val();
                var date_before = $('#date_before').val();
                var date_after = $('#date_after').val();
                var bottom_limit = $('#BottomLimit').val();
                var top_limit = $('#TopLimit').val();
                
                bottom_limit = parseInt(bottom_limit) - 50;
                top_limit = parseInt(top_limit) - 50;
                $('#BottomLimit').val(bottom_limit)
                $('#TopLimit').val(top_limit);
                
                x_time = setTimeout(function(){
                    search_log(user_name, ip, description, date_before, date_after, bottom_limit, top_limit);
                }, 1000);
            });
        });
    </script>
    
</head>
<body>
    <div id='MainContainer'>
        <div id='SeachContainer'>
            <% request.getRequestDispatcher("nav_bar.html").include(request,response); %>
            <h1>Logs</h1>
            <div id='SearchControls'>                
                <label>Username:</label><input type='text' id='username' name='username'>
                <label>IP:</label><input type='text' id='ip' name='ip'>
                <label>Description:</label><input type='text' id='description' name='description'>
                <label>Before Date:</label><input type='text' name='date_before' id='date_before'>
                <label>After Date:</label><input type='text' name='date_after' id='date_after'>
                
                <input type='button' name='search' onclick='' value='Search' id='search'>
                <input type='button' value='< Previous' id='PreviousButton' name='PreviousButton'></input>
                <input type='button' value='Next >' id='NextButton' name='NextButton'></input>
            <br><br>
            <!-- 
                We will save record limits in hidden attributes
                to keep track of what records to show for each page.
                Starting pages will be ranged from 0 to 100
            -->
            <input type='hidden' value='0' name='BottomLimit' id='BottomLimit'>        
            <input type='hidden' value='50' name='TopLimit' id='TopLimit'>
            <div id='SearchResult'></div>            
        </div>
    </div>
</body>
</HTML>