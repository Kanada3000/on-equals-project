$(function () {

    setTimeout(function () {
        $( window ).resize(function() {
            let text
            let logo = $("header .header__logo")
            let panel = $("header .header__panel")
            let name = $("header .header__sign .button.profile").parent()
            let paddings = $("header").css("padding-left")
            let res = panel.width() + logo.width() + 2 * paddings.substring(0, paddings.length - 2)
            if (res >= $(window).width()) {
                text = name.children().text().substring(0, 1)
                name.children().text(text)
                name.children().css("padding","0")
                if($(window).width() < 480){
                    name.children().css("width","calc(-11px + 16.9vw)")
                    name.children().css("height","calc(-11px + 16.9vw)")
                } else {
                    name.children().css("width","70px")
                    name.children().css("height","70px")
                }
                name.children().css("justify-content","center")
            }
        });
    }, 100);

// Добавление menu
    $(".header__nav").click(function () {
        $("#modalMenu").show();
        document.body.style.overflow = 'hidden';
    });

    $("#x").click(function () {
        $("#modalMenu").hide();
        document.body.style.overflow = 'visible';
    });

    $(".formSubmit").click(function () {
        $('#logoutForm').submit();
        return false;
    })

    $("a[href='/logout']").click(function (e){
        e.preventDefault();
        $("#logout").submit();
    })
}, 5000);

function replaceSpecSymbols(val){
    val = val.replace(/\(/g, "&#40;");
    val = val.replace(/\)+/g, '&#41;');
    val = val.replace(/\.+/g, '&#46;');
    val = val.replace(/:+/g, '&#58;');
    return val
}



