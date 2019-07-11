var route = 'DATE DEPART: ' + $("#dateFrom").text() + ", "
    + 'Initials: ' + $("#init").text() + ", "
    + 'Train Number: ' + $("#numTrain").text() + ", "
    + 'Carriage Number: ' + $("#numCarr").text() + ", "
    + 'Seat Number: ' + $("#numSeat").text() + ", "
    + 'Stations: ' + $("#cityStart").text() + "-"
    + $("#cityEnd").text() + ". ";

document.getElementById("qr").src = 'http://qrcoder.ru/code/?' + route + '&4&0';
print();