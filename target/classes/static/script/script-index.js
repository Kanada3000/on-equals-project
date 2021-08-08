$(function () {
    $("li").click(function () {
        setTimeout(function () {
            $(".list .select").removeAttr("style").hide();
        }, 50);
    })
});



