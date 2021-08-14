let timer  = document.getElementById('timer_text');
var myTimer, myTimerFunc;
var time;


var startTimer = function (duration, afterFunc){
    stopTimer();
    time = duration;
    timer.innerHTML = time;
    myTimer = setInterval(function() {editElem();}, 1000);
    myTimerFunc = setTimeout(function () {timer.innerHTML = ""; afterFunc(); }, duration * 1000);
}

var stopTimer = function (){
    timer.innerHTML = "";
    clearInterval(myTimer);
    clearTimeout(myTimerFunc);
}

var editElem = function(){
    time--;
    timer.innerHTML = time;
    if(time <= 0) {
        clearInterval(myTimer);
        return;
    }
}