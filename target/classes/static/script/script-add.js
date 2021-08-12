$(function () {
    let hr = document.createElement('hr')
    hr.classList.add('delimiter')

    let inputCity = document.createElement('input')
    inputCity.setAttribute("name", "citString")
    inputCity.setAttribute("value", ".")
    inputCity.setAttribute("type", "hidden")

    let clone = $("#form-reg-employer").children("#reg2").clone(false)
    let last
    let scrollTo
    let i = Number.parseInt(1)

    $("#reg2_sub .submit .add-vacancy").click(function (e) {
        e.preventDefault();
        if (i !== 4) {
            if ($("#form-reg-employer").valid()) {
                let plus = ""
                for (let k = 0; k < i; k++) {
                    plus = plus + '-'
                }
                $(".errorMessage").css("display", "none")
                let cloneClone = clone.clone()
                last = $("#form-reg-employer").children("#reg2").last()
                last.after(hr.cloneNode(true))
                last.after(inputCity.cloneNode(true))
                let lastHr = $("hr.delimiter:last")
                cloneClone.insertAfter(lastHr)

                cloneClone.find("input[type=hidden]").each(function () {
                    $(this).remove()
                })

                cloneClone.find("input").each(function () {
                    let inp = $(this)
                    if (inp.attr("id")) {
                        let id = inp.attr("id");
                        inp.attr("id", id + plus)
                    }
                })

                cloneClone.find("#categoryList").each(function () {
                    let list = $(this)
                    if (list.attr("id")) {
                        let id = list.attr("id");
                        list.attr("id", id + plus)
                    }

                    $(this).find("#vacancy-div").each(function () {
                        let list = $(this)
                        if (list.attr("id")) {
                            let id = list.attr("id");
                            list.attr("id", id + plus)
                        }
                    })
                })

                cloneClone.find("label").each(function () {
                    let lab = $(this)
                    if (lab.attr("for")) {
                        let f = lab.attr("for");
                        lab.attr("for", f + plus)
                    }
                })

                cloneClone.find(".green-choice").each(function () {
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

                // alert($("#form-reg-employer").children().last().find("input[type=radio]:checked").val())

                // cloneClone.insertAfter(lastHr)
                scrollTo = $("hr.delimiter:last").offset().top
                $("html, body").animate({"scrollTop": scrollTo}, 500, 'swing')

                i = i + 1
                list(".country .country-label ")
                list("#catList ")
                return false
            } else {
                $(".errorMessage").css("display", "block")
                $(".errorMessage").text("Спочатку заповніть попередню форму")
                return false
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
                localStorage.setItem("alertMode", "res")
                $("#formResume").submit();
            } else if (!$("#error2").length) {
                $("#invalid").append("<label id='error2' class='error'>Розмір перевищує ліміт (10МБ)</label>")
            }
        } else if (!$("#error").length) {
            $("#invalid").append("<label id='error' class='error'>Файл має бути в форматі pdf</label>")
        }
    } else {
        if ($("#form-reg-employer").valid()) {
            $("input[name=category]").each(function () {
                let val = $(this).val()
                $(this).after("<input type='hidden' name='categoryList'>")
                $(this).next().val(val.replaceAll(",", "%"))
            })
            $("#form-reg-employer input[type=radio]:checked").each(function () {
                $(this).after("<input type='hidden' name='typeList' value='" + $(this).val() + "'>")
            })
            $("#reg2").first().after("<input type='hidden' name='categoryList' value=''>")
            localStorage.setItem("alertMode", "res")
            $("#form-reg-employer").submit();
        }
    }

}



