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
        $("tr.edit :input").each(function () {
            let input = $(this);
            if (input.val().length <= 0) {
                i = 0;
            }
        });
        $("tr.edit select").each(function () {
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






