document.getElementById("add1").addEventListener("click", showAddPatientModal);
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
		.catch(error => console.error("Error in the fetch: ", error))
}

function createPatientRow(patient) {
	var row = document.createElement("tr");
	row.innerHTML =	"<td>" + patient.id +"</td>" +
		"<td>" + patient.name +"</td>" +
		"<td>" + patient.phone +"</td>" +
		"<td><button class='btn btn-info btn-sm' >Get info</button></td>" +
		"<td><button class='btn btn-danger btn-sm' >Delete</button></td>" +
		"<td><button class='btn btn-primary btn-sm ms-2' >Update</button></td>";

		row.querySelector(".btn-info").addEventListener("click", getInfo);
		row.querySelector(".btn-primary").addEventListener("click", showUpdatePatientModal);
		row.querySelector(".btn-danger").addEventListener("click", deletePatient);
		return row;
}

function showAddPatientModal() {
	document.getElementById("addPatientButton").addEventListener("click", addPatient);
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
		}).then (data => {
			let tableBody=document.getElementById("patientTableBody");
			let row=createPatientRow(data);
			tableBody.appendChild(row);
			$('#addPatientModal').modal('hide')
			document.getElementById("addPatientName").value = "";
			document.getElementById("addPatientPhone").value = "";
		}).catch (error => {
			console.error("Error in the fetch:", error)
		})
}

function showUpdatePatientModal(event) {
	let row = event.target.parentNode.parentNode;

	let cells = row.getElementsByTagName("td");
	let id = cells[0].innerText;
	let name = cells[1].innerText;
	let phone = cells[2].innerText;

	document.getElementById("updatePatientId").value=id;
	document.getElementById("updatePatientName").value=name;
	document.getElementById("updatePatientPhone").value=phone;
	let updateButton = document.getElementById("updatePatientButton");
	updateButton.addEventListener("click", handleUpdatePatient);

	let updatePatientModal = new bootstrap.Modal(document.getElementById('updatePatientModal'))
	updatePatientModal.show();

	function handleUpdatePatient() {
		updateButton.removeEventListener("click", handleUpdatePatient);
		updatePatient(row);
	}
}

function updatePatient(row) {
	let patientId = document.getElementById("updatePatientId").value;
	let patientName = document.getElementById("updatePatientName").value;
	let patientPhone = document.getElementById("updatePatientPhone").value;

	let patient = {
		id: patientId,
		name: patientName,
		phone: patientPhone
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
		console.log('Patient updated succesfully ' + data)
		$('#updatePatientModal').modal('hide');
		//modify data
		row.cells[1].innerText=patient.name;
		row.cells[2].innerText=patient.phone;
	})
	.catch (error => {
		console.error("Error in the fetch:", error)
	})
}

function deletePatient(event) {
	let row=event.target.parentNode.parentNode;
	let patientId=row.cells[0].innerText;

	fetch (`https://informatica.iesquevedo.es/marcas/patients/${patientId}?confirm=true`, {
		method: 'DELETE',
		headers: {
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
		document.getElementById("patientTableBody").removeChild(row);
	})
	.catch (error => {
		console.error('Error deleting patient: ', error);
	});
}