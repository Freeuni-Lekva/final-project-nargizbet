var wsocket;

function connect(id){
    console.log(id);
    wsocket = new WebSocket("ws://localhost:8080/chat/" + id);
    wsocket.onmessage = onMessage;
}

function onMessage(evt){
    var k = JSON.parse(evt.data);
    addMessage(k.username, k.message);
}

function sendMessage(){
    let msg  = document.getElementsByClassName('enterMessage').item(0).value;
    document.getElementById('message_box').value = '';
    wsocket.send(JSON.stringify({"username" : document.getElementsByClassName('username').item(0).value,
                                      "message" : msg}));
}