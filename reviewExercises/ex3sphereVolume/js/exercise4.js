function calculateVolume(){
	let radius=parseFloat(document.getElementById("radius").value)
	let volume=(4*Math.PI*Math.pow(radius, 3))/3;
	document.getElementById("volume").value=volume;

}

	document.getElementById("calculate").addEventListener("click", calculateVolume);
