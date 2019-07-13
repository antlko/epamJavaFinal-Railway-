var route = 'DATE DEPART: ' + $("#dateFrom").text() + ", "
    + 'Initials: ' + $("#init").text() + ", "
    + 'Train Number: ' + $("#numTrain").text() + ", "
    + 'Carriage Number: ' + $("#numCarr").text() + ", "
    + 'Seat Number: ' + $("#numSeat").text() + ", "
    + 'Stations: ' + $("#cityStart").text() + "-"
    + $("#cityEnd").text() + ". ";

var enc = encodeURIComponent(route);
document.getElementById("qr").src =
    'https://chart.apis.google.com/chart?cht=qr&chs=300x300&chld=L&choe=UTF-8&chl=' + enc;
print();