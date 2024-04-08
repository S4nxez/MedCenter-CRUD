const actionTypes = ['getinfo', 'delete', 'update', 'add'];

actionTypes.forEach(action => {
	for (let i = 1; i <= 4; i++) {
		const buttonId = `${action}${i}`;
		document.getElementById(buttonId).addEventListener('click', () => showAlert(action, i));
	}
});

function showAlert(action, index) {
	alert(`The ${action} button ${index} has been clicked`);
}
