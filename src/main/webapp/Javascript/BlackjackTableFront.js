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
const changeAmount = (newAmount) => {
    const amount = document.querySelector(".amountLable");
    amount.innerHTML = `Amount: ${newAmount}$`;
}

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

const addCard = (user, suitName, value) => {
    const userCards = document.querySelector(`.${user} .cards`);
    
    const color = (suitName === "CLUBS" || suitName === "SPADES") ? "black" : "white";
    
    let suit = "♠";
    if (suitName === "CLUBS") suit = "♣";
    if (suitName === "HEARTS") suit = "♥";
    if (suitName === "DIAMONDS") suit = "♦";
    
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

let players = null;
const addPlayer = (newUser) => {
    if (players == null) fillPlayers();

    const thisUser = document.querySelector(".username").value;

    const emptyUser = document.querySelector(`.emptyUser`);
    players.splice(players.indexOf(emptyUser), 1);
    emptyUser.remove();

    const newUserElem = document.createElement('div');
    newUserElem.classList.add("user");
    newUserElem.classList.add(`${newUser}`);
    newUserElem.innerHTML = `
        <div class="cards"></div>
        <p class="username">${newUser}</p>
    `;
    if (newUser == thisUser)
        newUserElem.innerHTML += `<p class="bet">bet: 0</p>`;

    players.push(newUserElem);
    sortPlayers();
}

const fillPlayers = () => {
    const emptyUsers = document.querySelectorAll(".user");

    players = [];
    emptyUsers.forEach((user) => {players.push(user)});
}

const removePlayer = (user) => {
    const thisUser = document.querySelector(`.${user}`);
    players.splice(players.indexOf(thisUser), 1);
    thisUser.remove();

    const users = document.querySelector("#lower_grid");

    const newEmptyUser = document.createElement('div');
    newEmptyUser.classList.add("user");
    newEmptyUser.classList.add(`emptyUser`);
    newEmptyUser.innerHTML = `<div class="cards"></div>`;

    players.push(newEmptyUser);
    sortPlayers();
}

const sortPlayers = () => {
    const placementIDs = ["upper_right", "lower_right", "lower_left", "upperleft"];
    const containerIDs = ["middle", "lower", "lower", "middle"];

    const users = document.querySelectorAll(".user");
	users.forEach((user) => {user.parentNode.removeChild(user)});
    
    for (let i = 0; i < players.length; i++) {
        const container = document.querySelector(`#${containerIDs[i]}_grid`);
        players[i].id = placementIDs[i];
        container.appendChild(players[i]);
    }
}

const enterBet = (onClickFunc) => {
    const betWindow = document.querySelector(".enterBet");
    const betButton = document.querySelector("#enter_bet_button");

    const user =  document.querySelector(".username").value;
    const userBet = document.querySelector(`.${user} .bet`);
	userBet.innerHTML = `bet: 0`;

    betWindow.hidden = false;
    betButton.onclick = () => {if (setBet(onClickFunc)) closeBet();};
}

const checkBet = (bet) => {
    const amount = parseInt(document.querySelector(".amountValue").value);

    return bet <= amount;
}

const closeBet = () => {
    const betWindow = document.querySelector(".enterBet");
    betWindow.hidden = true;
}

const setBet = (onClickFunc) => {
    const user =  document.querySelector(".username").value;
    const bet = document.getElementById("bet").value;

    const userBet = document.querySelector(`.${user} .bet`);

    if (checkBet(bet)) { 
    	userBet.innerHTML = `bet: ${bet}`;
        onClickFunc(bet);
        return true;
    } else {
        displayMessage("You do not have enough money for the bet");
        return false;
    }
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