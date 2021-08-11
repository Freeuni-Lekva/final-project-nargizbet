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