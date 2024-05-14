document.getElementById("rows").addEventListener("change", function(){textContent("rows")});
document.getElementById("columns").addEventListener("change", function(){textContent("columns")});
document.getElementById("ex8b").addEventListener("click", function() {
	if (textContent("rows") && textContent("columns"))
		buttonPressed();
});

function textContent(id){
	let field = document.getElementById(id).value.trim(); // trim para quitar los espacios en blanco

	if (isNaN(field) || field === "" || Number(field) > 9 || Number(field) < 1)
		document.getElementById("wrongCredentials").textContent = "Wrong credentials. Please try again.";
	else
		document.getElementById("wrongCredentials").textContent = "";

	return field;
}

function buttonPressed(){
	let rows	= textContent("rows");
	let columns	= textContent("columns");
	let table	= document.createElement("table");
	let tblDiv	= document.getElementById("tableContainer");
	tblDiv.innerHTML = '';

	table.id="medRecordTable";
	table.className="table table-bordered table-condensed table-striped";
	for (let i = 0; i < rows; i++) {
		let row = document.createElement("tr");
		for (let o = 0; o < columns; o++) {
			let cell = document.createElement("td"); // Crea una nueva celda en cada iteración del bucle de columnas
			cell.textContent = "fila"; // Añade el contenido "fila" a cada celda
			row.appendChild(cell);
		}
		table.appendChild(row);
	}
	tblDiv.appendChild(table);
}
