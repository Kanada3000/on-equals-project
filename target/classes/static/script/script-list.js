$(function () {

    list("")
    //Добавление страны/города
    $(".accept.add").click(function (e) {
        e.preventDefault()
        let added = $(this).closest(".accept.add");
        let i = 1;
        $(".country:last input").each(function (index, value) {
            let input = $(this);
            if (input.val() === "") {
                i = 0;
            } else if (index === 1 && jQuery.inArray(input.val(), cityList) === -1) {
                i = 0;
            }
        });
        if (i === 1) {
            if ($("#labelErrorCountry") !== null) {
                $("#labelErrorCountry").remove()
            }
            let clone = $(".country:last").clone(false);
            let li = $(".country:last").find("input").last().val()
            clone.find(".select li:contains('" + li + "')").remove()
            added.before(clone);
            $(".country:last .country-label").each(function () {
                $(this).find("input").val("")
                $(this).find("label").attr("for", $(this).find("label").attr("for") + "-")
                $(this).find(".list").attr("id", $(this).find(".list").attr("id") + "-")
                $(this).find(".list input").attr("id", $(this).find(".list input").attr("id") + "-")
                $(this).find(".select").attr("id", $(this).find(".select").attr("id") + "-")
            })
        } else if (i === 0) {
            let label = document.getElementById("labelErrorCountry")
            if (label === null) {
                label = document.createElement('label')
                label.classList.add('error')
                label.id = "labelErrorCountry"
                label.textContent += "Заповніть поля вище"
                $(".country:last").after(label)
            }
        }
        list(".country .country-label ")
        e.stopImmediatePropagation();
        return false;
    })

});

function list(string) {
    $(string + ".select-label").click(function (e) {
        let id = "#" + $(this).attr("id")
        let idDiv = "#" + $(this).next().attr("id")
        e.preventDefault()
        let div = $(".select" + idDiv)
        let li = $(".select" + idDiv + " li")
        let list = "#" + $(this).parents(".list").first().attr("id")
        div.show();

        li.mouseover(function () {
            if ($(".select" + idDiv).hasClass("green")) {
                $(this).find(".li").addClass("li-green")
            } else $(this).find(".li").addClass("li-hover")
        })

        li.mouseout(function () {
            if ($(".select" + idDiv).hasClass("green")) {
                $(this).find(".li").removeClass("li-green")
            } else $(this).find(".li").removeClass("li-hover")
        })

        if ($(".select" + idDiv).hasClass("green")) {
            $(".li-hover").css("background-color", "#c4de95")
        }
        let text = $(".list" + list + " input:text")


        $(window).click(function (e) {
            if (!$(".select-label" + id).is(e.target) && !$(".select" + idDiv).is(e.target))
                div.hide()
        });

        $('.select' + idDiv).find('li').click(function () {
            let selectResult = $(this).find(".li").html();
            $(this).parent().parent().parent().parent().find('input.select-label' + id).val(selectResult);
        });


        text.keyup(function () {
            if (text.val() !== '') {
                $('.select' + idDiv + ' .li').each(function () {
                    if ($(this).text().toLowerCase().match(new RegExp(text.val().toLowerCase())) != null) {
                        $(this).parent().show();
                    } else {
                        $(this).parent().hide();
                    }
                });
            } else {
                $('.select' + idDiv + ' .li').each(function () {
                    $(this).parent().show()
                });
            }
        });

    });
}




