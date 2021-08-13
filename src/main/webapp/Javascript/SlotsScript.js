function spin() {


    const bet = document.getElementById("input-format").value;

    if (bet <= 0) {
        alert("Your bet has to be positive");
        return;
    }

    if (bet > currentBalance) {
        alert("You do not have that much money on your account");
        return;
    }

    const moneyGambledP = document.getElementById("money-gambled-text-format");
    let moneyGambled = moneyGambledP.innerHTML;
    moneyGambled = moneyGambled.substring(moneyGambled.indexOf('$') + 1);
    moneyGambled = parseFloat(moneyGambled);
    const newMoneyGambled = moneyGambled + parseFloat(bet);
    moneyGambledP.innerHTML = "Money gambled: $" + newMoneyGambled;
    const moneyGambledInput = document.getElementById("moneyGambled");
    moneyGambledInput.setAttribute("value", newMoneyGambled);

    let spinAmount = 20;
    const imageAmount = 6;
    const delay = 100;

    const spinButton = document.getElementById("spin-button");
    const exitButton = document.getElementById("exit-button");
    spinButton.setAttribute("disabled", "");
    exitButton.setAttribute("disabled", "");

    setTimeout(function () {
        spinButton.removeAttribute("disabled");
        exitButton.removeAttribute("disabled");
    }, spinAmount * delay + 800);

    randomize(0, spinAmount, imageAmount, delay);
    setTimeout(function() {
        randomize(1, spinAmount, imageAmount, delay);
    }, delay);
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