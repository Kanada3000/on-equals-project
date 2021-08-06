$(function () {
    // $("#resume-list .block").each(function () {
    //     let title = $(this).find(".title").width()
    //     let span = $(this).find(".title span").width()
    //     if (span > title) {
    //         let coef = title / span
    //         let fontSize = parseInt($(this).find(".title").css("font-size").replace('px', ''))
    //         fontSize = Math.floor(fontSize * coef);
    //         $(this).find(".title").css("font-size", fontSize + "px")
    //     }
    // })

    $("#resume-list .block").click(function (){
        let id = $(this).find("#id").val()
        window.location.href = ("/pages/" + id);
    })
})



