var ws = new WebSocket("")

function connect(id, amount){
    console.log(id);
    wsocket = new WebSocket("ws://localhost:8080/chat/" +  id + "/" + amount);
    wsocket.onmessage = onMessage;
    wsocket.onclose = onClose;
    wsocket.onopen = onOpen;
}


function onMessage(event){
}

function onClose(event){
}

function onOpen(event){
}

function addPlayer(msg){
    addPlayer(msg['username']);
}

function removePlayer(msg){
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