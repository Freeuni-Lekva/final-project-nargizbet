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