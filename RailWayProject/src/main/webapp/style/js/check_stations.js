// Create a new list item when clicking on the "Add" button
function newElement() {
    var li = document.createElement("LI");
    li.className = 'new_li';
    var inputValue = document.getElementById("myInput").value;
    var timeValue = document.getElementById("time-station").value;
    var timeValueEnd = document.getElementById("time-station-end").value;
    var priceValue = document.getElementById("price-value").value;
    var t = document.createTextNode(inputValue + ", " + timeValue + "-" + timeValueEnd + ". Price: " + priceValue + ";");
    var myNodelist = $(".new_li").text();

    console.log(myNodelist + ", " + inputValue);
    li.appendChild(t);
    if (inputValue === '' || timeValue === '') {
        alert("You must write something!");
    } else {
        if (!myNodelist.includes(inputValue.split(",")[0])) {
            document.getElementById("myUL").appendChild(li);
        } else {
            alert("This value already exit");
        }
    }
    document.getElementById("myInput").value = "";
    document.getElementById("listInfoStation").value = $(".new_li").text();
}

function removeAllElement() {
    $('#myUL').empty();
}