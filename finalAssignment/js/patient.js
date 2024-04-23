document.getElementById("add1").addEventListener("click", addPatientModal);
getAllPatients()

function getAllPatients() {
	fetch ("https://informatica.iesquevedo.es/marcas/patients")
		.then (response => {
			if (!response.ok)
				throw new Error ('Network response is not ok')
			return response.json()
		})
		.then (data => {
			let tableBody = document.getElementById("patientTableBody");

			data.forEach(patient => {
				let row = createPatientRow(patient);
				tableBody.appendChild(row);
			})
		})
		.catch(error => {
			console.error("Error in the fetch: ", error)
		})
}

function createPatientRow(patient) {
	var row = document.createElement("tr");
	row.innerHTML =	"<td>" + patient.id +"</td>" +
		"<td>" + patient.name +"</td>" +
		"<td>" + patient.phone +"</td>" +
		"<td><button class='btn btn-info btn-sm' >Get info</button></td>" +
		"<td><button class='btn btn-danger btn-sm' >Delete</button></td>" +
		"<td><button class='btn btn-primary btn-sm' >Update</button></td>";

	//row.querySelector(".btn-info").addEventListener("click", getInfo);
	//row.querySelector(".btn-danger").addEventListener("click", deletePatient);
	//row.querySelector(".btn-primary").addEventListener("click", showUpdatePatientModalW);
	return row;
}

function addPatientModal() {
	let addPatientModal = new bootstrap.Modal(document.getElementById('addPatientModal'));
	addPatientModal.show();
}