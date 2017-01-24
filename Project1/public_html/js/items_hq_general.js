function send_specs()
{
  $(document).ready(function()
  {
    var x_timer;    
    var input_elements = document.getElementsByClassName("inputs_class");
    var input_names = new Array();
    var input_values = new Array();
    for(var i = 0; i<input_elements.length; ++i)
    {
      var name = input_elements[i].id;
      var value = input_elements[i].value;
      input_names.push(name);
      input_values.push(value);
    }
    clearTimeout(x_timer);		        
    x_timer = setTimeout(function()
    {
    send_to_servlet(input_names,input_values);}, 1000);
    
    function send_to_servlet(specs_names,specs_values)
    {
      //$("#specs_region").html('');
      //$("#specs_values_region").html('');
      $.get('generate_items_table', {'specs_names':specs_names, 'specs_values':specs_values}, function(data)
      {
        //$("#specs_region").html(data);
      });
    }
  });
}
