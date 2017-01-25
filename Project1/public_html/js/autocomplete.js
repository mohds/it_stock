$(document).ready(function(){
    // autocomplete boxes
    $("#TypeCombo").autocomplete({
        source: "load_types",
        minLength: 1,
        select: function(event, ui){
            var type_selected = ui.item;
            ajax_request(type_selected);
            
            
        }
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
    
});

function ajax_request(type_selected){
    $("#ExtraSpecs").html() = "";
    $.post('get_specs', {type_selected: "type_selected"}, 
        function(returnedData){
                for( i = 0 ; i < returnedData.length ; i++ ){
                    $("#ExtraSpecs").append('<label>' + returnedData[i] + ': <\/label><input type=\"text\" name=\"spec_' + returnedData[i] + '\"><button>New<\/button>');
                }
    }, 'json');
}