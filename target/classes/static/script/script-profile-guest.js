$(function () {

    let scroll = localStorage.getItem("scrollPos")
    if (scroll !== null) {
        $(window).scrollTop(scroll)
    }

    $("#profile-body .container .block .buttons .heart").click(function () {
        if ($(this).hasClass("red")) {
            $(this).removeClass("red")
            $(this).find("input#likeId").attr("form", "dislikeForm")
            $("input#userId").attr("form", "dislikeForm")
            localStorage.setItem("scrollPos", $(window).scrollTop())
            $("#dislikeForm").submit();
        } else {
            $(this).addClass("red")
            $(this).find("input#likeId").attr("form", "likeForm")
            $("input#userId").attr("form", "likeForm")
            localStorage.setItem("scrollPos", $(window).scrollTop())
            $("#likeForm").submit();
        }
    })

    if (likesId !== null) {
        let likesIdList = likesId.split("&")
        let id
        $("#profile-body .container .block").each(function () {
            id = $(this).find("#likeId").val()
            if (likesIdList.includes(id)) {
                $(this).find(".heart").addClass("red")
            }
        })
    }

});



