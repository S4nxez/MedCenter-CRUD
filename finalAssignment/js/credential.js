document.getElementById("login").addEventListener("click", credentials);

function credentials (){

	let cred = {
		username: document.getElementById("username").value,
		password: document.getElementById("pwd").value
		};

		fetch("https://informatica.iesquevedo.es/marcas/login", {
		method: 'POST',
		headers: {
		'Content-Type': 'application/json'
		},
		body: JSON.stringify(cred)
	})
		.then (response => {
			if (!response.ok) {
				throw new Error ('Network response is not ok');
			}
			return response. json()
		})
	.then (data => {
		if (data == true) {
			window.location.href = "main.html"
		} else {
			console.error("wrong credentials")
		}
	})
	.catch(error =>	console.error ("Error in the fetch:", error))
}
