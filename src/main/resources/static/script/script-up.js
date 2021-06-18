$(function () {
    $(window).scroll(function () {
        if (window.pageYOffset > $(window).height()) {
            $("#scroll").show("fast")

        } else $("#scroll").hide("fast")
    })

    $("#scroll").click(function(){
        $("html, body").animate({"scrollTop":"0"}, 500, 'swing')
    })
})






