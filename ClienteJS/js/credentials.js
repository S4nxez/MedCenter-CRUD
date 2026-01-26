document.getElementById("bCheck").addEventListener("click", function (event) {
    event.preventDefault();

    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;

    getCredentials(username, password)
})

function getCredentials(u, p) {
  let cred = {
    username: u,
    password: p,
  };

  fetch("/api/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(cred),
  })
    .then((response) => {
      if (response.ok) {
        return response.json();
      } else {
        if (response.status === 401) {
          document.getElementById("WrongCred").textContent = "Invalid credentials";
          throw new Error("Invalid credentials");
        }
        throw new Error("Network response was not ok.");
      }
    })
    .then((data) => {
      if (data && data.token && data.refreshToken) {
        authService.saveTokens(data.token, data.refreshToken);

        document.getElementById("WrongCred").textContent = "";
        window.location.href = "mainPage.html";
      } else {
        document.getElementById("WrongCred").textContent = "Login failed";
      }
    })
    .catch((error) => {
      console.error("Error al realizar la solicitud:", error);
      if (!document.getElementById("WrongCred").textContent) {
        document.getElementById("WrongCred").textContent = "Login error. Please try again.";
      }
    });
}
