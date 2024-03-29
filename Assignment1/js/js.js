document.getElementById("name").addEventListener("change", validateNumber);
document.getElementById("email").addEventListener("change", validateNumber);
document.getElementById("dateofbirth").addEventListener("change", validateNumber); // Agregamos el evento para el campo de fecha

function validateNumber() {
	const numb = document.getElementById("name");
	if (!numb.checkValidity()) {
		document.getElementById("valMessageN").innerHTML = numb.validationMessage;
		numb.style.backgroundColor = "red";
	} else {
		document.getElementById("valMessageN").innerHTML = null;
		numb.style.backgroundColor = "white";
	}

	const email = document.getElementById("email");
	if (!email.checkValidity()) {
		document.getElementById("valMessageE").innerHTML = email.validationMessage;
		email.style.backgroundColor = "red";
	} else {
		document.getElementById("valMessageE").innerHTML = null;
		email.style.backgroundColor = "white";
	}

	const dob = document.getElementById("dateofbirth");
	if (!dob.checkValidity()) {
		document.getElementById("valMessageB").innerHTML = dob.validationMessage;
		dob.style.backgroundColor = "red";
	} else {
		document.getElementById("valMessageB").innerHTML = null;
		dob.style.backgroundColor = "white";
	}
}

// Calculamos la fecha máxima para el campo de fecha (birthday)
document.addEventListener('DOMContentLoaded', function() {
	var maxDate = new Date();
	maxDate.setFullYear(maxDate.getFullYear() - 18);
	var maxDateString = maxDate.toISOString().slice(0, 10);
	document.getElementById('dateofbirth').setAttribute('max', maxDateString);
});
