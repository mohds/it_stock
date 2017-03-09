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
        width: "350px",
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
    $("#EditTypeDialog").dialog({
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
    $("#clear-button").on("click", function(){
        clear_input();
    });
    $("#NewBrandButton").on("click", function(){
        $("#NewBrandDialog").dialog("open");
    });
    $("#NewLocationButton").on("click", function(){
        $("#NewLocationDialog").dialog("open");
    });
    $("#NewTypeButton").on("click", function(){
        $("#NewTypeDialog").dialog("open");
    });
    $("#EditTypeButton").on("click", function(){
        $("#EditTypeDialog").dialog("open");
        $("#type_to_edit").autocomplete({
            source: "get_types",
            minLength: 1,
            select: function(event, ui){
                var type_selected = ui.item.value;
                document.getElementById("name_to_save").value = type_selected;
                generate_extra_specs_for_edit(type_selected);
            }
        });
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

function clear_input(){
    document.getElementById("TypeCombo").value = "";
    document.getElementById("label").value = "";
    document.getElementById("brand").value = "";
    document.getElementById("location").value = "";
    document.getElementById("serial_number").value = "";
    
}

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
    start_loading();
    
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
            stop_loading();
    });    
}

function save_specs_names(){
    var specs_names = [];
    var specs_ids = [];
    var type = document.getElementById("TypeCombo").value;
    var specs_names_temp = document.getElementsByClassName("spec_name");
    for(var i = 0 ; i < specs_names_temp.length ; i++){
        specs_names.push(specs_names_temp[i].innerHTML);       
    }
    var specs_ids_temp = document.getElementsByClassName("spec_id");
    for(var j = 0 ; j < specs_ids_temp.length ; j++){
        specs_ids.push(specs_ids_temp[j].value);       
    }
    $.post('save_specs_names', {type: type, specs_names: specs_names, spec_id: spec_id, specs_ids: specs_ids}, 
        function(returnedData){
            
    });
}

function generate_extra_specs(type_selected){
    $("#ExtraSpecs").html('');
    
    $.post('get_specs', {type_selected: type_selected}, 
        function(returnedData){
                document.getElementById("ExtraSpecs").innerHTML = "<strong>Extra specs<\/strong><br>";
                for( i = 0 ; i < returnedData.length ; i++ ){
                    $("#ExtraSpecs").append("<input type=\"hidden\" value=\""+ returnedData[i].spec_id +"\" class=\"spec_id\" ><label class=\"spec_name\" contenteditable=\"true\" onblur=\"save_specs_names()\" >" + returnedData[i].spec_name + "<\/label><div class=\"add-row\"><input type=\"text\" class=\"spec\" name=\"" + returnedData[i].spec_name + "\"><\/div><br>");
                }
    }, 'json');
    
    // on lost focus
    $(".spec_name").focusout(function(){
        save_specs_names();
    });
}
function generate_extra_specs_for_edit(type_selected){
    $("#specs_to_edit").html('');
    
    $.post('get_specs', {type_selected: type_selected}, 
        function(returnedData){
                document.getElementById("specs_to_edit").innerHTML = "<strong>Specs<\/strong><br>";
                for( i = 0 ; i < returnedData.length ; i++ ){
                    $("#specs_to_edit").append("<div id='spec_in_edit_"+ returnedData[i].spec_id +"' class='spec_in_edit'><input type=\"hidden\" value=\""+ returnedData[i].spec_id +"\" class=\"spec_id\" ><label class=\"spec_name\" contenteditable=\"true\" onblur=\"save_specs_names()\" >" + returnedData[i].spec_name + "<\/label><img src=\"images/remove.png\" onClick=\"delete_spec('"+ returnedData[i].spec_id +"')\"><br><\/div>");
                }
    }, 'json');
    
    // on lost focus
    $(".spec_name").focusout(function(){
        save_specs_names();
    });
}
function delete_spec(spec_id){
    start_loading();
    document.getElementById("message-box").innerHTML = "Deleting spec. Please wait.";
    var type = document.getElementById("type_to_edit").value;
    $.post('delete_spec', {spec_id: spec_id, type: type}, function(returnedData){
        document.getElementById("message-box").innerHTML = returnedData;
        if(returnedData.includes("success")){
            document.getElementById("spec_in_edit_" + spec_id).remove();
        }
        stop_loading();
    });
}

function save_type_name(){
    start_loading();
    document.getElementById("message-box").innerHTML = "Saving type name. Please wait.";
    
    var old_type_name = document.getElementById("type_to_edit").value;
    var new_type_name = document.getElementById("name_to_save").value;
    
    $.post('update_type_name', {old_type_name: old_type_name, new_type_name: new_type_name}, function(returnedData){
        document.getElementById("message-box").innerHTML = returnedData;
        if(returnedData.includes("success")){
            document.getElementById("type_to_edit").value = new_type_name;
        }
        stop_loading();
    });
}

function start_loading(){
  document.getElementById("div_loading").style.display = "block";
}
function stop_loading(){
  document.getElementById("div_loading").style.display = "none";
}