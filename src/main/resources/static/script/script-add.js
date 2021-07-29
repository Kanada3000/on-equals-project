$(function () {
    let hr = document.createElement('hr')
    hr.classList.add('delimiter')

    let inputCity = document.createElement('input')
    inputCity.setAttribute("name", "citString")
    inputCity.setAttribute("value", ".")
    inputCity.setAttribute("type", "hidden")

    let clone = $("#form-reg-employer").children().clone(false)
    let last
    let scrollTo
    let i = Number.parseInt(1)
    $("#reg2_sub .submit .add-vacancy").click(function (e) {
        e.preventDefault();

        if (i !== 4) {
            if ($("#form-reg-employer").valid()) {
                let plus = ""
                for (let k = 0; k < i; k++) {
                    plus = plus + '+'
                }
                $(".errorMessage").css("display", "none")
                let cloneClone = clone.clone()
                last = $("#form-reg-employer").children().last()
                last.after(hr.cloneNode(true))
                last.after(inputCity.cloneNode(true))
                let lastHr = $("hr.delimiter:last")
                cloneClone.insertAfter(lastHr)

                lastHr.nextAll("input[type=hidden]").each(function () {
                    $(this).remove()
                })

                lastHr.nextAll("input").each(function () {
                    let inp = $(this)
                    if (inp.attr("id")) {
                        let id = inp.attr("id");
                        inp.attr("id", id + plus)
                    }
                })
                lastHr.nextAll("label").each(function () {
                    let lab = $(this)
                    if (lab.attr("for")) {
                        let f = lab.attr("for");
                        lab.attr("for", f + plus)
                    }
                })

                lastHr.nextAll(".green-choice").each(function () {
                    $(this).find("label").each(function () {
                        let lab = $(this)
                        if (lab.attr("for")) {
                            let f = lab.attr("for");
                            lab.attr("for", f + plus)
                        }
                    })

                    $(this).find("input").each(function () {
                        let inp = $(this)
                        if (inp.attr("id")) {
                            let id = inp.attr("id");
                            inp.attr("id", id + plus)
                        }
                        let name = inp.attr("name");
                        inp.attr("name", name + plus)
                    })

                })

                scrollTo = $("hr.delimiter:last").offset().top
                $("html, body").animate({"scrollTop": scrollTo}, 500, 'swing')

                i = i + 1
                list(".country .country-label ")
                list("#catList ")
            } else {
                $(".errorMessage").css("display", "block")
                $(".errorMessage").text("Спочатку заповніть попередню форму")
            }
        } else {
            $(".errorMessage").css("display", "block")
            $(".errorMessage").text("Упс, одночасно можна додати лише 4 резюме")
            return false
        }

        e.stopImmediatePropagation()
    })
});

function onSubmit(token) {
    if ($("#form-type").val() === "file") {
        let size = $("#size").val()
        if ($("#download").val().split('.').pop().toLowerCase() === "pdf") {
            if (size / 1048576 < 10) {
                $("#formResume").submit();
            } else if (!$("#error2").length) {
                $("#invalid").append("<label id='error2' class='error'>Розмір перевищує ліміт (10МБ)</label>")
            }
        } else if (!$("#error").length) {
            $("#invalid").append("<label id='error' class='error'>Файл має бути в форматі pdf</label>")
        }
    } else {
        if ($("#form-reg-employer").valid()) {

            $("#form-reg-employer input[type=radio]:checked").each(function () {
                $(this).after("<input type='hidden' name='typeList' value='" + $(this).val() + "'>")
            })

            $("#form-reg-employer").submit();
        }
    }

}



