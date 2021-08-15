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
    if(newAmount == 0){
        document.getElementById("logo_container").click();
        return;
    }

    const amount = document.querySelector(".amountLable");
    amount.innerHTML = `Amount: ${newAmount}$`;
    document.querySelector(".amountValue").value = newAmount;
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
const flipCard = (user, suitName, value) => {
    const firstDealerCard = document.getElementById('flipped-card');
    const color = (suitName === "CLUBS" || suitName === "SPADES") ? "black" : "white";
    let suit = "♠";
    if (suitName === "CLUBS") suit = "♣";
    if (suitName === "HEARTS") suit = "♥";
    if (suitName === "DIAMONDS") suit = "♦";
    console.log(firstDealerCard);
    console.log("before");
    firstDealerCard.className = `card ${color}`;
    firstDealerCard.setAttribute("data-value",`${value}${suit}`);
    console.log(firstDealerCard);
    console.log("changed");
}
const addCard = (user, suitName, value) => {
    const userCards = document.querySelector(`.${user} .cards`);
    
    const color = (suitName === "CLUBS" || suitName === "SPADES") ? "black" : "white";
    
    let suit = "♠";
    if (suitName === "CLUBS") suit = "♣";
    if (suitName === "HEARTS") suit = "♥";
    if (suitName === "DIAMONDS") suit = "♦";
    if (suitName === "FLIPPED"){
        userCards.innerHTML += `
    	<div class="flipped-card" id="flipped-card">
    	</div>
	`;
    }else {
        userCards.innerHTML += `
    	<div class="card ${color}" data-value="${value}${suit}">
    		<div id="middle_suit">${suit}</div>
    	</div>
	`;
    }
}

const removeCards = (user) => {
    const userCards = document.querySelector(`.${user} .cards`);
    userCards.innerHTML = ``;
}

const removeEveryCard = () => {
    const cards = document.querySelectorAll(`.cards`);
    cards.forEach(userCards => userCards.innerHTML = ``);
}

let players = [];
let empty = null;
const addPlayer = (newUser) => {
    if (empty == null) fillPlayers();

    const thisUser = document.querySelector(".username").value;

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

    empty = [];
    emptyUsers.forEach((user) => {empty.push(user)});
}

const removePlayer = (user) => {
    const thisUser = document.querySelector(`.${user}`);
    players.splice(players.indexOf(thisUser), 1);
    thisUser.remove();

    sortPlayers();
}

const sortPlayers = () => {
    const placementIDs = ["upper_right", "lower_right", "lower_left", "upper_left"];
    const containerIDs = ["middle", "lower", "lower", "middle"];

    const users = document.querySelectorAll(".user");
	users.forEach((user) => {user.parentNode.removeChild(user)});
    
    for (let i = 0; i < empty.length; i++) {
        const container = document.querySelector(`#${containerIDs[i]}_grid`);
        if (i < players.length) {
            players[i].id = placementIDs[i];
            container.appendChild(players[i]);
        } else {
            empty[i].id = placementIDs[i];
            container.appendChild(empty[i]);
        }
    }
}

const enterBet = (onClickFunc) => {
    const betWindow = document.querySelector(".enterBet");
    const betButton = document.querySelector("#enter_bet_button");

    const user =  document.querySelector(".username").value;
    const userBet = document.querySelector(`.${user} .bet`);
	userBet.innerHTML = `bet: 0`;

    betWindow.hidden = false;
    betWindow.style = "";
    betButton.onclick = () => {if (setBet(onClickFunc)) closeBet();};
}

const checkBet = (bet) => {
    const amount = parseInt(document.querySelector(".amountValue").value);

    return bet >= 0 && bet <= amount;
}

const closeBet = () => {
    const betWindow = document.querySelector(".enterBet");
    betWindow.hidden = true;
    betWindow.style = "display: none;";
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
        displayMessage("Enter a valid bet");
        return false;
    }
}

const getBet = (user) => {
    const userBet = document.querySelector(`.${user} .bet`);

    return parseInt(userBet.value);
}

const displayMessage = (message) => {
    const messageWindow = document.querySelector(".displayMessage");

    if(message === "playerLost"){
        message = "You Lost";
    }else if(message === "playerWon"){
        message = "You Won";
    }else if(message === "playerPush"){
        message = "Push";
    }
    messageWindow.innerHTML = message;

    setTimeout(removeBlackjackMessage, 3000);
}

const removeBlackjackMessage = () => {
    const messageWindow = document.querySelector(".displayMessage");

    messageWindow.innerHTML = "";
}
