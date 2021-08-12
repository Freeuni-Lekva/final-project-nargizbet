function spin() {

    const currentBalanceP = document.getElementById("balance-text-format");
    let currentBalance = currentBalanceP.innerHTML;
    currentBalance = currentBalance.substring(currentBalance.indexOf('$') + 1);
    currentBalance = parseFloat(currentBalance);

    const bet = document.getElementById("input-format").value;

    if (bet > currentBalance) {
        alert("You do not have that much money on your account");
        return;
    }

    let spinAmount = 20;
    const imageAmount = 6;
    const delay = 100;

    const spinButton = document.getElementById("spin-button");
    spinButton.setAttribute("disabled", "");

    setTimeout(function () {
        spinButton.removeAttribute("disabled");
    }, spinAmount * delay + 800);

    randomize(0, spinAmount, imageAmount, delay);
    setTimeout(function() {
        randomize(1, spinAmount, imageAmount, delay);
    }, delay);
    setTimeout(function() {
        randomize(2, spinAmount, imageAmount, delay);
    }, 2 * delay);

    setTimeout(function () {
        spinResult(currentBalanceP, currentBalance, bet);
    }, spinAmount * delay + 700);

}

function randomize(slotId, spinAmount, imageAmount, delay) {

    const slot = document.getElementById("slot" + `${slotId}`);

    setTimeout(function () {
        spinOnce(slot, slotId, spinAmount, imageAmount, delay);
    }, delay);

}

function getRandomInt(limit) {
    return Math.floor(Math.random() * limit);
}

function spinOnce(slot, slotId, spinAmount, imageAmount, delay) {
    let randomId = getRandomInt(imageAmount);
    slot.innerHTML = '<img src = "Images/SlotsImage' + `${randomId}` +
                     '.png" class = image-format id = "slot' + `${slotId}` + '-img"/>';
    if (spinAmount > 0) {
        setTimeout(function () {
            spinOnce(slot, slotId, spinAmount - 1, imageAmount, delay);
        }, delay);
    }
}

function spinResult(currentBalanceP, currentBalance, bet) {
    const slot0Img = document.getElementById("slot0-img").getAttribute("src");
    const slot1Img = document.getElementById("slot1-img").getAttribute("src");
    const slot2Img = document.getElementById("slot2-img").getAttribute("src");

    let delta;

    if (slot0Img === slot1Img && slot1Img === slot2Img && slot2Img === "Images/SlotsImage5.png") {
        delta = bet * 100;
        alert("JACKPOT!")
    } else if (slot0Img === slot1Img && slot1Img === slot2Img) {
        delta = bet * 10;
    } else if (slot0Img === slot1Img || slot0Img === slot2Img || slot1Img === slot2Img) {
        delta = bet * 3;
    } else {
        delta = bet * -1;
    }

    const newBalance = currentBalance + delta;
    currentBalanceP.innerHTML = "Current Balance: $" + newBalance;

    const exitBalance = document.getElementById("balance");
    exitBalance.setAttribute("value", newBalance);
}