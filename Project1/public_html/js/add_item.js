$(document).ready(function(){
    // autocomplete boxes
    $("#TypeCombo").autocomplete({
        source: "get_types",
        minLength: 1,
        select: function(event, ui){
            var type_selected = ui.item.value;
            generate_extra_specs(type_selected);            
            
        }
    });
    $("#brand").autocomplete({
        source: "get_brands",
        minLength: 1
    });
    $("#location").autocomplete({
        source: "get_locations",
        minLength: 1
    });
        
    // popups
    $("#NewTypeDialog").dialog({
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
    $("#NewTypeButton").on("click", function(){
        $("#NewTypeDialog").dialog("open");
    });
    
    $("#NewBrandDialog").dialog({
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
    $("#NewBrandButton").on("click", function(){
        $("#NewBrandDialog").dialog("open");
    });
    
    $("#NewLocationDialog").dialog({
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
    $("#NewLocationButton").on("click", function(){
        $("#NewLocationDialog").dialog("open");
    });
    
    
    // add buttons
    $("#AddItemButton").on("click", function() {
        add_item();
    });
    
    $("#AddBrandButton").on("click", function(){
        add_brand();
    });
    
    $("#AddLocationButton").on("click", function(){
        add_location();
    });
    
    $("#add_spec").on("click", function(){
        add_spec();
    });
    
    $("#AddTypeButton").on("click", function(){
        add_type();
    });
    
    
    // input checker
    var x_timer;    
    $("#label").keyup(function (e){
            clearTimeout(x_timer);
            var label = $(this).val();
            x_timer = setTimeout(function(){
                    check_label_ajax(label);
            }, 1000);
    }); 

    function check_label_ajax(label){
            $("#label-result").html('<img src="images/ajax-loader.gif" />');
            $.post('label_checker', {'label':label}, function(data) {
              $("#label-result").html(data);
            });
    }
    $("#serial_number").keyup(function (e){
            clearTimeout(x_timer);
            var serial = $(this).val();
            x_timer = setTimeout(function(){
                    check_serial_ajax(serial);
            }, 1000);
    }); 

    function check_serial_ajax(serial){
            $("#serial-result").html('<img src="images/ajax-loader.gif" />');
            $.post('serial_checker', {'serial':serial}, function(data) {
              $("#serial-result").html(data);
            });
    }
    
});

// populate conditions select box
get_conditions();

var spec_id = 0; // will be used as a unique identifier for each added spec space

function add_spec(){
    var html_code = "<div id=\"spec_"+ spec_id +"\"><input type=\"text\" id=\"spec_input_"+ spec_id +"\" name=\"spec_new\" class=\"spec_new\"><button onclick=\"remove_spec("+ spec_id +")\">remove<\/button><br><\/div>";
    
    var selector = "#spec_input_"+ spec_id +"";
    $(document).on('keydown.autocomplete', selector, function() {
        $(this).autocomplete({
            source: "get_all_specs_filtered",
            minLength: 1
        });
    });
    
    //$("#spec_input_"+ spec_id +"").autocomplete({
    //  source: "get_all_specs",
    //  minLength: 1
    //});
    
    $("#specs_list").append(html_code);
    spec_id++;
}

function remove_spec(spec_id_){
    $("#spec_"+ spec_id_ +"").remove();
}

function get_conditions(){
    $("#condition_options").html('');
    $.get('get_conditions', {}, 
        function(returnedData){
                var html_code = "";
                 html_code += "<select id=\"condition\" name=\"condition\">";
                for( i = 0 ; i < returnedData.length ; i++ ){
                     html_code += "<option value=\""+ returnedData[i] +"\">"+ returnedData[i] +"<\/option>";
                }
                html_code += "<\/select>";
                $("#condition_options").append(html_code);
    }, 'json');
}

function add_type(){
    // first we get the main three attributes
    var type = document.getElementById("type_name").value;
    
    // second we get the extra specs attributes
    var specs_names = [];
    var specs_temp = document.getElementsByClassName("spec_new");
    for(var i = 0 ; i < specs_temp.length ; i++){
        specs_names.push(specs_temp[i].value);
    }
    
    $.post('add_type', {type: type,specs_names: specs_names}, 
        function(returnedData){
            
    }, 'json');
    
    $("#NewTypeDialog").dialog("close");
    $("#specs_list").html('');
    document.getElementById("type_name").value = "";
    
}

function add_brand(){

    var brand = document.getElementById("brand_to_add").value;
    
    $.post('add_brand', {brand: brand}, 
        function(returnedData){
            
    }, 'json');
    
    $("#NewBrandDialog").dialog("close");
    document.getElementById("brand_to_add").value = "";
}
function add_location(){

    var location = document.getElementById("location_to_add").value;
    
    $.post('add_location', {location: location}, 
        function(returnedData){
            
    }, 'json');
    
    $("#NewLocationDialog").dialog("close");
    document.getElementById("location_to_add").value = "";
}

function add_item(){
    
    $("#message-box").html("Adding new Item please wait.");
    
    // first we get the main attributes
    var type = document.getElementById("TypeCombo").value;
    var brand = document.getElementById("brand").value;
    var location = document.getElementById("location").value;
    var label = document.getElementById("label").value;
    var serial_number = document.getElementById("serial_number").value;
    var condition = document.getElementById("condition").value;
    
    // second we get the extra specs attributes
    var specs_names = [];
    var specs_values = [];
    var specs_temp = document.getElementsByClassName("spec");
    for(var i = 0 ; i < specs_temp.length ; i++){
        specs_names.push(specs_temp[i].getAttribute("name"));
        specs_values.push(specs_temp[i].value);
    }
    
    $.post('add_item', {type: type, brand: brand, location: location, label: label, serial_number: serial_number, condition: condition, specs_names: specs_names, specs_values: specs_values}, 
        function(returnedData){
            $("#message-box").html(returnedData);
    });    
}

function generate_extra_specs(type_selected){
    $("#ExtraSpecs").html('');
    $.post('get_specs', {type_selected: type_selected}, 
        function(returnedData){
                for( i = 0 ; i < returnedData.length ; i++ ){
                    $("#ExtraSpecs").append('<label>' + returnedData[i] + ': <\/label><input type=\"text\" class=\"spec\" name=\"' + returnedData[i] + '\">');
                }
    }, 'json');
}