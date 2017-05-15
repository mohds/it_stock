
function show_specs(item_id)  //show popup containing item details
{
start_loading();
  $(function() {
      $("#dialog").dialog({ //settings for the dialog box
              autoOpen: false,
              resizable: false,
              width: 'auto',
              show: {
                  effect: "slide",
                  duration: 200
              },
              hide: {
                  effect: "slide",
                  duration: 200
              }
      });      
      
      $.get('get_specs_popup', {'item_id':item_id}, function(data)
      {
        $("#specs_content_region").html('');
        $("#receipt_content_region").html('');
        $("#specs_content_region").html(data);
        $('#dialog').dialog("open"); 
        stop_loading();
      });
  });
}

function update_popup(item_id)  //function to update item information
{
start_loading();
  $(document).ready(function()
    {
      var x_timer;    
      var popup_spec_input_elements = document.getElementsByClassName("popup_spec_inputs"); //contains spec input text elements in popup (not new specs)
      var popup_input_names = new Array();  //array will contain spec input text elements ids (not new specs)
      var popup_input_values = new Array(); //array will contain added spec input text elements values  (not new specs)
      
      var popup_new_specs_input_elements = document.getElementsByClassName("popup_new_specs_inputs_class"); //contains added spec input text elements in popup (new specs)
      var popup_new_specs_input_names = new Array();  //array will contain added spec input text elements ids (new specs)
      var popup_new_specs_input_values = new Array(); //array will contain added spec input text elements values (new specs)
      
      //retrieve general information values from popup
      //
      //
      
      var popup_select_brand = document.getElementById('popup_select_item_brand_id').value;
      var popup_input_model = document.getElementById('popup_model_input_id').value;
      var popup_select_location = document.getElementById('popup_select_item_location_id').value;
      var popup_select_condition = document.getElementById('popup_select_item_condition_id').value;
      var popup_input_label = document.getElementById('popup_label_input_id').value;
      var popup_input_keyword = document.getElementById('popup_keyword_input_id').value;
      var popup_input_sn = document.getElementById('popup_sn_input_id').value;
      var popup_input_notes = document.getElementById('popup_notes_input_id').value;
      
      for(var i = 0; i<popup_spec_input_elements.length; ++i) //for every spec in popup (not new specs)
      {
        var name = popup_spec_input_elements[i].id; //get input text element id
        var value = popup_spec_input_elements[i].value; //get input text element value
        popup_input_names.push(name); //add input text element id in corresponding array
        popup_input_values.push(value); //add input text element value in corresponding array
      }
      
      for(var k = 0; k<popup_new_specs_input_elements.length; ++k)  //for every added spec in popup (new specs)
      {
        var name_new = popup_new_specs_input_elements[k].id;  //get input text element id  
        var value_new = popup_new_specs_input_elements[k].value;  //get input text element value
        popup_new_specs_input_names.push(name_new); //add input text element id in corresponding array
        popup_new_specs_input_values.push(value_new); //add input text element value in corresponding array
      }
      
      clearTimeout(x_timer);		        
      x_timer = setTimeout(function()
      {
        send_to_servlet(popup_input_names,popup_input_values,popup_new_specs_input_names,popup_new_specs_input_values,popup_select_brand,popup_input_model,popup_select_location,popup_select_condition,popup_input_label,popup_input_keyword,popup_input_sn,popup_input_notes);
      }, 1000);
        
      function send_to_servlet(popup_specs_names,popup_specs_values,popup_new_specs_names,popup_new_specs_values,popup_brand,popup_model,popup_location,popup_condition,popup_label,popup_keyword,popup_sn,popup_notes)
      {
      $.get('popup_update', {'popup_specs_names':popup_specs_names, 'popup_specs_values':popup_specs_values,'popup_new_specs_names':popup_new_specs_names, 'popup_new_specs_values':popup_new_specs_values, 'popup_brand':popup_brand, 'popup_model':popup_model, 'popup_location':popup_location,'popup_condition':popup_condition, 'popup_label':popup_label,'popup_keyword':popup_keyword,'popup_sn':popup_sn,'popup_notes':popup_notes, 'item_id':item_id}, function(data)
      {
        show_specs(item_id);  //update item popup view
        send_specs(); //update search results
        stop_loading();
        alert("Item successfully updated.");
      });
      }
    });
}



function popup_add_spec() 
{
  var popup_specs_select = document.getElementById("select_item_specs_popup_id"); //select element that contains type relevant spec names (in popup)
  var selected_spec = popup_specs_select.value;
  if(selected_spec != 'Please select')
  {
    var div = document.getElementById("popup_specs_values_region")  //all new elements will be added in div (in popup) 
    var spec_label = document.createElement('label'); //label for the added in spec
    spec_label.innerHTML = selected_spec + ": ";
    spec_label.id = "popup_label_" + selected_spec;
    div.appendChild(spec_label);  //add in div
    
    var input = document.createElement('input');  //input text element for added spec
    input.name = "popup_spec_" + selected_spec + "_value_input";  //give relevant name
    input.id = "popup_spec_" + selected_spec + "_value_input";  //give relevant id
    input.type = "text";
    input.className = "popup_new_specs_inputs_class"; //give all specs input elements same class name so that it is possible to retrieve all popup spec inputs by class
    div.appendChild(input);
    
    var span = document.createElement('span');  //will contain the remove button of this added spec in popup (to remove the label, input text element, the remove button, and br)
    var test = "<button id = '" + selected_spec + "' onclick = popup_remove_spec('" + encodeURIComponent(selected_spec) + "');>Remove</button>"; //on button click call popup_remove_spec in popup_jquery.js and give it as parameter the name of the selected spec
    span.innerHTML = test;
    div.appendChild(span);  //add in div
    
    var br1 = document.createElement('br'); //br for design purposes
    br1.id = "popup_br1_" + selected_spec;
    var br2 = document.createElement('br'); //br for design purposes
    br2.id = "popup_br2_" + selected_spec;
    div.appendChild(br1); //add in div
    div.appendChild(br2); //add in div
    $("#select_item_specs_popup_id option[value='" + selected_spec + "']").remove();
  }
}

function popup_remove_spec(removed_spec)  //function to remove the label, input text element, the remove button, and br of an added spec (in popup)
{
    removed_spec = removed_spec.replace(/%20/g," ");
    var removed_label = document.getElementById("popup_label_" + removed_spec); //get label to be removed
    removed_label.outerHTML = "";
    delete removed_label; //remove
    
    var removed_input = document.getElementById("popup_spec_" + removed_spec + "_value_input"); //get input text element to be removed
    removed_input.outerHTML = "";
    delete removed_input; //remove
    
    var removed_button = document.getElementById(removed_spec); //get remove button to be removed
    removed_button.outerHTML = "";
    delete removed_input; //remove
    
    var removed_br1 = document.getElementById("popup_br1_" + removed_spec); //get br1 to be removed
    removed_br1.outerHTML = "";
    delete removed_br1; //remove
    
    var removed_br2 = document.getElementById("popup_br2_" + removed_spec); //get br2 to be removed
    removed_br2.outerHTML = "";
    delete removed_br2; //remove
    
    $("#select_item_specs_popup_id").append("<option value='" + removed_spec + "'>" + removed_spec + "</option>");
}

function show_receipt()  //show popup containing receipt details
{
start_loading();
  $(function()
  {
          $("#dialog").dialog({ //settings for the dialog box
                  autoOpen: false,
                  resizable: false,
                  width: 'auto',
                  effect: 'slide'
          });
         var ids_array = [];
          $("#items_in_cart_table tr").each(function(){
              ids_array.push($(this).find("td:first").text()); //put elements into array
          });
          $.get('show_receipt_popup', {'ids_array':ids_array}, function(data)
          {
            $("#specs_content_region").html('');
            $("#receipt_content_region").html('');
            $("#receipt_content_region").html(data);
            $('#dialog').dialog("open"); 
            stop_loading();
          });
  });
}

function set_expected_return_date() //function to set all records expected date of return the same as the receipt expected date of return (global)
{
  var global_return_date = document.getElementById('global_expected_date_id').value;
  var receipt_item_return_dates = document.getElementsByClassName("records_expected_date_class");
  for(var i = 0 ; i < receipt_item_return_dates.length ; ++i)
  {
    receipt_item_return_dates[i].value = global_return_date;
  }
}

function create_receipt() //function to create receipt after checkout is complete
{
    var client_name = document.getElementById('receipt_client_name_id').value;
    var receiver_name = document.getElementById('receipt_receiver_name_id').value;
    var receipt_country = $("#receipt_location_id option:selected").text();
    if(client_name ==  '' || receiver_name == '' || receipt_country == '')
    {
      alert('All elements preceded by * must be entered');
    }
    else
    {
      if(confirm('Create receipt?'))
      {
        var x_timer;   
        start_loading();
        var receipt_notes = document.getElementById('receipt_notes_id').value;
        var current_date_time = document.getElementById('current_date_time_id').value;
        var admin_id = document.getElementById('admin_id_id').value;
        var admin = document.getElementById('admin_name_id').value;
        var global_expected_date = document.getElementById('global_expected_date_id').value;  
        var records_items_ids = document.getElementsByClassName('item_ids_class');  
        var records_expected_dates_of_return = document.getElementsByClassName('records_expected_date_class');
        var records_notes = document.getElementsByClassName('records_notes_class'); 
        var records_returning = document.getElementsByClassName('records_returning_class');
        
        var array_records_items_ids = new Array();  //will contain records item ids
        var array_records_expected_dates_of_return = new Array(); //will contain records expected dates of return
        var array_records_notes = new Array();  //will contain record notes
        var array_records_returning = new Array();   //will contain whether or not an item from record will be returned
        
        for(var k = 0 ; k < records_items_ids.length ; ++k)
        {
          array_records_items_ids.push(records_items_ids[k].innerText);
          array_records_expected_dates_of_return.push(records_expected_dates_of_return[k].value);
          array_records_notes.push(records_notes[k].value);
          array_records_returning.push(records_returning[k].value);
        }
        
        clearTimeout(x_timer);		        
        x_timer = setTimeout(function()
        {
          //send parameters to 2 servlets
          //create_receipt_and_records to create the receipt and records in the database
          //create_receipt_form to create a pdf file from the receipt and also a printable form of the receipt in the browser
          
          send_to_servlet(array_records_items_ids,array_records_expected_dates_of_return,array_records_notes,array_records_returning,client_name,receiver_name,receipt_notes,current_date_time,admin_id,admin,global_expected_date,receipt_country);
        }, 1000);
        
        function send_to_servlet(get_array_records_items_ids,get_array_records_expected_dates_of_return,get_array_records_notes,get_array_records_returning,get_client_name,get_receiver_name,get_receipt_notes,get_current_date_time,get_admin_id,get_admin,get_global_expected_date,get_receipt_country)
        {
          $.get('create_receipt_and_records', {'records_items_id':get_array_records_items_ids, 'records_expected_dates_of_return':get_array_records_expected_dates_of_return,'records_notes':get_array_records_notes, 'records_returning':get_array_records_returning, 'client_name':get_client_name, 'receiver_name':get_receiver_name, 'receipt_notes':get_receipt_notes,'current_date_time':get_current_date_time, 'admin_id':get_admin_id, 'global_expected_date':get_global_expected_date,'receipt_country':get_receipt_country}, function(data)
          {
            //alert("Receipt successfully created.");
            send_specs(); //update search results
            $.get('create_receipt_form', {'records_items_id':get_array_records_items_ids, 'records_expected_dates_of_return':get_array_records_expected_dates_of_return,'records_notes':get_array_records_notes, 'records_returning':get_array_records_returning, 'client_name':get_client_name, 'receiver_name':get_receiver_name, 'receipt_notes':get_receipt_notes,'current_date_time':get_current_date_time, 'admin_id':get_admin_id, 'admin':get_admin, 'global_expected_date':get_global_expected_date,'receipt_country':get_receipt_country}, function(data)
            {
              stop_loading();
              myWindow = window.open(); //create new tab
              myWindow.document.body.innerHTML = data;  //generated html from create_receipt_form will make up the new tab
              if(document.getElementById('print_checkbox_id').checked)
              {
                myWindow.print(); //print the opened tab
              }
            });
          });
          $("#items_in_cart_table td").remove();  //empty all entries in the items cart table
        }
        $("#dialog").dialog('close'); //close receipt popup
    }
  }
}
$( function() {
    $( ".datepicker" ).datepicker({showOn: "button",buttonImage: 'images/calendar.gif',buttonImageOnly: true,buttonText: "Select date", dateFormat: 'dd/mm/yy'});
  } );
  
  
function delete_item_spec(item_id, spec_id) //function to delete existing spec from item
{
start_loading();
  $(document).ready(function()
    {
      var x_timer;
      clearTimeout(x_timer);		        
      x_timer = setTimeout(function()
      {
        send_to_servlet(item_id,spec_id);
      }, 1000);
      function send_to_servlet(get_item_id,get_spec_id)
      {
      $.get('delete_item_spec', {'item_id':get_item_id, 'spec_id':get_spec_id}, function(data)
      {
        show_specs(get_item_id);  //update item popup view
        send_specs(); //update search results
      });
      }
    });
}





///////////// new client handler //////////////
    
// popup

$(document).ready(function(){
    $("#NewClientPopup").dialog({
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
});

$(document).ready(function(){
    $("#NewLocationPopup").dialog({
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
});

$(document).ready(function(){
    $("#NewRemoteLocationPopup").dialog({
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
});

function display_new_client_popup()
{
    $("#NewClientPopup").dialog("open");
}

function save_new_client()
{
    var client = document.getElementById("NewClientName").value;
    $.post('add_client', {client: client}, function(returnedData)
    {
        $("#NewClientPopup").dialog("close");
        show_receipt();
    });
}

function display_new_location_popup()
{
    $("#NewLocationPopup").dialog("open");
}

function display_new_remote_location_popup()
{
    $("#NewRemoteLocationPopup").dialog("open");
}

function save_new_location()
{
    var location = document.getElementById("NewLocationName").value;
    $.post('add_location', {location: location}, function(returnedData)
    {
        $("#NewLocationPopup").dialog("close");
        var location_combo = document.getElementById("popup_select_item_location_id");
        var option = document.createElement("option");
        option.text = returnedData;
        option.value = returnedData;
        location_combo.add(option);
    });
}

function save_new_remote_location()
{
    var location = document.getElementById("NewRemoteLocationName").value;
    $.post('add_remote_location', {location: location}, function(returnedData)
    {
        $("#NewRemoteLocationPopup").dialog("close");
        var location_combo = document.getElementById("receipt_location_id");
        var option = document.createElement("option");
        option.text = returnedData;
        location_combo.add(option);
    });
}


function start_loading()
{
  document.getElementById("div_loading").style.display = "block";
}
function stop_loading()
{
  document.getElementById("div_loading").style.display = "none";
}

function sort_select_edit_item_brand()
{
  var my_options = $("#popup_select_item_brand_id option");
  var selected = $("#popup_select_item_brand_id").val();
  
  my_options.sort(function(a,b) {
      if (a.text > b.text) return 1;
      if (a.text < b.text) return -1;
      return 0
  })
  
  $("#popup_select_item_brand_id").empty().append( my_options );
  $("#popup_select_item_brand_id").val(selected);
}

function sort_select_edit_item_location()
{
  var my_options = $("#popup_select_item_location_id option");
  var selected = $("#popup_select_item_location_id").val();
  
  my_options.sort(function(a,b) {
      if (a.text > b.text) return 1;
      if (a.text < b.text) return -1;
      return 0
  })
  
  $("#popup_select_item_location_id").empty().append( my_options );
  $("#popup_select_item_location_id").val(selected);
}

function sort_select_edit_item_condition()
{
  var my_options = $("#popup_select_item_condition_id option");
  var selected = $("#popup_select_item_condition_id").val();
  
  my_options.sort(function(a,b) {
      if (a.text > b.text) return 1;
      if (a.text < b.text) return -1;
      return 0
  })
  
  $("#popup_select_item_condition_id").empty().append( my_options );
  $("#popup_select_item_condition_id").val(selected);
}

function sort_select_receipt_client()
{
  var my_options = $("#receipt_client_name_id option");
  var selected = $("#receipt_client_name_id").val();
  
  my_options.sort(function(a,b) {
      if (a.text > b.text) return 1;
      if (a.text < b.text) return -1;
      return 0
  })
  
  $("#receipt_client_name_id").empty().append( my_options );
  $("#receipt_client_name_id").val(selected);
}

function sort_select_receipt_receiver()
{
  var my_options = $("#receipt_receiver_name_id option");
  var selected = $("#receipt_receiver_name_id").val();
  
  my_options.sort(function(a,b) {
      if (a.text > b.text) return 1;
      if (a.text < b.text) return -1;
      return 0
  })
  
  $("#receipt_receiver_name_id").empty().append( my_options );
  $("#receipt_receiver_name_id").val(selected);
}

function sort_select_receipt_country()
{
  var my_options = $("#receipt_location_id option");
  var selected = $("#receipt_location_id").val();
  
  my_options.sort(function(a,b) {
      if (a.text > b.text) return 1;
      if (a.text < b.text) return -1;
      return 0
  })
  
  $("#receipt_location_id").empty().append( my_options );
  $("#receipt_location_id").val(selected);
}