var names = ["Aidan","Thing","me"];

$(document).ready(function(){
    $("#type_combo").autocomplete({
        source: ""
    });
});


function request() {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      document.getElementById("demo").innerHTML =
      this.responseText;
    }
  };
  xhttp.open("GET", "load_types", true);
  xhttp.send();
}