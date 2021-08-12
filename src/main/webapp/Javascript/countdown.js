let tmer  = document.getElementById('timer_text');
var myTimer = setInterval(function() {editElem();}, 1000);
var time;


var startTimer = function (){
    time = 20;
    tmer.innerHTML = time;
    myTimer;
}

var editElem = function(){
    time--;
    tmer.innerHTML = time;
    if(time <= 0) {
        clearInterval(myTimer);
        return;
    }
}