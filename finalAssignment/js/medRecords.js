function getInfo(event){
	let row = event.target.parentNode.parentNode;
	let patientId=row.cells[0].innerText;
	let patientName=row.cells[1].innerText;

	fetch (`https://informatica.iesquevedo.es/marcas/patients/${patientId}/medRecords`)
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
		medRTable.innerHTML +="<tr><th>ID</th><th>Diagnosis</th><th>Date</th><th>Doctor</th><th>Medications</th><th>Action</th><th>Action</th></tr></thead></tbody>"
		let medRTableBody = medRTable.querySelector("tbody");
		data.forEach(medRecord => {
			let medRecordRow = createMedRecordRow(medRecord);
			medRTableBody.appendChild(medRecordRow);
		})

		infoDiv.appendChild(medRTable);
		document.getElementById("medRecAddB").addEventListener("click", showAddMedRecordModal);
	})
	.catch(error => console.error("Error in the fetch: ", error))
}

function createMedRecordRow(medRecord) {
	let row = document.createElement("tr");
	row.innerHTML =	"<td>" + medRecord.id +"</td>" +
	"<td>" + medRecord.description +"</td>" +
	"<td>" + medRecord.date +"</td>" +
	"<td>" + medRecord.idDoctor +"</td>" +
	"<td>" + medRecord.medications +"</td>" +
	"<td><button class='btn btn-danger btn-sm' >Delete</button></td>" +
	"<td><button class='btn btn-primary btn-sm ms-2' >Update</button></td>";
	return row;
}

function showAddMedRecordModal() {
	document.getElementById("addMRB").addEventListener("click", addMedRecord);
	//Fill combo with doctors
	fillDoctors();
	//Fill list with medications
	fillMedications();
	let addMedRecordModal = new bootstrap.Modal(document.getElementById('addMedRecordModal'));

	addMedRecordModal.show();
}

function addMedRecord() {
	let diagnosis = document.getElementById("addMedRecordDesc").value;
	let parsedDate=new Date(document.getElementById("addMedRecordDate").value);
	let formattedDate=parsedDate.toISOString().split('T')[0]; //convert to java date
	let idDoctor= document.getElementById("addMedRecordDoctor").value;
	let selectedMed = getSelectMeds("addMedRecordMeds");

	let medRecord = {
		id: 0,
		description: diagnosis,
		date: formattedDate,
		idDoctor: Number(idDoctor),
		medications: selectedMed
	};

	fetch ("https://informatica.iesquevedo.es/marcas/patients/medRecords", {
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
		let tableBody=document.getElementById("medRecordTable");
		let row=createMedRecordRow(data);
		tableBody.appendChild(row);
		$('#addMedRecordModal').modal('hide')
	}).catch (error => {
		console.error("Error in the fetch:", error)
	})
}


function fillDoctors() {
	fetch ("https://informatica.iesquevedo.es/marcas/doctors")
	.then (response => {
		if (!response.ok) {
			throw new Error ('Network response is not ok');
		}
		return response.json()
	})
	.then(data => {
		let comboD=document.getElementById("addMedRecordDoctor");
		comboD.innerHTML='';
		data.forEach(doctor => {
			let option=document.createElement("option");
			option.textContent=doctor.name;
			option.value=doctor.id;
			comboD.appendChild(option);
		})
	})
	.catch (error => {
		console.error('Error updating patient: ', error);
	});
}

function fillMedications() {
	let comboM = document.getElementById("addMedRecordMeds");
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

function getSelectMeds(medsSelect) {
	let medsList=document.getElementById("addMedRecordMeds");
	let meds=[];
	for(let i = 0; i < medsList.options.length; i++) {
		let option=medsList.options[i];
		if (option.selected)
			meds.push(option.textContent);
	}
	return meds;
}