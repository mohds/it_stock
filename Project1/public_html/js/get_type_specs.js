function type_value()
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
  var specs_select = document.getElementById("select_item_specs");
  var selected_spec = specs_select.value;
  var div = document.getElementById("specs_values_region")
  
  var spec_label = document.createElement('label');
  spec_label.innerHTML = selected_spec + ": ";
  spec_label.id = "label_" + selected_spec;
  div.appendChild(spec_label);
  
  var input = document.createElement('input');
  input.name = "spec_" + selected_spec + "_value_input";
  input.id = "spec_" + selected_spec + "_value_input";
  input.type = "text";
  input.className = "inputs_class";
  div.appendChild(input);
  
  var span = document.createElement('span');
  var test = "<input type = 'button' id = '" + selected_spec + "' value = 'Remove' onclick = remove_spec('" + selected_spec + "');>";
  span.innerHTML = test;
  div.appendChild(span);
  
  var br1 = document.createElement('br');
  br1.id = "br1_" + selected_spec;
  var br2 = document.createElement('br');
  br2.id = "br2_" + selected_spec;
  div.appendChild(br1);
  div.appendChild(br2);
}

function remove_spec(removed_spec)
{
    var removed_label = document.getElementById("label_" + removed_spec);
    removed_label.outerHTML = "";
    delete removed_label;
    
    var removed_input = document.getElementById("spec_" + removed_spec + "_value_input");
    removed_input.outerHTML = "";
    delete removed_input;
    
    var removed_button = document.getElementById(removed_spec);
    removed_button.outerHTML = "";
    delete removed_input;
    
    var removed_br1 = document.getElementById("br1_" + removed_spec);
    removed_br1.outerHTML = "";
    delete removed_br1;
    
    var removed_br2 = document.getElementById("br2_" + removed_spec);
    removed_br2.outerHTML = "";
    delete removed_br2;
}
