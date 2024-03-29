document.getElementById("tbNumber").addEventListener("change", validateNumber)
document.getElementById("tbEmail").addEventListener("change", validateNumber)

function validateNumber() {
	const numb = document.getElementById("tbNumber");
	if (!numb.checkValidity()) {
		document.getElementById("valMessageN").innerHTML=numb.validationMessage;
		numb.style.backgroundColor="red"
	}
	else{
		document.getElementById("valMessageN").innerHTML=null;
		numb.style.backgroundColor="white"
	}
	const email = document.getElementById("tbEmail");
	if (!email.checkValidity()) {
		document.getElementById("valMessageE").innerHTML=email.validationMessage;
		email.style.backgroundColor="red"
	}
	else{
		document.getElementById("valMessageE").innerHTML=null;
		email.style.backgroundColor="white"
	}
}