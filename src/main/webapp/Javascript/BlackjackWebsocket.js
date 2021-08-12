var ws = new WebSocket("")

function connect(id, amount){
    console.log(id);
    wsocket = new WebSocket("ws://localhost:8080/chat/" +  id + "/" + amount);
    wsocket.onmessage = onMessage;
    wsocket.onclose = onClose;
    wsocket.onopen = onOpen;
}

function onMessage(event){
    if(event['type'] === "BustedAction"){
        displayMessage("Bust");
    }else if(event['type'] === "ClearAction"){
        clearTable();
    }else if(event['type'] === "ResultAction"){
        displayMessage(event['result']);
    }else if(event['type'] === "NextPlayerAction"){
        nextPlayer(event['username']);
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