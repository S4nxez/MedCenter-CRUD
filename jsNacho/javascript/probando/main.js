/*Dejar que se cargue el DOM antes, si tienes defer no hace falta
document.addEventListener("DOMContentLoaded", assignEvents);*/
function assignEvents() {
    document.getElementById("bClickMe").addEventListener("click", showMessage);
}
function showMessage() {
    let name = document.getElementById("name").value;
    alert("Hi there " + name + "! \nhyd?");
    document.getElementById("messagestatus").innerHTML = "Hi there " + name + "! \nhyd?";
    
}