document.getElementById("ex10b").addEventListener("click", stringify)

function stringify(){
	let param1 = document.getElementById("param1").value;
	let param2 = document.getElementById("param2").value;
	let param3 = document.getElementById("param3").value;

	let object = {
		firstname	: param1,
		lastname	: param2,
		lastname2	: param3
	}
	if (areStrings(param1,param2,param3))
		console.log(JSON.stringify(object));
	else
		console.log("Params aren't strings");
}
function areStrings(param1, param2, param3) {
	return typeof param1 === 'string' && typeof param2 === 'string' && typeof param3 === 'string';
}