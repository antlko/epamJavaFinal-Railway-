var sliderCarr = document.getElementById("rangeCarr");
var sliderSeat = document.getElementById("rangeSeat");

// Update the current slider value (each time you drag the slider handle)
sliderCarr.oninput = function() {
    $(".countRangeCarr").val(sliderCarr.value);
};
sliderSeat.oninput = function() {
    $(".countRangeSeat").val(sliderSeat.value);
};