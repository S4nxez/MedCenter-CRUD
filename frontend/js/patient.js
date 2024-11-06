document.getElementById("add1").addEventListener("click", showAddPatientModal);
getAllPatients()

function getAllPatients() {
	fetch ("http://localhost:8080/patients")
		.then (response => {
			if (!response.ok)
				throw new Error ('Network response is not ok')
			return response.json()
		})
		.then (data => {
			var tableBody = document.querySelector("#patientTable tbody");
			tableBody.innerHTML = "";
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
		"<td>" + patient.dob +"</td>" +
		"<td>" + patient.phone +"</td>" +
		"<td>" + patient.paid +"</td>" +
		"<td><button class='btn btn-danger btn-sm' >Delete</button></td>" +
		"<td><button class='btn btn-primary btn-sm ms-2' >Update</button></td>";

		row.addEventListener("click", getInfo);
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
		name: patientName,
		phone: patientPhone
	};

	fetch ("http://localhost:8080/api/v1/Patient", {
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
	fetch (`http://localhost:8080/api/v1/Patient/${patientId}`, {
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
		console.log("Patient updated succesfully ", data)
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

	sendDeleteRequest(false);

    // Función para enviar la solicitud DELETE al servidor
    function sendDeleteRequest(confirmation) {
        // Delete the patient from the server
        fetch(`https://informatica.iesquevedo.es/marcas/patients/${patientId}?confirm=${confirmation}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (response.status === 204) {
                    // No content, successful deletion
                    console.log('Patient deleted successfully');
                    row.parentNode.removeChild(row);
                    document.getElementById("infoContainer").innerHTML = "";
                    return;
                }

                if (!response.ok) {
                    // Si la respuesta no es ok, manejar el error
                    if (response.status === 409) {
                        // Conflicto: el paciente tiene registros médicos
                        return response.text().then(eMessage => {
                            // Mostrar el mensaje de error al usuario y confirmar
                            let resp = confirm(eMessage);
                            if (resp) {
                                // Volvemos a llamar al delete pero con la confirmación
                                sendDeleteRequest(true);
                                throw new Error('Patient has medical records');
                            } else {
                                // El usuario canceló, lanzar un error
                                throw new Error('User cancelled operation');
                            }
                        });
                    } else {
                        // Otro tipo de error
                        throw new Error('Network response was not ok');
                    }
                }
                // Si la respuesta es ok, devolver el cuerpo de la respuesta como JSON
                return response.json();
            })
            .catch(error => {
                console.error('Error deleting patient:', error);
            });
    }
}