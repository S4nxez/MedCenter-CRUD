function getInfo(event){
	let row = event.target.closest('tr');
	if (row) {
	let patientId=row.cells[0].innerText;
	let patientName=row.cells[1].innerText;

	fetch (`http://localhost:8080/api/v1/MedRecord/${patientId}`)
	.then (response => {
		if (!response.ok)
			throw new Error ('Network response is not ok')
		return response.json()
	})
	.then (data => {
		let infoDiv=document.getElementById("infoContainer");
		infoDiv.innerHTML = '';


		let medRTable=document.createElement("table");
		medRTable.id="medRecordTable";
		medRTable.className="table table-bordered table-condensed table-striped";
		medRTable.innerHTML=`<thead><tr><th colspan=5>${patientName}'s MedRecords</th><td colspan=2>
		<button id="medRecAddB" class = 'btn btn-info btn-sm ms-2'>Add medRecord</button></td></tr>`
		//second row
		medRTable.innerHTML +="<tr><th>ID</th><th>Diagnosis</th><th>Date</th><th>Doctor</th><th>Medications</th><th style='text-align: center;'><img src='images/icons8-eliminar-64.png' alt='delete' style='width: 30px; height: 30px;'></th><th style='text-align: center;'><img src='images/icons8-actualizar-60.png' alt='delete' style='width: 30px; height: 30px;'></th></tr></thead></tbody>"
		let medRTableBody = medRTable.querySelector("tbody");
		medRTable.setAttribute("patientIdV", patientId);
		data.forEach(medRecord => {
			let medRecordRow = createMedRecordRow(medRecord);
			medRTableBody.appendChild(medRecordRow);
		})

		infoDiv.appendChild(medRTable);
		document.getElementById("medRecAddB").addEventListener("click", showAddMedRecordModal);
	})
	.catch(error => console.error("Error in the fetch: ", error))
}
}

function createMedRecordRow(medRecord) {
	let row = document.createElement("tr");
	row.innerHTML =	"<td>" + medRecord.id +"</td>" +
	"<td>" + medRecord.description +"</td>" +
	"<td>" + medRecord.date +"</td>" +
	"<td>" + medRecord.idDoctor +"</td>" +
	"<td>" + medRecord.medications +"</td>" +
	"<td><button class='btn btn-danger btn-sm'>Delete</button></td>" +
	"<td><button class='btn btn-primary btn-sm ms-2' >Update</button></td>";
	let deleteButton = row.querySelector(".btn-danger");
	deleteButton.addEventListener("click", function () { deleteMedRecord(medRecord, row) });
	let updateButton = row.querySelector(".btn-primary");
	updateButton.addEventListener("click", function(event) {showUpdateMedModal(event)});

	return row;
}

function showUpdateMedModal(event) {
	let row = event.target.parentNode.parentNode;

	let cells = row.getElementsByTagName("td");
	let id = cells[0].innerText;
	let diagnosis = cells[1].innerText;
	let date = cells[2].innerText;

	let patientId	= document.getElementById("medRecordTable").getAttribute("patientIdV");
	//Fill combo with doctors
	fillDoctors("updateMedRecordDoctor");
	//Fill list with medications
	fillMedications("updateMedRecordMeds");

	document.getElementById("updateMedRecordPatientId").value=patientId;
	document.getElementById("updateMedRecordId").value=id;
	document.getElementById("updateMedRecordDesc").value=diagnosis;
	document.getElementById("updateMedRecordDate").value=date;

	let updateButton = document.getElementById("updateMRB");
	updateButton.addEventListener("click", handleUpdateMedRec);

	let updateMedRecordModal = new bootstrap.Modal(document.getElementById('updateMedRecordModal'))
	updateMedRecordModal.show();

	function handleUpdateMedRec() {
		updateButton.removeEventListener("click", handleUpdateMedRec);
		updateMedRecord(row)
	}
}

function updateMedRecord(row) {
	let medRecordId	= document.getElementById("updateMedRecordId").value;
	let patientId	= document.getElementById("medRecordTable").getAttribute("patientIdV");
	let diagnosis	= document.getElementById("updateMedRecordDesc").value;
	let parsedDate	= new Date(document.getElementById("updateMedRecordDate").value);
	let idDoctor	= document.getElementById("updateMedRecordDoctor").value;
	let selectedMed	= getSelectMeds("updateMedRecordMeds");
	let formattedDate=parsedDate.toISOString().split('T')[0];

	let medRecord = {
		id: medRecordId,
		description: diagnosis,
		date: formattedDate,
		idPatient: patientId,
		idDoctor: Number(idDoctor),
		medications: selectedMed
	}

	fetch (`http://localhost:8080/api/v1/MedRecord/${medRecordId}`, {
		method: 'PUT',
		headers: {
			'Content-Type' : 'application/json'
		},
		body: JSON.stringify(medRecord)
	})
	.then (response => {
		if(!response.ok) {
			throw new Error('Network response is not ok');
		}
		return response.json()
	}).then (data => {
		console.log(medRecord)
		console.log('MedRecord updated succesfully ' + data)
		$('#updateMedRecordModal').modal('hide');
		//modify data
		row.cells[1].innerText=medRecord.description;
		row.cells[2].innerText=medRecord.date;
		row.cells[3].innerText=medRecord.idDoctor;
		row.cells[4].innerText=medRecord.medications;
	})
	.catch (error => {
		console.error("Error in the fetch:", error)
	})
}

function deleteMedRecord(medRecord, row) {
	fetch (`http://localhost:8080/api/v1/MedRecord/${medRecord.id}`, {
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
		console.log("MedRecord deleted succesfully: ", data);
		row.parentNode.removeChild(row);
	})
	.catch (error => {
		console.error('Error deleting MedRecord: ', error);
	});
}

function showAddMedRecordModal() {
	document.getElementById("addMRB").addEventListener("click", addMedRecord);
	//Fill combo with doctors
	fillDoctors("addMedRecordDoctor");
	//Fill list with medications
	fillMedications("addMedRecordMeds");
	let addMedRecordModal = new bootstrap.Modal(document.getElementById('addMedRecordModal'));

	addMedRecordModal.show();
}

function addMedRecord() {
	let diagnosis = document.getElementById("addMedRecordDesc").value;
	let parsedDate=new Date(document.getElementById("addMedRecordDate").value);
	let formattedDate=parsedDate.toISOString().split('T')[0]; //convert to java date
	let idDoctor= document.getElementById("addMedRecordDoctor").value;
	let selectedMed = getSelectMeds("addMedRecordMeds");
	let patientId	= document.getElementById("medRecordTable").getAttribute("patientIdV");

	let medRecord = {
		id: 0,
		description: diagnosis,
		date: formattedDate,
		idPatient: patientId,
		idDoctor: Number(idDoctor),
		medications: selectedMed
	};

	fetch ("http://localhost:8080/api/v1/MedRecord", {
		method: 'POST',
		headers: {
			'Content-Type' : 'application/json'
		},
		body: JSON.stringify(medRecord)
	}).then (response => {
		if(!response.ok) {
			throw new Error('Network response is not ok');
		}
		return response.json()
	}).then (data => {
		let table=document.getElementById("medRecordTable");
		let row=createMedRecordRow(data);
		let medRTableBody = table.querySelector("tbody");
		medRTableBody.appendChild(row);
		$('#addMedRecordModal').modal('hide')
	}).catch (error => {
		console.error("Error in the fetch:", error)
	})
}


function fillDoctors(elementId) {
	fetch ("https://informatica.iesquevedo.es/marcas/doctors")
	.then (response => {
		if (!response.ok) {
			throw new Error ('Network response is not ok');
		}
		return response.json()
	})
	.then(data => {
		let comboD=document.getElementById(elementId);
		comboD.innerHTML='';
		data.forEach(doctor => {
			let option=document.createElement("option");
			option.textContent=doctor.name;
			option.value=doctor.id;
			comboD.appendChild(option);
		})
	})
	.catch (error => {
		console.error('Error filling the doctors: ', error);
	});
}

function fillMedications(meds) {
	let comboM = document.getElementById(meds);
	comboM.innerHTML='';
	let option = document.createElement("option");
	option.textContent="Ibuprofen";
	option.value = 1;
	comboM.appendChild(option);
	option=document.createElement("option");
	option.textContent="Insulin";
	option.value=2;
	comboM.appendChild(option);
	option=document.createElement("option");
	option.textContent="Tylenol";
	option.value=3;
	comboM.appendChild(option);
	option=document.createElement("option");
	option.textContent="Amoxicilin";
	option.value=4;
	comboM.appendChild(option);
}

function getSelectMeds(medsSelect) { //I modified this method so that the selected meds is just a string joined by comas instead of an array of strings as it wasn´t a valid primitive type in java
	let medsList = document.getElementById(medsSelect);
	let meds = [];

	for (let i = 0; i < medsList.options.length; i++) {
		let option = medsList.options[i];
		if (option.selected) {
			meds.push(option.textContent);
		}
	}

	return meds.join(", ");
}