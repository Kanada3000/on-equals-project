$(function () {

    // Добавление menu
        $(".header__nav").click(function () {
            $("#modalMenu").show();
            document.body.style.overflow = 'hidden';
        });

        $("#x").click(function () {
            $("#modalMenu").hide();
            document.body.style.overflow = 'visible';
        });

});



