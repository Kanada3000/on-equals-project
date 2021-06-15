
$(function () {
    $("#news .switch-r").click(function(){
        let first = $(".posts :first")
        first.removeClass("active").next().addClass("active");
        $(".posts").append(first)
    });

    $("#news .switch-l").click(function(){
        $(".posts :first").removeClass("active");
        $(".post").last().addClass("active").prependTo($(".posts"));
    });
});