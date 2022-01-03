//валидация данных на странице регистрации регулярными выражениями
function validate(email, regexpVal, message) {
    const regexp = regexpVal;
    var field = document.getElementById(email);
    if (regexp.test(field.value)) {
        document.getElementById("validateField").innerHTML = "";
        document.getElementById("sendButton").disabled = false;
    } else {
        document.getElementById("validateField").innerHTML = message;
        document.getElementById("sendButton").disabled = true;
    }
}

function validateUsername(username) {
    const regexp = /a/;
}

//document.getElementById("email").oninput = validateEmail;