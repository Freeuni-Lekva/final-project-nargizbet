// front end script, not for websockets
const drawBlank = () => {
    let maxPlayers = parseInt(document.querySelector(".maxPlayers").value);
    const users = document.querySelector(".users");

    for (let i = 0; i < maxPlayers; i++) {
        users.innerHTML += `<div class="user emptyUser"></div>`;
    }
}

// for chat client
const addMessage = (user, message) => {
    const maxMessages = 50;
    console.log(maxMessages);

    const messageWindow = document.querySelector(".messageWindow");
    const messageCount = messageWindow.childElementCount;

    if (messageCount >= maxMessages) {
        const message = messageWindow.querySelector(".message");
        message.remove();
    }

    messageWindow.innerHTML += `<p class="message">${user}: ${message}</p>`;
}

// for blackjack client
const drawActionButtons = (onClickHit, onClickStand) => {
    const hitButton = document.querySelector(".hitBtn");
    const standButton = document.querySelector(".standBtn");

    hitButton.onclick = () => {onClickHit(); removeActionButtons();};
    standButton.onclick = () => {onClickStand(); removeActionButtons();};

    hitButton.hidden = false;
    standButton.hidden = false;
}

const removeActionButtons = () => {
    const hitButton = document.querySelector(".hitBtn");
    const standButton = document.querySelector(".standBtn");

    hitButton.hidden = true;
    standButton.hidden = true;
}

const addCard = (user, suit, value) => {
    const userCards = document.querySelector(`.${user} .cards`);
    
    const color = (suit === "CLUBS" || suit === "SPADES") ? "black" : "white";
    
    userCards.innerHTML += `
    	<div class="card ${color}" data-value="${value}${suit}">
    		<div id="middle_suit">${suit}</div>
    	</div>
	`;
}

const removeCards = (user) => {
    const userCards = document.querySelector(`.${user} .cards`);
    userCards.innerHTML = ``;
}

const removeEveryCard = () => {
    const cards = document.querySelectorAll(`.cards`);
    cards.forEach(userCards => userCards.innerHTML = ``);
}

const addPlayer = (newUser) => {
    const placementIDs = ["upper_right", "lower_right", "lower_left", "upper_left"];
    const containerID = [0, 1, 1, 0]; // users0 or users1

    const emptyUser = document.querySelector(`.emptyUser`);
    emptyUser.remove();

    const newUserElem = document.createElement('div');
    newUserElem.classList.add("user");
    newUserElem.classList.add(`${newUser}`);
    newUserElem.innerHTML = `
        <div class="cards"></div>
        <p class="username">${newUser}</p>
        <p class="bet">bet: 0</p>
    `;

    const oldUsers = document.querySelectorAll(".user");
    const users = [];
    oldUsers.forEach((user) => {users.push(user);});
    users.push(newUserElem);

    document.querySelector(".users0").innerHTML = ``;
    document.querySelector(".users1").innerHTML = ``;
    for (let i = 0; i < users.length; i++) {
        const container = document.querySelector(`.users${containerID[i]}`);
        users[i].id = placementIDs[i];
        container.appendChild(users[i]);
    }
}

const removePlayer = (user) => {
    const thisUser = document.querySelector(`.${user}`);
    thisUser.remove();

    const users = document.querySelector(".users");
    users.innerHTML += `
    <div class="user emptyUser">
        <div class="cards"></div>
    </div>`;
}

const enterBet = (onClickFunc) => {
    const betWindow = document.querySelector(".enterBet");
    const betButton = document.querySelector("#enter_bet_button");

    betWindow.hidden = false;
    betButton.onclick = () => {setBet(onClickFunc); closeBet();};
}

const closeBet = (user) => {
    const betWindow = document.querySelector(".enterBet");
    betWindow.hidden = true;
}

const setBet = (onClickFunc) => {
    const user =  document.querySelector(".username").value;
    const bet = document.getElementById("bet").value;

    const userBet = document.querySelector(`.${user} .bet`);
    console.log(user);

    userBet.innerHTML = `bet: ${bet}`;
    onClickFunc(bet);
}

const getBet = (user) => {
    const userBet = document.querySelector(`.${user} .bet`);

    return parseInt(userBet.value);
}

const displayMessage = (message) => {
    const messageWindow = document.querySelector(".message");

    messageWindow.innerHTML = message;

    setTimeout(removeBlackjackMessage, 3000);
}

const removeBlackjackMessage = () => {
    const messageWindow = document.querySelector(".message");

    messageWindow.innerHTML = "";
}