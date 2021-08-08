$(function () {

    let scroll = localStorage.getItem("scrollPos")
    if (scroll !== null) {
        $(window).scrollTop(scroll)
        // localStorage.setItem("scrollPos", "0")

    }

    $("#vacancies .vacancy .buttons .heart").click(function () {
        if ($(this).hasClass("red")) {
            $(this).removeClass("red")
            let value = $("input#dislikes-input").val() + "&" + $(this).parent().parent().find("#idLike").text()
            $("input#dislikes-input").val(value)
        } else {
            $(this).addClass("red")
            let value = $("input#likes-input").val() + "&" + $(this).parent().parent().find("#idLike").text()
            $("input#likes-input").val(value)
        }
        localStorage.setItem("scrollPos", $(window).scrollTop())
        $("#sortForm").submit();
    })

    $("#likes #vacancies .vacancy .buttons .like").click(function () {
        let searchParams = new URLSearchParams(window.location.search)
        if (searchParams.has('key_words')) {
            $("#sort input#" + searchParams.get('sort')).prop("checked", "true")
        }
        $(this).parent().parent().find("#likesId").attr("form", "likeForm");
        localStorage.setItem("scrollPos", $(window).scrollTop())
        $("#likeForm").submit();

    })

    $(".roll-down").click(function () {
        $(this).css("display", "none")
        $(this).parent().find(".descript").slideDown();
        $(this).parent().find(".roll-up").css("display", "block");
    })

    $(".roll-up").click(function () {
        $(this).parent().find(".roll-down").css("display", "block");
        $(this).parent().find(".descript").slideUp(150);
        $(this).css("display", "none")
    })

});



