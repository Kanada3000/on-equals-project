$(function () {
    $("#admin #body a.first").click(function (e) {
        e.preventDefault();
        if (!$("#admin #body tr.edit").length) {
            let hidden = $("#admin #body tr.hidden")
            let block = hidden.clone().removeClass("hidden").addClass("edit")
            hidden.before(block)

            $("tr.edit :input").each(function () {
                let input = $(this);
                let inputId = input.attr("id")
                input.attr("name", inputId)
                input.attr("form", "form")
            });

            $("tr.edit select").each(function () {
                let select = $(this);
                let selectId = select.attr("id")
                select.attr("name", selectId)
                select.attr("form", "form")
            });
        }
        list("#admin #body tr.edit ")

        $("#admin #body").on('click', 'td.cancel', function (e) {
            e.preventDefault()
            $(this).parent().remove();
            e.stopImmediatePropagation()
        })
        e.stopImmediatePropagation()
    })

    $("#admin #body").on('click', 'td.accept', function () {
        let i = 1;
        $("tr.edit :input[required]").each(function () {
            let input = $(this);
            let id = $(this).attr("id")
            if (input.val().length <= 0) {
                alert("Поля із * не мають бути порожніми!")
                i = 0;
            } else if(id.indexOf("category") > -1 &&
                jQuery.inArray(input.val(), catList) === -1){
                alert("Поле category обране не зі списку!")
                i = 0;
            } else if(id.indexOf("city") > -1 &&
                jQuery.inArray(input.val(), cityList) === -1){
                alert("Поле city обране не зі списку!")
                i = 0;
            } else if(id.indexOf("type") > -1 &&
                jQuery.inArray(input.val(), typeList) === -1){
                alert("Поле type обране не зі списку!")
                i = 0;
            } else if(id.indexOf("user") > -1 &&
                jQuery.inArray(input.val(), userList) === -1){
                alert("Поле user обране не зі списку!")
                i = 0;
            } else if(id.indexOf("salary") > -1 &&
                !$.isNumeric(input.val())){
                alert("Поле salary має містити лише цифри!")
                i = 0;
            } else if(id.indexOf("description") > -1){
                if(input.val().length > 1000){
                    alert("Поле description має містити не більше 1000 символів!")
                    i = 0;
                }else {
                    let val = input.val()
                    val = replaceSpecSymbols(val)
                    input.val(val)
                }
            }
        });
        $("tr.edit select[required]").each(function () {
            let select = $(this);
            if (select.val().length <= 0) {
                i = 0;
            }
        })
        if (i !== 0) {
            $("#form").submit();
        }

    })

    $("#admin #body").on('click', 'td.edit', function () {
        if (!$("#admin #body tr.edit").length) {
            let id = $(this).parent().find("td.id").text()
            let hidden = $("#admin #body tr.hidden")
            let block = hidden.clone().removeClass("hidden").addClass("edit")
            $(this).parent().addClass("temp")
            $("input#id").val(id)
            $(this).parent().before(block)
            block.find("td.id").html(id)

            $("tr.edit :input").each(function () {
                let input = $(this);
                let inputId = input.attr("id")
                let val = $("#admin #body tr.temp").find("td." + inputId).text()
                input.attr("name", inputId)
                input.attr("form", "form")
                block.find("input#" + inputId).val(val)
            });

            $("tr.edit select").each(function () {
                let input = $(this);
                let inputId = input.attr("id")
                let val = $("#admin #body tr.temp").find("td." + inputId).text()
                input.attr("name", inputId)
                input.attr("form", "form")
                block.find("select#" + inputId + " option:contains('" + val + "')").attr("selected", "selected")
            });

            $("#admin #body").on('click', 'td.cancel', function (e) {
                e.preventDefault()
                $(this).parent().remove();
                $("#admin #body tr.temp").removeClass("temp")
                e.stopImmediatePropagation()
            })

            list("#admin #body tr.edit ")
        }
    })

    $("#admin #body").on('click', 'td.delete', function () {
        if (!$("#admin #body tr.edit").length) {
            $(this).removeClass("delete")
            $(this).addClass("del")
            $("#accept-delete").css("display", "flex")
            $(".backdrop").css({
                "left": "0",
                "opacity": "1"
            })
        }
    })

    $("#accept-delete").on('click', '.button.yes', function () {
        window.location.href = "/admin/" + $("#admin #body td.del").attr("id") + "/delete/" + $("#admin #body td.del").parent().find("td.id").text();
    })

    $("#accept-delete").on('click', '.button.no', function () {
        $("#accept-delete").css("display", "none")
        $("#admin #body td.del").addClass("delete")
        $("#admin #body td.del").removeClass("del")
        $(".backdrop").css({
            "left": "-100%",
            "opacity": "0"
        })
    })
})






