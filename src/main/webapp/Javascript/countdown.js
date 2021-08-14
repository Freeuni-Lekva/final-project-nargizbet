let tmer  = document.getElementById('timer_text');
var myTimer, myTimerFunc;
var time;


var startTimer = function (duration, afterFunc){
    time = duration;
    tmer.innerHTML = time;
    myTimer = setInterval(function() {editElem();}, 1000);
    myTimerFunc = setTimeout(afterFunc, duration);
}

var stopTimer = function (){
    clearInterval(myTimer);
    clearTimeout(myTimerFunc);
}

var editElem = function(){
    time--;
    tmer.innerHTML = time;
    if(time <= 0) {
        clearInterval(myTimer);
        return;
    }
}