var ws;

function connectTable(id, amount){
    connectBlackjack(id, amount);
    connect(id);
}

function closeGame(){
    ws.close();
    stopTimer();
}

function leaveTable(){
    closeGame();
    closeChat();
}

function connectBlackjack(id, amount){
    console.log(id);
    ws = new WebSocket("ws://localhost:8080/game/blackjack/" +  id + "/" + amount);
    ws.onmessage = onMessageBlackjack;
    console.log("Connected");
}

function flipDealerCard(eventJson) {
    console.log(eventJson);
    let card = eventJson.card;
    console.log("came here at least");
    flipCard("dealer", card.suit, card['value']);
}

function onMessageBlackjack(event){
    let eventJson = JSON.parse(event.data);
    let actionType = eventJson['type'];
    console.log(actionType);
    console.log(eventJson);
    if(actionType == "AddCardAction") {
        addPlayerCard(eventJson);
    }
    if(actionType == "AddPlayerAction"){
        addPlayerJS(eventJson);
    }
    if(actionType == "AskBetAction"){
        askBet();
    }
    if(actionType == "AskMoveAction") {
        askMove();
    }
    if(actionType == "BustedAction"){
        console.log("Bust Before");
        displayMessage("Bust");
    }
    if(actionType == "ClearAction"){
        resetTable();
    }
    if(actionType == "DrawTableAction"){
        drawTable(eventJson);
    }
    if(actionType == "NextPlayerAction"){
        //nextPlayer(eventJson.username);
    }
    if(actionType == "RemovePlayerAction"){
        removePlayerJS(eventJson);
    }
    if(actionType == "ResultAction"){
        displayResult(eventJson);
    }
    if(actionType == "FlipCardAction"){
        console.log("was here");
        flipDealerCard(eventJson);
    }
    if(actionType == "BlackjackAction"){
        displayMessage("BLACKJACK");
    }

}


function displayResult(msg){
    displayMessage(msg.result);
    setTimeout(function (){ changeAmount(msg.amount)}, 2000);
}

function resetTable(){
    removeEveryCard();
}

function drawTable(event){
    let dealerCards = event.dealer.currentCards;
    let arrLength = dealerCards.length;
    for(let i = 0; i < arrLength; i++){
        if(i===0) {
            addCard("dealer", "FLIPPED", "");
        } else addCard("dealer", dealerCards[i].suit, (dealerCards[i])['value']);
    }

    let playerList = event.players;
    console.log(event);
    console.log(playerList);
    let playersSize = event.players.length;
    for(let i = 0; i < playersSize; i++){
        addPlayer((playerList[i].user)['username']);
        console.log(playerList[i]['username']);
        let cardsList = playerList[i].currentCards;
        let cardLength = cardsList.length;
        for(let j = 0; j < cardLength; j++){
            addCard((playerList[i].user)['username'], cardsList[j].suit, (cardsList[j])['value']);
        }
    }
}

function addPlayerCard(event){
    let user = event['username'];
    let cardList = event['cards'];
    let cardListLength = cardList.length;
    let numInHand = event['numCardsInHand'];
    console.log(numInHand);
    for(let i = 0; i < cardListLength; i++){
        let card = cardList[i];
        if(user==="dealer" && numInHand <3 && i===0){
            addCard(user, "FLIPPED", "");
        }else {
            addCard(user, card.suit, card['value']);
        }
    }
}

function addPlayerJS(msg){
    addPlayer(msg['username']);
}

function removePlayerJS(msg){
    removePlayer(msg['username']);
}

function hitMessage(){
    stopTimer();
    sendMoveMessage(JSON.stringify({
        "type" : "move",
        "move" : "hit"
    }))
}

function standMessage(){
    stopTimer();
    sendMoveMessage(JSON.stringify({
        "type" : "move",
        "move" : "stand"
    }))
}

function askMove(){
    startTimer(20, standMessage);
    drawActionButtons(hitMessage, standMessage);
}

function sendMoveMessage(msg){
    removeActionButtons();
    ws.send(msg);
}

function onBet(bet){
    stopTimer();
    ws.send(JSON.stringify({
        "type" : "bet",
        "amount" : bet
    }));
}

function skipRound(){
    ws.send(JSON.stringify({
        "type": "skip"
    }));
    closeBet();
}

function askBet(){
    console.log("Bet Asked");
    startTimer(20, skipRound);
    enterBet(onBet);
}