var ws = new WebSocket("")

function connect(id, amount){
    console.log(id);
    wsocket = new WebSocket("ws://localhost:8080/chat/" +  id + "/" + amount);
    wsocket.onmessage = onMessage;
    wsocket.onclose = onClose;
    wsocket.onopen = onOpen;
}

function onMessage(event){
    let actionType = event["TYPE"];
    if(actionType == "AddCardAction") {
        addPlayerCard(event);
    }
    if(actionType == "AddPlayerAction"){
        addPlayer(event["username"]);
    }
    if(actionType == "AskBetAction"){
        askBet(event);
    }
    if(actionType == "AskMoveAction") {
        askMove();
    }
    if(actionType == "BustedAction"){
        displayMessage("Bust");
    }
    if(actionType == "ClearAction"){
        clearTable();
    }
    if(actionType == "DrawTableAction"){
        drawTable(event);
    }
    if(actionType == "NextPlayerAction"){
        nextPlayer(event['username']);
    }
    if(actionType == "RemovePlayerAction"){
        removePlayerJS(event);
        removePlayer(event["username"]); 
    }
    if(actionType == "ResultAction"){
        displayMessage(event['result']);
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

function onClose(event){
}

function onOpen(event){
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

function onBet(event,bet){
    removeBetButton();
    setBet(event["username"],bet);
    ws.send(JSON.stringify({
        "type" : "BetAction",
        "amount" : bet
    }));
}

function askBet(event){
    drawBetButton(onBet,event);
}