var checkAmount = function(){
    if(+document.getElementById('amount').value <= 0){
        alert("Please enter a positive amount");
        return false;
    }
    return true;
}