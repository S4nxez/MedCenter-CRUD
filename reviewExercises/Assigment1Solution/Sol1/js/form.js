assingEvents()

function assingEvents() {

    document.getElementById("rButton").addEventListener("click", askUser);
    document.getElementById("name").addEventListener("blur", validateName);
    document.getElementById("surname").addEventListener("blur", validateSurname);
    document.getElementById("address").addEventListener("blur", validateAddress);
    document.getElementById("email").addEventListener("blur", validateEmail);
    document.getElementById("confirmpassword").addEventListener("blur", validateConfirmPassword);
    document.getElementById("dateofbirth").addEventListener("blur", validateDateOfBirth);
    document.getElementById("sButton").addEventListener("click", validateAddress);
    document.getElementById("sButton").addEventListener("click", validateAll);

}

//Validations

function validateName(ev) {
   

    let nameU = document.getElementById("name");
    let mErrorN = document.getElementById("valName");

    if (nameU.value.length < 2 || nameU.value.length > 15) {
        mErrorN.innerHTML = "The name must be between 2 to 15 characters";
        mErrorN.style.color = "white";
        mErrorN.style.backgroundColor = "red";


    } else {
        mErrorN.style.backgroundColor = "white";
        mErrorN.innerHTML = null;
    }

}

function validateSurname(ev) {
    ev.preventDefault();

    let surnameU = document.getElementById("surname");
    let mErrorS = document.getElementById("valSurname");

    if (surnameU.value.length < 2 || surnameU.value.length > 15) {

        mErrorS.innerHTML = "The surname must be between 2 to 15 characters";
        mErrorS.style.color = "white";
        mErrorS.style.backgroundColor = "red";
    } else {
        mErrorS.style.backgroundColor = "white";
        mErrorS.innerHTML = null;
    }


}

function validateAddress(ev) {
    ev.preventDefault();
    
    let adressB = document.getElementById("address");
    let mErrorAD = document.getElementById("valAd");

    if (adressB.value.length == 0) {

        mErrorAD.innerHTML = "You must to put something in address";
        mErrorAD.style.color = "white";
        mErrorAD.style.backgroundColor = "red";

    } else {

        mErrorAD.style.backgroundColor = "white";
        mErrorAD.innerHTML = null;

    }
}

function validateEmail(ev) {
    ev.preventDefault();

    let emailU = document.getElementById("email");
    let pattern = (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/);
    let mErrorE = document.getElementById("valEmail");

    if (!(pattern.test(emailU.value))) {

        mErrorE.innerHTML = "Email not valid";
        mErrorE.style.color = "white";
        mErrorE.style.backgroundColor = "red";
    } else {
        mErrorE.style.backgroundColor = "white";
        mErrorE.innerHTML = null;
    }
}

function validateConfirmPassword(ev) {
    ev.preventDefault();

    let password = document.getElementById("password");
    let confirmPassword = document.getElementById("confirmpassword");
    let mErrorCP = document.getElementById("valCP");

    if (password.value != confirmPassword.value) {

        mErrorCP.innerHTML = "You must introduce the same password";
        mErrorCP.style.color = "white";
        mErrorCP.style.backgroundColor = "red";
    } else {
        mErrorCP.style.backgroundColor = "white";
        mErrorCP.innerHTML = null;
    }
}

function validateDateOfBirth(ev) {
    ev.preventDefault();

    let dateofbirth = new Date(document.getElementById("dateofbirth").value)
    let todaydate = new Date();

       var age = todaydate.getFullYear() - dateofbirth.getFullYear();
    if (dateofbirth.getMonth() > todaydate.getMonth() ||
        (dateofbirth.getMonth() === todaydate.getMonth() && dateofbirth.getDate() > todaydate.getDate())) {
        age--;
    }

    let mErrorDB = document.getElementById("valDate");

    if (age <= 18) {
        mErrorDB.innerHTML = "You need to be 18 year old or more to register";
        mErrorDB.style.color = "white";
        mErrorDB.style.backgroundColor = "red";
    } else {
        mErrorDB.style.backgroundColor = "white";
        mErrorDB.innerHTML = null;
    }
}

function checkValue(item) {
    if (item.innerHTML == '') {

        return 0;

    } else {

        return 1;

    }

}
function validateTerms() {

    const termsC = document.getElementById("termsofservice");
    let mErrorN = document.getElementById("valTerms");

    if (!termsC.checked) {
        mErrorN.innerHTML = "Need to accept the terms";
        mErrorN.style.color = "white";
        mErrorN.style.backgroundColor = "red";


    } else {
        mErrorN.style.backgroundColor = "white";
        mErrorN.innerHTML = null;
    }
}

//Submit

function validateAll(ev) {
    ev.preventDefault();
    validateTerms();
    let valid = 0;
    const mess = document.getElementsByClassName("message");

    for (let i = 0; i < mess.length; i++) {

        valid += checkValue(mess[i]);

    }

    let mErrorAll = document.getElementById("valAll");

    if (valid > 0) {
            mErrorAll.innerHTML = "Fix the errors before submitting, please";
            mErrorAll.style.color = "white";
            mErrorAll.style.backgroundColor = "red";


    } else {

        mErrorAll.style.backgroundColor = "white";
        mErrorAll.innerHTML = null;
        document.getElementById("dataForm").submit();

    }

}

//Reset

function askUser(ev) {
    ev.preventDefault();

    let answer = confirm("Are you sure that you want to reset the form?")
    if (answer) {
        document.getElementById("dataForm").reset();
    }

}