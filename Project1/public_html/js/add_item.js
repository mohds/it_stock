$(document).ready(function(){

    // spinner
    var spinner = $( "#count" ).spinner();

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
    $("#AddSpecToTypeDialog").dialog({
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
    $("#EditBrandDialog").dialog({
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
    $("#EditLocationDialog").dialog({
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
        generate_type_combo();
        document.getElementById("specs_to_edit").innerHTML = "";
        document.getElementById("name_to_save").value = "";
    });
    $("#AddSpecToType").on("click", function(){
        add_spec_to_type();
    });
    $("#EditBrandButton").on("click", function(){
        $("#EditBrandDialog").dialog("open");
        generate_brand_combo();
        document.getElementById("brand_to_save").value = "";
    });
    $("#EditLocationButton").on("click", function(){
        $("#EditLocationDialog").dialog("open");
        generate_location_combo();
        document.getElementById("location_to_save").value = "";
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
    
    // date boxes
    $("#WarrantyStartDate").datepicker({
        changeYear: true,
        changeMonth: true,
        dateFormat: "dd/mm/yy"
    });
    $("#WarrantyEndDate").datepicker({
        changeYear: true,
        changeMonth: true,
        dateFormat: "dd/mm/yy"
    });
});

function add_spec_to_type(){
    start_loading();    
    var type = document.getElementById("type_to_edit").value;
    var spec = document.getElementById("spec_to_add").value;
    
    $.post('add_spec_to_type', {type: type, spec: spec}, function(returnedData){
        $("#AddSpecToTypeDialog").dialog("close");
        document.getElementById("spec_to_add").value = "";        
        generate_extra_specs_for_edit(type);
        document.getElementById("message-box").innerHTML = returnedData;
        stop_loading();
    });
}

function open_add_spec_to_type_dialog(){
    $("#AddSpecToTypeDialog").dialog("open");
    $("#spec_to_add").autocomplete({
        source: "get_all_specs_filtered",
        minLength: 1
    });
}

function edit_brand_on_change(){
    var brand_selected = document.getElementById("brand_to_edit").value;
    document.getElementById("brand_to_save").value = brand_selected;
}
function edit_location_on_change(){
    var location_selected = document.getElementById("location_to_edit").value;
    document.getElementById("location_to_save").value = location_selected;
}

function generate_extra_specs_for_edit_event(){
    var type_selected = document.getElementById("type_to_edit").value;
    document.getElementById("name_to_save").value = type_selected;
    generate_extra_specs_for_edit(type_selected);
}

function clear_input(){
    document.getElementById("message-box").innerHTML = "";
    
    document.getElementById("TypeCombo").value = "";
    document.getElementById("label").value = "";
    document.getElementById("label-result").innerHTML = "";
    document.getElementById("brand").value = "";
    document.getElementById("location").value = "";
    document.getElementById("serial_number").value = "";
    document.getElementById("serial-result").innerHTML = "";
    document.getElementById("model").value = "";
    document.getElementById("keyword").value = "";
    document.getElementById("ExtraSpecs").innerHTML = "<strong>Extra specs<\/strong>";    
    document.getElementById("notes").value = "";
    var select = document.getElementById("condition");
    select.selectedIndex = select.options[0];
    document.getElementById("count").value = "1";
    
}

// populate conditions select box
// get_conditions();

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
            document.getElementById("TypeCombo").value = type;
            generate_extra_specs(type);
    }, 'json');
    
    $("#NewTypeDialog").dialog("close");
    $("#specs_list").html('');
    document.getElementById("type_name").value = "";
    
}

function add_brand(){
    var brand = document.getElementById("brand_to_add").value;    
    $.post('add_brand', {brand: brand}, 
        function(returnedData){   
            document.getElementById("brand").value = brand;
    }, 'json');    
    $("#NewBrandDialog").dialog("close");
    document.getElementById("brand_to_add").value = "";
}
function add_location(){

    var location = document.getElementById("location_to_add").value;
    
    $.post('add_location', {location: location}, 
        function(returnedData){
            document.getElementById("location").value = location;
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
    var model = document.getElementById("model").value;
    var keyword = document.getElementById("keyword").value;
    var notes = document.getElementById("notes").value;
    var count = document.getElementById("count").value;
    var InvoiceImage = document.getElementById("InvoiceImage").value;
    
    // second we get the extra specs attributes
    var specs_names = [];
    var specs_values = [];
    var specs_temp = document.getElementsByClassName("spec");
    for(var i = 0 ; i < specs_temp.length ; i++){
        specs_names.push(specs_temp[i].getAttribute("name"));
        specs_values.push(specs_temp[i].value);
    }
    //if(count > 0 && count < 101){
        //for(var i = 0 ; i < count ; i++){
            $.post('add_item', {type: type, brand: brand, location: location, label: label, serial_number: serial_number, condition: condition, specs_names: specs_names, specs_values: specs_values, model: model, keyword: keyword, notes: notes, count: count, InvoiceImage: InvoiceImage}, 
                function(returnedData){
                    $("#message-box").html(returnedData);
                    stop_loading();
            });
        //}
    //}
    /*else{
        $("#message-box").html("Count error.");
        stop_loading();
    }*/
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
    start_loading();
    $.post('get_specs', {type_selected: type_selected}, 
        function(returnedData){
                document.getElementById("specs_to_edit").innerHTML = "<strong>Specs<\/strong><br>";
                for( i = 0 ; i < returnedData.length ; i++ ){
                    $("#specs_to_edit").append("<div id='spec_in_edit_"+ returnedData[i].spec_id +"' class='spec_in_edit'><input type=\"hidden\" value=\""+ returnedData[i].spec_id +"\" class=\"spec_id\" ><label class=\"spec_name\" contenteditable=\"true\" onblur=\"save_specs_names()\" >" + returnedData[i].spec_name + "<\/label><img src=\"images/remove.png\" onClick=\"delete_spec('"+ returnedData[i].spec_id +"')\"><br><\/div>");
                }
                stop_loading();
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
            generate_type_combo();
            document.getElementById("specs_to_edit").innerHTML = "";
            document.getElementById("name_to_save").value = "";
        }
        stop_loading();
    });
}
function save_brand_name(){
    start_loading();
    document.getElementById("message-box").innerHTML = "Saving brand name. Please wait.";
    
    var old_brand_name = document.getElementById("brand_to_edit").value;
    var new_brand_name = document.getElementById("brand_to_save").value;
    
    $.post('update_brand_name', {old_brand_name: old_brand_name, new_brand_name: new_brand_name}, function(returnedData){
        document.getElementById("message-box").innerHTML = returnedData;
        if(returnedData.includes("success")){
            generate_type_combo();
            document.getElementById("brand_to_save").value = "";
            $("#EditBrandDialog").dialog("close");
        }
        stop_loading();
    });
}
function save_location_name(){
    start_loading();
    document.getElementById("message-box").innerHTML = "Saving location name. Please wait.";
    
    var old_location_name = document.getElementById("location_to_edit").value;
    var new_location_name = document.getElementById("location_to_save").value;
    
    $.post('update_location_name', {old_location_name: old_location_name, new_location_name: new_location_name}, function(returnedData){
        document.getElementById("message-box").innerHTML = returnedData;
        if(returnedData.includes("success")){
            generate_type_combo();
            document.getElementById("location_to_save").value = "";
            $("#EditLocationDialog").dialog("close");
        }
        stop_loading();
    });
}

function generate_type_combo(){
    var html = "<select id=\"type_to_edit\" onchange=\"generate_extra_specs_for_edit_event()\">";
    var term = "";
    var options = "";
    options += "<option value=\"\"><\/option>";
    $.getJSON('get_types', {term: term}, function(returnedData){
        for(var i = 0 ; i < returnedData.length ; i++){
            options += "<option value=\""+ returnedData[i] +"\">"+ returnedData[i] +"<\/option>";
        }
        html += options;
        html += "<\/select>";
        document.getElementById("type-select-box").innerHTML = html;
    });    
}
function generate_brand_combo(){
    var html = "<select id=\"brand_to_edit\" onchange=\"edit_brand_on_change()\">";
    var term = "";
    var options = "";
    options += "<option value=\"\"><\/option>";
    $.getJSON('get_brands', {term: term}, function(returnedData){
        for(var i = 0 ; i < returnedData.length ; i++){
            options += "<option value=\""+ returnedData[i] +"\">"+ returnedData[i] +"<\/option>";
        }
        html += options;
        html += "<\/select>";
        document.getElementById("brand-select-box").innerHTML = html;
    });    
}
function generate_location_combo(){
    var html = "<select id=\"location_to_edit\" onchange=\"edit_location_on_change()\">";
    var term = "";
    var options = "";
    options += "<option value=\"\"><\/option>";
    $.getJSON('get_locations', {term: term}, function(returnedData){
        for(var i = 0 ; i < returnedData.length ; i++){
            options += "<option value=\""+ returnedData[i] +"\">"+ returnedData[i] +"<\/option>";
        }
        html += options;
        html += "<\/select>";
        document.getElementById("location-select-box").innerHTML = html;
    });    
}

function start_loading(){
  document.getElementById("div_loading").style.display = "block";
}
function stop_loading(){
  document.getElementById("div_loading").style.display = "none";
}