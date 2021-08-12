function spin() {

    const delay = 300;

    randomize(0, delay);
    setTimeout(function() {
        randomize(1, delay);
    }, 2 * delay);
    setTimeout(function() {
        randomize(2, delay);
    }, 3 * delay);

}

function getRandomInt(limit) {
    return Math.floor(Math.random() * limit);
}

function randomize(slotId, delay) {

    const slot = document.getElementById("slot" + `${slotId}`);

    let spinAmount = 10;
    const imageAmount = 5;

    setTimeout(function () {
        spinOnce(slot, slotId, spinAmount, imageAmount, delay);
    }, delay);

}

function spinOnce(slot, slotId, spinAmount, imageAmount, delay) {
    let randomId = getRandomInt(imageAmount);
    slot.innerHTML = '<img src = "Images/SlotImage' + `${randomId}` +
                     '.png" alt="" width="180" height=300 id = "slot' + `${slotId}` + '-img"/>';
    if (spinAmount > 0) {
        setTimeout(function () {
            spinOnce(slot, slotId, spinAmount - 1, imageAmount, delay);
        }, delay);
    }
}