function type_value() //calls generate_specs_select and sends as a parameter the selected type name
{
  $(document).ready(function()
  {
    var x_timer;    
    d = document.getElementById("select_item_type_id");
    
    clearTimeout(x_timer);		        
    x_timer = setTimeout(function()
    {
    show_specs(d.value);}, 1000);
    
    function show_specs(selected_type)
    {
      $("#specs_region").html('');
      $("#specs_values_region").html('');
      $.get('generate_specs_select', {'selected_type':selected_type}, function(data)
      {
        $("#specs_region").html(data);
      });
    }
  });
}



function add_spec()
{
  var specs_select = document.getElementById("select_item_specs");  //select element that contains type relevant spec names
  var selected_spec = specs_select.value;
  var div = document.getElementById("specs_values_region")  //all new elements will be added in div
  
  var spec_label = document.createElement('label'); //label for the added spec 
  spec_label.innerHTML = selected_spec + ": ";
  spec_label.id = "label_" + selected_spec;
  div.appendChild(spec_label);  //add in div
  
  var input = document.createElement('input');  //input text for the added spec
  input.name = "spec_" + selected_spec + "_value_input";  //give relevant name
  input.id = "spec_" + selected_spec + "_value_input";  //give relevant id
  input.type = "text";  
  input.className = "specs_inputs_class"; //give all specs input elements same class name so that it is possible to retrieve all spec inputs by class
  div.appendChild(input);   //add in div
  
  var span = document.createElement('span');  //will contain the remove button of this added spec(to remove the label, input text element, the remove button, and br)
  var test = "<input type = 'button' id = '" + selected_spec + "' value = 'Remove' onclick = remove_spec('" + selected_spec + "');>"; //on button click call remove_spec in get_type_specs.js and give it as parameter the name of the selected spec
  span.innerHTML = test;
  div.appendChild(span);  //add in div
  
  var br1 = document.createElement('br'); //br for design purposes
  br1.id = "br1_" + selected_spec;
  var br2 = document.createElement('br'); //br for design purposes
  br2.id = "br2_" + selected_spec;
  div.appendChild(br1); //add in div
  div.appendChild(br2); //add in div
}

function remove_spec(removed_spec)  //function to remove the label, input text element, the remove button, and br of an added spec 
{
    var removed_label = document.getElementById("label_" + removed_spec); //get label to be removed 
    removed_label.outerHTML = "";
    delete removed_label; //remove
    
    var removed_input = document.getElementById("spec_" + removed_spec + "_value_input"); //get input text element to be removed
    removed_input.outerHTML = "";
    delete removed_input; //remove
    
    var removed_button = document.getElementById(removed_spec); //get remove button to be removed
    removed_button.outerHTML = "";
    delete removed_input; //remove
    
    var removed_br1 = document.getElementById("br1_" + removed_spec); //get br1 to be removed
    removed_br1.outerHTML = "";
    delete removed_br1; //remove
    
    var removed_br2 = document.getElementById("br2_" + removed_spec); //get br2 to be removed
    removed_br2.outerHTML = "";
    delete removed_br2; //remove
}
