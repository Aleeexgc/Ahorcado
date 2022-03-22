function verClave(){
	
	 var tipo = document.getElementById("clave");

      if(tipo.type == "password"){
          tipo.type = "text";
      }else{
          tipo.type = "password";
      }

}

function verClave2(){
	
	 var tipo2 = document.getElementById("confirmaClave");

     
      
     if(tipo2.type == "password"){
          tipo2.type = "text";
      }else{
          tipo2.type = "password";
      }

}
function limpiarFormulario(formulario) {

  var elements = formulario.elements;

  formulario.reset();

  for(i=0; i<elements.length; i++) {

    field_type = elements[i].type.toLowerCase();

    switch(field_type) {
      case "text":
      case "email":
      case "password":
      case "textarea":
      case "hidden":
        elements[i].value = "";
        break;

      case "radio":
      case "checkbox":
          if (elements[i].checked) {
            elements[i].checked = false;
        }
        break;

      case "select-one":
      case "select-multiple":
        elements[i].selectedIndex = -1;
        break;

      default:
        break;
    }
  }
}

function llenarSelect(input) {
  select = Array.from(input.options);
  for (let i = 0; i < select.length; i++) {
    console.log((select[i].selected = true));
  }
}

function vaciarSelect(input) {
  select = Array.from(input.options);
  for (let i = 0; i < select.length; i++) {
    console.log((select[i].selected = false));
  }
}

  function deseleccionarRadio() { 
        	var ele = document.getElementsByName("siglasGenero");
        	   for(var i=0;i<ele.length;i++)
        	      ele[i].checked = false;
		}

function limpiarFormulario() {
    document.getElementById("alta").reset();
}


function seleccionarCheckboxes(input){
  input.forEach((element) => {
    element.checked = true;
  });
}

function deseleccionarCheckboxes(input){
  input.forEach((element) => {
    element.checked = false;
  });
}


function prueba(resultado) {

    if (resultado === "Usted ha ganado") {
        alert("Usted ha ganado");

    }

    if (resultado === "Usted ha perdido") {

        alert("Usted ha perdido");
    }
}

function alertCondicional(mensaje) {

    if (mensaje.length > 0) {

        alert(mensaje);
    }
}