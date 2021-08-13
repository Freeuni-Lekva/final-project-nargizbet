var ws;

function connectBlackjack(id, amount){
    console.log(id);
    ws = new WebSocket("ws://localhost:8080/game/blackjack/" +  id + "/" + amount);
    ws.onmessage = onMessageBlackjack;
    console.log("Connected");
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
        displayMessage("Bust");
    }
    if(actionType == "ClearAction"){
        removeEveryCard();
    }
    if(actionType == "DrawTableAction"){
        drawTable(eventJson);
    }
    if(actionType == "NextPlayerAction"){
        nextPlayer(eventJson.username);
    }
    if(actionType == "RemovePlayerAction"){
        removePlayerJS(eventJson);
    }
    if(actionType == "ResultAction"){
        displayMessage(eventJson.result);
    }

}

function drawTable(event){
    addPlayer("dealer");
    let dealerCards = event.dealer.currentCards;
    let arrLength = dealerCards.length;
    for(let i = 0; i < arrLength; i++){
        addCard("dealer", dealerCards[i].suit, (dealerCards[i])['value']);
    }

    let playerList = event.players;
    let playersSize = event.players.length;
    for(let i = 0; i < playersSize; i++){
        addPlayer((playerList[i])['username']);
        let cardsList = playerList[i].currentCards;
        let cardLength = cardsList.length;
        for(let j = 0; j < cardLength; j++){
            addCard((playerList[i])['username'], cardsList[j].suit, (cardsList[j])['value']);
        }
    }
}

function addPlayerCard(event){
    let user = event['username'];
    let cardList = event['cards'];
    let cardListLength = cardList.length;
    for(let i = 0; i < cardListLength; i++){
        let card = cardList[i];
        addCard(user, card.suit, card['value']);
    }
}

function addPlayerJS(msg){
    addPlayer(msg['username']);
}

function removePlayerJS(msg){
    removePlayer(msg['username']);
}

function hitMessage(){
    sendMoveMessage(JSON.stringify({
        "type" : "move",
        "move" : "hit"
    }))
}

function standMessage(){
    sendMoveMessage(JSON.stringify({
        "type" : "move",
        "move" : "stand"
    }))
}

function askMove(){
    drawActionButtons(hitMessage, standMessage);
}

function sendMoveMessage(msg){
    removeActionButtons();
    ws.send(msg);
}

function onBet(bet){
    ws.send(JSON.stringify({
        "type" : "bet",
        "amount" : bet
    }));
}

function askBet(){
    console.log("Bet Asked");
    enterBet(onBet);
}