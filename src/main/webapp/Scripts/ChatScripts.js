var wsocket;

function connect(id){
    console.log(id);
    wsocket = new WebSocket("ws://localhost:8080/chat/" + id);
    wsocket.onmessage = newMessage;
}

function sendMessage(){
    wsocket.send(JSON.stringify({"username" : document.getElementsByClassName('username').item(0).value,
                                       "message" : document.getElementsByClassName('enterMessage').item(0).value}));
}

function newMessage(evt){
    addMessage(evt.username, evt.message);
}