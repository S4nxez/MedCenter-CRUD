document.getElementById("name").addEventListener("change", validate_name);
document.getElementById("email").addEventListener("change", validate_email);
document.getElementById("password").addEventListener("change", validate_pwd);
document.getElementById("confirmpassword").addEventListener("change", validate_confirm_pwd);
document.getElementById("empty").addEventListener("click", empty);
document.getElementById("dateofbirth").addEventListener("change", validate_dob)
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
	const pattern = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;

	if (!pattern.test(email.value)) {
		pEmail.innerHTML = "Please enter a valid email address.";
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

function validate_dob() {
	const dob = document.getElementById("dateofbirth");
	const pDob = document.getElementById("valMessageB");
	const selectedDate = new Date(dob.value);
	const currentDate = new Date();

	let age = currentDate.getFullYear() - selectedDate.getFullYear();
	const m = currentDate.getMonth() - selectedDate.getMonth();
	if (m < 0 || (m === 0 && currentDate.getDate() < selectedDate.getDate())) {
		age--;
	}

	if (age < 18) {
		pDob.innerHTML = "You must be at least 18 years old.";
		pDob.style.backgroundColor = "red";
	} else {
		pDob.innerHTML = null;
		pDob.style.backgroundColor = "white";
	}
}