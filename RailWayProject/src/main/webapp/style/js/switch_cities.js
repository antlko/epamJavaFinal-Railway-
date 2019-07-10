$('.switch_img').on("click", function () {
    console.log("Click!");
    var cityStart = $("#cityStart").val();
    var cityEnd =$("#cityEnd").val();
    $("#cityStart").val(cityEnd);
    $("#cityEnd").val(cityStart);
});