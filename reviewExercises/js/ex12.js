function formatName(json) {
	// Obtener los valores de las claves firstname, mediumname y lastname
	var firstname = json.firstname || '';
	var mediumname = json.mediumname || '';
	var lastname = json.lastname || '';

	// Concatenar los valores con espacios entre ellos y agregar un punto al final
	var formattedName = firstname + ' ' + mediumname + ' ' + lastname + '.';

	return formattedName.trim(); // Eliminar espacios en blanco adicionales al principio y al final
}

// Ejemplo de uso:
var person1 = { firstname: "John", mediumname: "Doe", lastname: "Smith" };
console.log(formatName(person1)); // Output: "John Doe Smith."