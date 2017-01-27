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
});

// populate conditions select box
get_conditions();

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
    // first we get the main three attributes
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
            
    }, 'json');    
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