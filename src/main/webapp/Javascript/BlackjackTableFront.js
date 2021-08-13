// front end script, not for websockets
const drawBlank = () => {
    const maxPlayers = parseInt(document.querySelector(".maxPlayers").value)
    const users = document.querySelector(".users");

    for (let i = 0; i < maxPlayers; i++) {
        users.innerHTML += `<div class="user emptyUser"></div>`;
    }
} 

// for chat client
const addMessage = (user, message) => {
    const maxMessages = 10;

    const messageWindow = document.querySelector(".messageWindow");
    const messageCount = messageWindow.childElementCount;

    if (messageCount >= maxMessages) {
        const message = messageWindow.querySelector(".message");
        message.remove();
    }

    messageWindow.innerHTML += `<p class="message">${user}: ${message}</p>`;
}

// for blackjack client
const setAmount = (amount) => {
    const amountWindow = document.querySelector(".amountLable");

    amountWindow.innerHTML += amount;
}

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

const setBet = (user, bet) => {
    const userBet = document.querySelector(`.${user} .bet`);

    userBet.innerHTML = bet;
}
const drawBetButton = (onBet,username) => {
    const betButton = document.querySelector(".amount-button");
    const betField = document.querySelector(".amount-field")
    betButton.onclick = onBet(username,betField.value);

    betButton.hidden = true;
    betField.hidden = true;
}
const removeBetButton = () => {
    const betButton = document.querySelector(".amount-button");
    const betField = document.querySelector(".amount-field")

    betButton.hidden = false;
    betField.hiddrn = false;
}

