$(function () {

    let scroll = localStorage.getItem("scrollPos")
    if (scroll !== null) {
        $(window).scrollTop(scroll)
    }


    let step = 1000
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

    if(searchParams.has('category')){
        for(let check of searchParams.entries()) {
            if(check[0] === 'category'){
                $("#category .cat-list input#cat-"+ check[1]).prop("checked", "true")
            }
        }
    }

    if(searchParams.has('type')){
        for(let check of searchParams.entries()) {
            if(check[0] === 'type'){
                $("#category .type-list input#type-"+ check[1]).prop("checked", "true")
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
    output_1.setAttribute("style", "left: " + (((width - 40) * (min_start / (max - min))) - output_1.offsetWidth / 2) + "px")

    let slider_2 = document.getElementById("max");
    let output_2 = document.getElementById("max-output");
    slider_2.setAttribute("min", min)
    slider_2.setAttribute("max", max)
    slider_2.setAttribute("value", max_start)
    output_2.innerHTML = numberFormat(slider_2.value);
    output_2.setAttribute("style", "left: " + (((width - 40) * (max_start / (max - min))) - output_2.offsetWidth / 2) + "px")


    slider_1.oninput = function () {
        if (parseInt(this.value) >= parseInt(slider_2.value)) {
            console.log("BEFORE ---- " + slider_2.value)
            slider_2.value = parseInt(this.value) + step
            console.log("AFTER ---- " + slider_2.value)
            output_2.innerHTML = numberFormat(slider_2.value);
            pos = slider_2.value / (max - min)
            output_2.setAttribute("style", "left: " + (((width - 40) * pos) - output_2.offsetWidth / 2) + "px")
        }
        output_1.innerHTML = numberFormat(this.value);
        pos = this.value / (max - min)
        output_1.setAttribute("style", "left: " + (((width - 40) * pos) - output_1.offsetWidth / 2) + "px")
    }

    slider_2.oninput = function () {
        if (parseInt(this.value) <= parseInt(slider_1.value)) {
            slider_1.value = this.value - step
            output_1.innerHTML = numberFormat(slider_1.value);
            pos = slider_1.value / (max - min)
            output_1.setAttribute("style", "left: " + (((width - 40) * pos) - output_1.offsetWidth / 2) + "px")
        }
        output_2.innerHTML = numberFormat(this.value);
        pos = this.value / (max - min)
        output_2.setAttribute("style", "left: " + (((width - 40) * pos) - output_2.offsetWidth / 2) + "px")
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

    let timer;

    $('#category .salary .input input').click(function () {
        if (timer)
            clearTimeout(timer);
        timer = setTimeout(function () {
            localStorage.setItem("scrollPos", $(window).scrollTop())
            $("#sortForm").submit()
        }, 1500)
    });

    $('#category .cat-list label').click(function () {
        if (timer)
            clearTimeout(timer);
        timer = setTimeout(function () {
            localStorage.setItem("scrollPos", $(window).scrollTop())
            $("#sortForm").submit()
        }, 1500)
    });


    $('#category .type-list label').click(function () {
        if (timer)
            clearTimeout(timer);
        timer = setTimeout(function () {
            localStorage.setItem("scrollPos", $(window).scrollTop())
            $("#sortForm").submit()
        }, 1500)
    });

    $("#vacancies .vacancy .buttons .heart").click(function (){
        $(this).css("background-color", "#FF5C55")
        let value = $("input#likes-input").val() +
        $("input#likes-input").val()
    })
})

function numberFormat(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, "&nbsp;");
}






