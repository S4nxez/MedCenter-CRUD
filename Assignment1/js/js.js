document.getElementById("name").addEventListener("change", validate_name);
document.getElementById("email").addEventListener("change", validate_email);
document.getElementById("password").addEventListener("change", validate_pwd);
document.getElementById("confirmpassword").addEventListener("change", validate_confirm_pwd);
document.getElementById("empty").addEventListener("click", empty);
document.getElementById("miFormulario").addEventListener("submit", function(event) {
	const pwd = document.getElementById("password").value;
	const pwdConfirm = document.getElementById("confirmpassword").value;

	if (pwd !== pwdConfirm) {
		event.preventDefault();
	}
	});

function validate_name() {
	const numb = document.getElementById("name");
	const pNumb = document.getElementById("valMessageN");

	if (!numb.checkValidity()) {
		pNumb.innerHTML = numb.validationMessage;
		pNumb.style.backgroundColor = "red";
	} else {
		pNumb.innerHTML = null;
		pNumb.style.backgroundColor = "white";
	}
}

function validate_email() {
	const email = document.getElementById("email");
	const pEmail = document.getElementById("valMessageE");

	if (!email.checkValidity()) {
		pEmail.innerHTML = email.validationMessage;
		pEmail.style.backgroundColor = "red";
	} else {
		pEmail.innerHTML = null;
		pEmail.style.backgroundColor = "white";
	}
}

function validate_pwd() {
	const pwd = document.getElementById("password");
	const pPwd = document.getElementById("valMessageP");

	if(!pwd.checkValidity()){
		if (pwd.value.length < 8) {
			pPwd.innerHTML = "The password must have more than 8 characters.";
		} else if (!/[a-z]/.test(pwd.value)) {
			pPwd.innerHTML = "The password must contain at least one lowercase letter.";
		} else if (!/[A-Z]/.test(pwd.value)) {
			pPwd.innerHTML = "The password must contain at least one uppercase letter.";
		} else if (!/\d/.test(pwd.value)) {
			pPwd.innerHTML = "The password must contain at least one number.";
		} else {
			pPwd.innerHTML = pwd.validationMessage;
		}
		pPwd.style.backgroundColor = "red";
	}else {
		pPwd.innerHTML = null;
		pPwd.style.backgroundColor = "white";
	}
}

function validate_confirm_pwd(){
	const pwd = document.getElementById("password").value;
	const pwdConfirm = document.getElementById("confirmpassword").value;
	const pPwd = document.getElementById("valMessageC");

	if (!(pwd == pwdConfirm)){
		pPwd.innerHTML = "Passwords should match";
		pPwd.style.backgroundColor = "red";
	}else {
		pPwd.innerHTML = "";
		pPwd.style.backgroundColor = "white";
	}
}

function empty(ev) {
	ev.preventDefault();
	if (confirm("Do you want to clear the form?"))
		document.getElementById("miFormulario").reset();
}

document.addEventListener('DOMContentLoaded', function() {
	var maxDate = new Date();
	var maxDateString;

	maxDate.setFullYear(maxDate.getFullYear() - 18);
	maxDateString = maxDate.toISOString().slice(0, 10);
	document.getElementById('dateofbirth').setAttribute('max', maxDateString);
});
