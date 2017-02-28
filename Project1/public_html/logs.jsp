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
                    $("#gif-loading").html('<img src="images/ajax-loader.gif">');
                    $.post('log_search', {'username': username,'ip':ip,'description':description,'date_before':date_before,'date_after':date_after,'bottom_limit':bottom_limit,'top_limit':top_limit}, function(data) {
                      $("#SearchResult").html(data);
                      $("#gif-loading").html('');
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
        <link rel="icon" href="images/logo_image.png">
    </head>
    <body>
        <div id='MainContainer'>
            <% request.getRequestDispatcher("nav_bar.html").include(request,response); %>
            <div id='SearchContainer'>
                
                <h1>Logs</h1>
                <div id='SearchControls'>                
                    <div class="search-row"><label>Username:</label><input type='text' id='username' name='username'></div><br>
                    <div class="search-row"><label>IP:</label><input type='text' id='ip' name='ip'></div><br>
                    <div class="search-row"><label>Description:</label><input type='text' id='description' name='description'></div><br>
                    <div class="search-row"><label>Before Date:</label><input type='text' name='date_before' id='date_before'></div><br>
                    <div class="search-row"><label>After Date:</label><input type='text' name='date_after' id='date_after'></div><br>
                    <br><br>
                    <button type='button' id='PreviousButton' name='PreviousButton'>< Previous</button>
                    <button type='button' id='NextButton' name='NextButton'>Next ></button>
                    <button type='button' name='search' onclick='' id='search'>Search</button>
                    <div id="gif-loading"></div>
                
                    <!-- 
                        We will save record limits in hidden attributes
                        to keep track of what records to show for each page.
                        Starting pages will be ranged from 0 to 100
                    -->
                    <input type='hidden' value='0' name='BottomLimit' id='BottomLimit'>        
                    <input type='hidden' value='50' name='TopLimit' id='TopLimit'>
                </div>
                <div id='SearchResult'></div> 
            </div>
        </div>
    </body>
</HTML>