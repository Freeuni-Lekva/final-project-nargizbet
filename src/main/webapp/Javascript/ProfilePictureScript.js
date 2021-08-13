const imagePreview = function(event) {
    const display = document.getElementById('prof_img');
    display.addEventListener("load", () => {URL.revokeObjectURL(display.src);})
    display.src = URL.createObjectURL(event.target.files[0]);
}

let imageSrc;

const discardPicture = function (event){
    const display = document.getElementById('prof_img');
    display.src = imageSrc;
}

function viewOptions() {
    const profilePicture = document.getElementById('prof_img');
    imageSrc = profilePicture.getAttribute("src");

    const imageOptions = document.getElementById("image-options");
    imageOptions.removeAttribute("style");
    imageOptions.innerHTML = "<form id = image-options class=\"add-image\" action=\"/addimage\" method=\"post\" enctype=\"multipart/form-data\">\n" +
        "            <input type=\"file\" id=\"image\" name=\"image\" accept=\"image/*\" onchange = 'imagePreview(event)'/>\n" +
        "            <div class = \"image-buttons\">\n" +
        "                <button class=\"image-button\" id=\"set-button\" type=\"submit\"> Set Picture </button>\n" +
        "                <button class=\"image-button\" id=\"revert-button\" type=\"reset\" onclick = 'discardPicture()'> Revert </button>" +
        "                <button class=\"image-button\" onclick = 'cancelChanges()'> Cancel </button>\n" +
        "            </div>\n" +
        "        </form>";
    const changeButton = document.getElementById("change-button");
    changeButton.setAttribute("style", "display: none");
}

function cancelChanges() {
    const profilePicture = document.getElementById('prof_img');
    profilePicture.setAttribute("src", imageSrc);
    const imageOptions = document.getElementById("image-options");
    imageOptions.setAttribute("style", "display: none");
    imageOptions.innerHTML = "";
    const changeButton = document.getElementById("change-button");
    changeButton.removeAttribute("style");
}