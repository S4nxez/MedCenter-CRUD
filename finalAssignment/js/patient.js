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
		"<td><button class='btn btn-primary btn-sm ms-2' >Update</button></td>";

	//row.querySelector(".btn-info").addEventListener("click", getInfo);
	//row.querySelector(".btn-danger").addEventListener("click", deletePatient);
	row.querySelector(".btn-primary").addEventListener("click", showUpdatePatientModal);// esto habria que cambiarlo pero no se si lo arregló al final
	return row;
}

function addPatientModal() {
	let addPatientModal = new bootstrap.Modal(document.getElementById('addPatientModal'));
	addPatientModal.show();
}

function addPatient() {
	let patientName=document.getElementById("addPatientName").value;
	let patientPhone=document.getElementById("addPatientPhone").value;

	let patient= {
		id:0,
		name: patientName,
		phone: patientPhone
	};

	fetch ("https://informatica.iesquevedo.es/marcas/patients", {
		method: 'POST',
		headers: {
			'Content-Type' : 'application/json'
		},
		body: JSON.stringify(patient)
	}).then (response => {
		if(!response.ok) {
			throw new Error('Network response is not ok');
		}
		return response.json()
		}).catch (error => {
			console.error("Error in the fetch:", error)
		})
}

function showUpdatePatientModal(event) {
	let row = event.target.parentNode.parentNode;
	let cells = row.getElementByTagName("td");
	let id = cells[0].innerText;
	let name = cells[1].innerText;
	document.getElementById("updatepatientId").value=patientId;
	document.getElementById("updatepatentName").value=patientName;
	document.getElementById("updatePatientB").addEventListener("click", );
	let updatePatientModal = new bootstrap.Modal(document.getElementById)

}

function updatePatient() {
	let patientId = document.getElementById("updatepatientId").value;
	let patientName = document.getElementById("updatepatientName").value;

	let patient = {
		id: patientId,
		name: patientName,
		phone: patientName
	}
	fetch ("https://informatica.iesquevedo.es/marcas/patients", {
		method: 'PUT',
		headers: {
			'Content-Type' : 'application/json'
		},
		body: JSON.stringify(patient)
	})
	.then (response => {
		if(!response.ok) {
			throw new Error('Network response is not ok');
		}
		return response.json()
	}).then (data => {
		console.log('Patient updated succesfully' + data)
		$('#updatePatientModal').modal('hide');
		//modify data
		row.cells[0].innerText=patient.name;
		row.cells[2].innerText=patient.phone;
	})
	.catch (error => {
		console.error("Error in the fetch:", error)
	})
}
function deletePatient(event) {
	let row=event.target.parentNode.parentNode;
	let patientId=row.cells[0].innerText;

	fetch ("https://informatica.iesquevedo.es/marcas/patients/${patientId}?confirm=true", {
		method: 'DELETE',
		heraders: {
			'Content-Type' : 'application/json'
		}
	})
	.then (response => {
		if (!response.ok) {
			throw new Error ('Network response is not ok');
		}
		return response.json()
	})
	.then(data => {
		console.log("patient deleted succesfully: ", data);
		document.getElementById("patientTableBody").removeChild(row) //revisar
	})
	.catch (error => {
		console.error('Error updating patient: ', error);
	});
}