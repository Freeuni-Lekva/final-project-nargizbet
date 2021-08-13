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

    hitButton.onclick = onClickHit;
    standButton.onclick = onClickStand;

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
    userCards.innerHTML += `<div class="card ${suit} ${value}"></div>`;
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
    const emptyUser = document.querySelector(`.emptyUser`);
    emptyUser.remove();

    const users = document.querySelector(`.users`);
    users.innerHTML += `
    <div class="user ${newUser}">
        <div class="cards"></div>
        <p class="username">${newUser}</p>
        <p class="bet">0</p>
    </div>`
}

const removePlayer = (user) => {
    const thisUser = document.querySelector(`.${user}`);
    thisUser.remove();

    
    const users = document.querySelector(".users");
    users.innerHTML += `<div class="user emptyUser"></div>`;
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
    
    userBet.innerHTML = bet;
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