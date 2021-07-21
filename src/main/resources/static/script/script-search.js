$(function () {

    let scroll = localStorage.getItem("scrollPos")
    if (scroll !== null) {
        $(window).scrollTop(scroll)
    }

    setTimeout(function () {
        localStorage.setItem("scrollPos", "0")
    }, 200);


        if (likesId !== null) {
        let likesIdList = likesId.split("&")
        let id
        $("#vacancies .vacancy").each(function () {
            id = $(this).find("#idLike").text()
            if (likesIdList.includes(id)) {
                $(this).find(".heart").addClass("red")
            }
        })
    }

    let step = 1000

    if (max % step !== 0) {
        max = Math.floor(max / step) * step + step
    }

    if (min % step !== 0) {
        min = Math.floor(min / step) * step
    }
    let min_val = min
    let max_val = max
    let min_start = min.toString()
    let max_start = max.toString()

    let searchParams = new URLSearchParams(window.location.search)

    if (searchParams.has('sort')) {
        $("#sort input#" + searchParams.get('sort')).prop("checked", "true")
    } else {
        $("#sort input#id").prop("checked", "true")
    }

    if (searchParams.has('min')) {
        min_start = searchParams.get('min')
    }

    if (searchParams.has('min')) {
        max_start = searchParams.get('max')
    }

    if (searchParams.has('category')) {
        for (let check of searchParams.entries()) {
            if (check[0] === 'category') {
                $("#category .cat-list input#cat-" + check[1]).prop("checked", "true")
            }
        }
    }

    if (searchParams.has('type')) {
        for (let check of searchParams.entries()) {
            if (check[0] === 'type') {
                $("#category .type-list input#type-" + check[1]).prop("checked", "true")
            }
        }
    }

    let slider_1 = document.getElementById("min");
    let output_1 = document.getElementById("min-output");
    slider_1.setAttribute("min", min)
    slider_1.setAttribute("max", max)
    slider_1.setAttribute("value", min_start)
    output_1.innerHTML = numberFormat(slider_1.value);
    let width = slider_1.offsetWidth
    let pos
    slider_1.setAttribute("step", step)
    pos = (min_start - min) / (max - min)
    output_1.setAttribute("style", "left: " + ((width - 20) * pos) + "px")

    let slider_2 = document.getElementById("max");
    let output_2 = document.getElementById("max-output");
    slider_2.setAttribute("min", min)
    slider_2.setAttribute("max", max)
    slider_2.setAttribute("value", max_start)
    output_2.innerHTML = numberFormat(slider_2.value);
    pos = (max_start - min) / (max - min)
    output_2.setAttribute("style", "left: " + ((width - 20) * pos) + "px")


    slider_1.oninput = function () {
        if (parseInt(this.value) >= parseInt(slider_2.value)) {
            slider_2.value = parseInt(this.value) + step
            output_2.innerHTML = numberFormat(slider_2.value);
            pos = (slider_2.value - min) / (max - min)
            output_2.setAttribute("style", "left: " + ((width - 20) * pos) + "px")
        }
        output_1.innerHTML = numberFormat(this.value);
        pos = (this.value - min) / (max - min)
        output_1.setAttribute("style", "left: " + (((width - 20) * pos)) + "px")
    }

    slider_2.oninput = function () {
        if (parseInt(this.value) <= parseInt(slider_1.value)) {
            slider_1.value = this.value - step
            output_1.innerHTML = numberFormat(slider_1.value);
            pos = (slider_1.value - min) / (max - min)
            output_1.setAttribute("style", "left: " + ((width - 20) * pos) + "px")
        }
        output_2.innerHTML = numberFormat(this.value);
        pos = (this.value - min) / (max - min)
        output_2.setAttribute("style", "left: " + ((width - 20) * pos) + "px")
    }


    $("#sort label").click(function () {
        if ($(this).prev().prop("checked")) {
            return false
        } else {
            $(this).prev().prop("checked", "true")
            localStorage.setItem("scrollPos", $(window).scrollTop())
            $("#sortForm").submit()
        }
    })

    $('#category .salary .input input:not(:disabled)').click(function () {
        localStorage.setItem("scrollPos", $(window).scrollTop())
        $("#sortForm").submit()
    });

    $('#category .cat-list label').click(function () {
        if ($(this).prev("input:not(:disabled)").length) {
            $(this).prev("input").prop("checked", !$(this).prev("input").prop("checked"))
            localStorage.setItem("scrollPos", $(window).scrollTop())
            $("#sortForm").submit()
        }
    });

    $('#category .type-list label').click(function () {
        if ($(this).prev("input:not(:disabled)").length) {
            $(this).prev("input").prop("checked", !$(this).prev("input").prop("checked"))
            localStorage.setItem("scrollPos", $(window).scrollTop())
            $("#sortForm").submit()
        }
    });
})

function numberFormat(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, "&nbsp;");
}






