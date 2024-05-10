function fillDoctors() {
	fetch ("https://informatica.iesquevedo.es/marcas/doctors")
	.then (response => {
		if (!response.ok) {
			throw new Error ('Network response is not ok');
		}
		return response.json()
	})
	.then(data => {
		console.log(data);
		let comboD=document.getElementById("addMedRecordDoctor");
		comboD.innerHTML='';
		data.foreEach(doctor => {
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

function getSelectMeds(medsSelect) {

	let medsList=document.getElementById("addMedRecordMeds");
	let meds=[];
	for(let i = 0; i < medsList.options.length; i++) {
		let option=medsList.option[i];
		if (option.selected)
			meds.push(option.textContent);
	}
	return meds;
}