$(function () {
    if (alertMode === "true") {
        $("#alert").css("display", "flex")
        $("#alert").animate({
            bottom: '20px'
        }, 400, function () {
        });
        setTimeout(function () {
            $("#alert").stop().animate({
                bottom: '-200px'
            }, 400, function () {
            });
            setTimeout(function () {
                $("#alert").css("display", "none")
            }, 1000);
        }, 4000);
    }
});



