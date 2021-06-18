$(function () {
    let i = 0;
    let myTimeout;
    let n;
    let src;

    $("#celebrities img").mouseenter(function (elem) {
        let img = $(this);
        myTimeout = setTimeout(function () {
            if (!img.hasClass("active") && i === 0) {
                img.animate({
                    "height": "+=5.2vw",
                    "width": "+=5.2vw",
                }).css("z-index", 90).addClass("active");
                src = img.attr("src")
                n = src.indexOf("-");
                $("#celebrities .text").hide()
                $("#celebrities .text-"+src.slice(n+1, -4)).show()
                i++;
            }
        }, 300);

        elem.stopImmediatePropagation();
        return false;

    }).mouseleave(function (elem) {
        let img = $(this)
        clearTimeout(myTimeout);
        if (img.hasClass("active") && i !== 0) {
            img.animate({
                "height": "-=5.2vw",
                "width": "-=5.2vw",
            }, 300).css("z-index", "auto").removeClass("active");
            $("#celebrities .text-"+src.slice(n+1, -4)).hide()
            $("#celebrities .text").show()
            i--;
        }

        elem.stopImmediatePropagation();
        return false;
    })
})
