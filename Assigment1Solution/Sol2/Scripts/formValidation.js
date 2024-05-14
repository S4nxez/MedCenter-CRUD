document.getElementById('name').addEventListener('keyup', checkNameLength);
document.getElementById('name').addEventListener('blur', showNameError);
document.getElementById('surname').addEventListener('keyup', checkSurnameLength);
document.getElementById('surname').addEventListener('blur', showSurnameError);
document.getElementById('address').addEventListener('keyup', checkAddress);
document.getElementById('address').addEventListener('blur', showAddressError);
document.getElementById('email').addEventListener('keyup', checkEmail);
document.getElementById('email').addEventListener('blur', showEmailError);
document.getElementById('password').addEventListener('keyup', checkPassword);
document.getElementById('password').addEventListener('keyup', checkConfirmPassword);
document.getElementById('password').addEventListener('blur', showPasswordError);
document.getElementById('confirmPassword').addEventListener('keyup', checkConfirmPassword);
document.getElementById('confirmPassword').addEventListener('blur', showConfirmPasswordError);
document.getElementById('dateOfBirth').addEventListener('blur', showDateError);
document.getElementById('terms').addEventListener('blur', showTermsError);
document.getElementById('resetButton').addEventListener('click', askUser);
document.getElementById('submitButton').addEventListener('click', submitForm);



function checkNameLength() {
    const name = document.getElementById('name').value;
    if (name.length < 2 || name.length > 15) {
        return false;
    } else {
        document.getElementById('nameErrorLabel').innerText = '';
        document.getElementById('nameErrorLabel').style.backgroundColor = 'Transparent';
        return true;
    }
}

function showNameError() {
    if (checkNameLength() == false) {
        document.getElementById('nameErrorLabel').innerText = 'Name must be between 2 and 15 characters long';
        document.getElementById('nameErrorLabel').style.backgroundColor = 'Red';
    }
}

function checkSurnameLength() {
    const surname = document.getElementById('surname').value;
    if (surname.length < 2 || surname.length > 15) {
        return false;
    } else {
        document.getElementById('surnameErrorLabel').innerText = '';
        document.getElementById('surnameErrorLabel').style.backgroundColor = 'Transparent';
        return true;
    }
}

function showSurnameError() {
    if (checkSurnameLength() == false) {
        document.getElementById('surnameErrorLabel').innerText = 'Surname must be between 2 and 15 characters long';
        document.getElementById('surnameErrorLabel').style.backgroundColor = 'Red';

    }
}

function checkAddress() {
    const address = document.getElementById('address').value;
    if (address === '') {
        return false;
    } else {
        document.getElementById('addressErrorLabel').innerHTML = '';
        document.getElementById('addressErrorLabel').style.backgroundColor = 'Transparent';

        return true;
    }
}

function showAddressError() {
    if (checkAddress() == false) {
        document.getElementById('addressErrorLabel').innerText = 'Address is required';
        document.getElementById('addressErrorLabel').style.backgroundColor = 'Red';

    }
}

function checkEmail() {
    const email = document.getElementById('email').value;
    const pattern = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    if (pattern.test(email)) {
        document.getElementById('emailErrorLabel').innerText = '';
        document.getElementById('emailErrorLabel').style.backgroundColor = 'Transparent';

        return true;
    } else {
        return false;
    }
}

function showEmailError() {
    if (checkEmail() == false) {
        document.getElementById('emailErrorLabel').innerText = 'Email not valid';
        document.getElementById('emailErrorLabel').style.backgroundColor = 'Red';
        
    }
}

function checkPassword() {
    const password = document.getElementById('password').value;
    const pattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{3,15}$/;
    if (pattern.test(password)) {
        document.getElementById('passwordErrorLabel').innerText = '';
        document.getElementById('passwordErrorLabel').style.backgroundColor = 'Transparent';

        return true;
    } else {
        return false;
    }
}

function showPasswordError() {
    if (checkPassword() == false) {
        document.getElementById('passwordErrorLabel').innerText = 'Password is not valid. It must have between 3 and 15 characters and contain at least one uppercase letter, one lowercase letter, one number and no special characters';
        document.getElementById('passwordErrorLabel').style.backgroundColor = 'Red';

    }
}

function checkConfirmPassword() {
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    if (password === confirmPassword) {
        document.getElementById('confirmPasswordErrorLabel').innerText = '';
        document.getElementById('confirmPasswordErrorLabel').style.backgroundColor = 'Transparent';

        return true;
    } else {
        return false;
    }
}

function showConfirmPasswordError() {
    if (checkConfirmPassword() == false) {
        document.getElementById('confirmPasswordErrorLabel').innerText = 'Both passwords must be the same';
        document.getElementById('confirmPasswordErrorLabel').style.backgroundColor = 'Red';

    }
}

function checkDate() {
    const currentDate = new Date();
    const birthDate = new Date(document.getElementById('dateOfBirth').value);
    let valid = false;
    
    if (currentDate.getFullYear() - birthDate.getFullYear() > 18) {
        valid = true;
    } else if (currentDate.getFullYear() - birthDate.getFullYear() == 18) {
        if (currentDate.getMonth() == birthDate.getMonth()) {
            if (currentDate.getDate() >= birthDate.getDate()) {
                valid = true;
            } else {
                valid = false;
            }
        } else if (currentDate.getMonth() < birthDate.getMonth()) {
            valid = false;
        } else {
            valid = true;
        }
    } else {
        valid = false;
    }
    
    return valid;

}

function showDateError() {
    if (checkDate() == true) {
        document.getElementById('dateErrorLabel').innerText = '';
        document.getElementById('dateErrorLabel').style.backgroundColor = 'Transparent';

    } else {
        document.getElementById('dateErrorLabel').innerText = 'You must be at least 18 years old';
        document.getElementById('dateErrorLabel').style.backgroundColor = 'Red';

    }
}

function checkTerms() {
    if (document.getElementById('terms').checked) {
        document.getElementById('termsErrorLabel').innerText = '';
        document.getElementById('termsErrorLabel').style.backgroundColor = 'Transparent';

        return true;
    } else {
        return false;
    }
}

function showTermsError() {
    if (!checkTerms()) {
        document.getElementById('termsErrorLabel').innerText = 'Terms must be accepted';
        document.getElementById('termsErrorLabel').style.backgroundColor = 'Red';

    }
}

function askUser(ev) {
    ev.preventDefault();
    const answer = confirm("Do you want to reset the form?");
    if (answer) {
        document.getElementById('form').reset();
    }

}

function checkForm() {
    if (checkNameLength() && checkSurnameLength() && checkAddress() && checkEmail() && checkPassword() && checkConfirmPassword() && checkDate() && checkTerms()) {
        return true;
    } else {
        return false;
    }

}

function submitForm(ev) {
    ev.preventDefault();
    if (checkForm()) {
        document.getElementById('form').submit();
    } else {
        alert('Form is not valid');
    }
}













