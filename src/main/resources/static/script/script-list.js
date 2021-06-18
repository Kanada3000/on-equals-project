
$(function () {

    //Обработка выпадающего списка
    $(".select-label").click(function () {
        let div = $(".select")
        let li = $(".select li")
        div.show();

        li.mouseover(function(){
            if($(".select ").hasClass("green")) {
                $(this).find(".li").addClass("li-green")
            } else $(this).find(".li").addClass("li-hover")
        })

        li.mouseout(function(){
            if($(".select ").hasClass("green")) {
                $(this).find(".li").removeClass("li-green")
            } else $(this).find(".li").removeClass("li-hover")
        })

        if($(".select ").hasClass("green")) {
            $(".li-hover").css("background-color", "#c4de95")
        }
        let text = $(".list input:text")


        $(window).click(function (e) {
            if (!$(".select-label").is(e.target) && !$(".select").is(e.target))
                div.hide()
        });

        $('.select').find('li').click(function () {
            let selectResult = $(this).find(".li").html();
            $(this).parent().parent().parent().parent().find('input.select-label').val(selectResult);
        });


        text.keyup(function () {
            if (text.val() !== '') {
                $('.select .li').each(function () {
                    if ($(this).text().toLowerCase().match(new RegExp(text.val().toLowerCase())) != null) {
                        $(this).show();
                    } else {
                        $(this).hide();
                    }
                });
            } else {
                $('.select .li').each(function () {
                    $(this).show()
                });
            }
        });

    });


    //Добавление страны/города
    $(".accept.add").click(function(e){
        let added = $(this).closest(".accept.add");
        let clone = $(".country:last").clone(false);
        added.before(clone);
        e.stopImmediatePropagation(); // он должен остаться только один
        return false;
    })

});




