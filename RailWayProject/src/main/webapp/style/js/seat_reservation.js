var settings = {
    rows: 3,
    cols: 15,
    rowCssPrefix: 'row-',
    colCssPrefix: 'col-',
    seatWidth: 40,
    seatHeight: 40,
    seatCss: 'seat',
    selectedSeatCss: 'selectedSeat',
    selectingSeatCss: 'selectingSeat'
};

var init = function (reservedSeat) {
    var str = [], seatNo, className;
    for (i = 0; i < settings.rows; i++) {
        for (j = 0; j < settings.cols; j++) {
            seatNo = (i + j * settings.rows + 1);
            className = settings.seatCss + ' '
                + settings.rowCssPrefix + i.toString()
                + ' ' + settings.colCssPrefix + j.toString();
            if ($.isArray(reservedSeat) && $.inArray(seatNo, reservedSeat) == -1) {
                className += ' ' + settings.selectedSeatCss;
            }
            str.push('<li class="' + className + '"' + 'title="' + seatNo + '"' +
                'style="top:' + (i * settings.seatHeight).toString() + 'px;left:' + (j * settings.seatWidth).toString() + 'px">' +
                '<a title="' + seatNo + '">' + seatNo + '</a>' +
                '</li>');
            // document.write(str.toString());
        }
    }
    $('#place').html(str.join(''));
};
//case I: Show from starting
// init();

//Case II: If already booked
var lInf = document.getElementsByClassName("sLi");
var listArray = [];
for (var i = 0; i < lInf.length; i++) {
    listArray.push(+lInf[i].textContent);
}
init(listArray);

$('.' + settings.seatCss).click(function () {
    if (!$(this).hasClass(settings.selectedSeatCss)) {
        //alert('This seat is already reserved');
        $(this).toggleClass(settings.selectingSeatCss);
    }
    var totalPrice = 0;
    var listNums = [];
    var classList = document.getElementsByClassName(settings.seatCss);
    for (var i = 0; i < classList.length; i++) {
        var names = classList[i].className.split(' ');
        names.forEach(function (nm) {
            if (nm === settings.selectingSeatCss) {
                console.log(classList[i].title);
                listNums.push(classList[i].title);
                totalPrice += parseInt(document.getElementById("rtPrice").value);
            }
        });
    }
    console.log("Total price " + totalPrice);
    $("#chSeat").val(listNums.join(' '));
    $("#totalPrice").val(totalPrice);
    console.log('~~~');
});

$('#btnShow').click(function () {
    var str = [];
    $.each($('#place li.' + settings.selectedSeatCss + ' a, #place li.' + settings.selectingSeatCss + ' a'), function (index, value) {
        str.push($(this).attr('title'));
    });
    alert(str.join(','));
});

$('#btnShowNew').click(function () {
    var str = [], item;
    $.each($('#place li.' + settings.selectingSeatCss + ' a'), function (index, value) {
        item = $(this).attr('title');
        str.push(item);
    });
    alert(str.join(','));
});