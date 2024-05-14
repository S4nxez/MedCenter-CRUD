//Listeners
document.getElementById("createbutton").addEventListener("click", startGame);

var score = 0;
var currentSequence = [];
var guessedElements = 0;
var intervalId;

function startGame() {
  document.getElementById("rows").disabled = true;
  document.getElementById("cols").disabled = true;
  document.getElementById("level").disabled = true;
  document.getElementById("createbutton").disabled = true;

  document.getElementById("StatusMessage").innerHTML = "Playing...";

  drawField(document.getElementById("cols").value, document.getElementById("rows").value);

  var sequence = generateSequence(document.getElementById("cols").value, document.getElementById("rows").value, document.getElementById("level").value);

  var level = document.getElementById("level").value;
  var delay = 1000 - level * 100;

  setTimeout(function () {
    playSequence(sequence, level);
    enableEventsField();
  }, delay);
}

function playGame() {
  disableEventsField();
  showElement(currentSequence[guessedElements]);
  guessedElements++;
  if (guessedElements === currentSequence.length) {
    guessedElements = 0;
    score++;
    document.getElementById("score").innerHTML = "Score: " + score;
    var level = document.getElementById("level").value;
    var delay = 1000 - level * 100;
    setTimeout(function () {
      currentSequence.push(Math.floor(Math.random() * document.getElementsByClassName("buttonGrid").length));
      playSequence(currentSequence, level);
      enableEventsField();
    }, delay);
  } else {
    enableEventsField();
  }
}

function drawField(width, height) {
  var field = document.getElementById("field");
  var colors = ["#7FFF00", "#9ACD32", "#BDB76B", "#D8BFD8", "#ADD8E6", "#8FBC8F", "#6B8E23", "#483D8B", "#191970", "#00BFFF", "#00CED1", "#20B2AA", "#32CD32", "#3CB371", "#2E8B57", "#228B22", "#008080", "#006400", "#008000", "#66CDAA", "#8B008B", "#00FA9A", "#00FF7F", "#7FFFD4"];
  field.innerHTML = "";
  var k = 0;
  for (var i = 0; i < height; i++) {
    for (var j = 0; j < width; j++, k++) {
      var button = document.createElement("button");
      button.setAttribute("id", j + "_" + i);
      button.setAttribute("class", "buttonGrid");
      button.setAttribute("style", "width:50px; height:50px; background-color:" + colors[k] + ";");
      field.appendChild(button);
    }
    var breakElement = document.createElement("br");
    field.appendChild(breakElement);
  }
}

function enableEventsField() {
  var buttons = document.getElementsByClassName("buttonGrid");
  for (var i = 0; i < buttons.length; i++) {
    buttons[i].addEventListener("click", check);
  }
}

function disableEventsField() {
  var buttons = document.getElementsByClassName("buttonGrid");
  for (var i = 0; i < buttons.length; i++) {
    buttons[i].removeEventListener("click", check);
  }
}

function generateSequence(width, height, level) {
  var numElements = 5 * level;
  var sequence = [];
  for (var i = 0; i < numElements; i++) {
    var x = Math.floor(Math.random() * width);
    var y = Math.floor(Math.random() * height);
    sequence.push([x, y]);
  }
  return sequence;
}

function playSequence(sequence, level) {
  disableEventsField();
  var subSequence = sequence.slice(0, level + 1);
  var i = 0;
  intervalId = setInterval(function () {
    showElement(subSequence[i]);
    i++;
    if (i >= subSequence.length) {
      clearInterval(intervalId);
      enableEventsField();
    }
  }, 1000 - level * 100);
}


function showElement(element) {
  var originalColor = document.getElementById(element).style.backgroundColor;
  document.getElementById(element).style.backgroundColor = "red";

  setTimeout(function () {
    document.getElementById(element).style.backgroundColor = originalColor;
    if (guessedElements === currentSequence.length) {
      clearInterval(intervalId);
      enableEventsField();
    }
  }, 1000);

  disableEventsField();
}



function check(event) {
  disableEventsField();

  var selectedNumber = event.target.textContent;
  var correctNumber = currentSequence[guessedElements];

  if (selectedNumber == correctNumber) {
    guessedElements++;

    if (guessedElements === currentSequence.length) {
      guessedElements = 0;
      score++;
      document.getElementById("score").innerHTML = "Score: " + score;
      var level = document.getElementById("level").value;
      var delay = 1000 - level * 100;

      setTimeout(function () {
        currentSequence.push(Math.floor(Math.random() * document.getElementsByClassName("buttonGrid").length));
        playSequence(currentSequence, level);
        enableEventsField();
      }, delay);
    } else {
      enableEventsField();
    }
  } else {
    document.getElementById("StatusMessage").innerHTML = "Wrong button! Try again.";
    guessedElements = 0;
    score = 0;
    document.getElementById("score").innerHTML = "Score: " + score;
    currentSequence = [];
    disableEventsField();
    setTimeout(function () {
      playSequence(generateSequence(document.getElementById("cols").value, document.getElementById("rows").value, document.getElementById("level").value), document.getElementById("level").value);
      enableEventsField();
    }, 3000);
  }
}


function getCurrentSequence() {
  return currentSequence;
}

function getGuessedElements() {
  return guessedElements;
}

function setGuessedElements(value) {
  guessedElements = value;
}

function incrementScore() {
  if (typeof score === "undefined") {
    score = 0;
  }
  score++;
  document.getElementById("score").innerHTML = "Score: " + score;
}

function getScore() {
  return score;
}

function stopChrono() {
  clearInterval(intervalId);
}

function showMessage(message) {
  var statusElement = document.getElementById('StatusMessage');
  statusElement.innerHTML = message;
}