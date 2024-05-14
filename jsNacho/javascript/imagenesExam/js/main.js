const start = document.getElementById('start');
const stop = document.getElementById('stop');
stop.style.display = 'none';
const message = document.getElementById('textmessages');
let numerofinal;
let clickedStop = false;
let wantedimage = document.createElement('img');
let imageshowed;
wantedimage.setAttribute('id', 'wantedimage');
start.addEventListener('click', startGame);
stop.addEventListener('click', stopPhoto);

function startGame() {
    start.style.display = 'none';
    stop.style.display = 'block';
    createNewBoard();
    createImages();
}

function createImages() {
    clickedStop = false
    let lastimageshowed = -1;

    for (let i = 0; i < 5; i++) {
        let img = document.createElement('img');
        img.setAttribute('src', 'images/' + (i + 1) + '.png');
        img.setAttribute('id', i);
        document.getElementById('board').appendChild(img);
    }

    createWantedImage();
    showSequece();

    function showSequece() {
        if (!clickedStop) {
            imageshowed = Math.floor((Math.random() * 5) + 1);
            if (imageshowed !== lastimageshowed) {
                lastimageshowed = imageshowed;
                document.getElementById(imageshowed-1).style.display = 'block';
                setTimeout(function () {
                    document.getElementById(imageshowed-1).style.display = 'none';
                }, 500);
            }
            else {
                while (imageshowed === lastimageshowed) {
                    imageshowed = Math.floor((Math.random() * 5) + 1);
                }
                lastimageshowed = imageshowed;
                document.getElementById(imageshowed-1).style.display = 'block';
                setTimeout(function () {
                    document.getElementById(imageshowed-1).style.display = 'none';
                }, 500);
            }
            setTimeout(showSequece, 500);
        }
    }
}

function createWantedImage() {
    numerofinal = (Math.floor(Math.random() * 5) + 1);
    message.innerText = "Wanted image:";
    wantedimage.setAttribute('src', 'images/' + numerofinal + '.png');
    document.getElementById('chats').appendChild(wantedimage);
}


function stopPhoto() {
    clickedStop = true;
    stopGame();
    if (imageshowed === numerofinal) {
        message.innerText = 'You won!';
        wantedimage.classList.add('loca');
    } else {
        message.innerText = 'You Lost...';
    }
}

function stopGame() {
    start.style.display = 'block';
    stop.style.display = 'none';
    start.innerHTML = "restart";
}

function createNewBoard() {
    let oldBoard = document.getElementById('board');
    oldBoard.remove();

    let board = document.createElement('div');
    board.setAttribute('id', 'board');

    document.body.appendChild(board);
    wantedimage.removeAttribute('class');
    wantedimage.remove();
}
