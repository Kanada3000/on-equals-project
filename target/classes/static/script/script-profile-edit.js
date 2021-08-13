$(function () {

    $("#exit").click(function (e){
        e.preventDefault()
        $("#logoutForm").submit()
        e.stopImmediatePropagation()
    })

    $("#edit-button").click(function (e) {
        e.preventDefault();
        $("#profile-head").first().hide()
        $("#head-edit").show()
        $("html, body").animate({"scrollTop": "0"}, 500, 'swing')
        $("#profile-body span").first().hide()
    })

    $("#head-edit .social div").click(function (e) {
        e.preventDefault()
        let div = $(this)
        $("#modal").css("display", "flex")
        $(".backdrop").css({
            "left": "0",
            "opacity": "1"
        })

        let modalSpan = $("#modal-span")
        let modalInput = $("#link")
        let href = $(this).find("a").attr("href")
        switch ($(this).attr("class")) {
            case "fb-but":
                modalSpan.text("Змінити посилання на Facebook");
                break;
            case "inst-but":
                modalSpan.text("Змінити посилання на Instagram");
                break;
            case "tw-but":
                modalSpan.text("Змінити посилання на Twitter");
                break;
            case "li-but":
                modalSpan.text("Змінити посилання на LinkedIn");
                break;
        }
        modalInput.val(href)

        $("#save").click(function () {
            if (modalInput.val() !== href) {
                div.find("input").val(modalInput.val())
                div.find("a").attr("href", modalInput.val())
            }
            $("#modal").css("display", "none")
            $(".backdrop").css({
                "left": "-100%",
                "opacity": "0"
            })
        })
    })


    $("#head-edit span.city").click(function (e) {
        e.preventDefault()
        let div = $(this)
        $("#modal").css("display", "flex")
        $(".backdrop").css({
            "left": "0",
            "opacity": "1"
        })
        $("#modal-span").hide()
        $("#link").hide()
        $("#modal #cityModal").css("display", "block")
        list("#modal #cityModal .country .country-label ")

        let string = ""
        $("#save").click(function () {
            $("#modal input").each(function () {
                if ($(this).val() !== "" && $(this).attr("id").startsWith("city"))
                    string = string + $(this).val() + "$"
            })
            $("#head-edit span.city input").val(string)

            $("#modal").css("display", "none")
            $(".backdrop").css({
                "left": "-100%",
                "opacity": "0"
            })
        })
    })


    $("#modal").on('click', ".accept.add", function (e) {
        e.preventDefault()
        let added = $(this).closest(".accept.add");
        let i = 1;
        let country = added.prevAll(".country").first()
        country.find("input").each(function (index, value) {
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
            let clone = country.clone(false);
            let li = country.find("input").last().val()
            clone.find(".select li:contains('" + li + "')").remove()
            added.before(clone);
            country.nextAll(".country").first().find(".country-label").each(function () {
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
        list("")
        e.stopImmediatePropagation();
        return false;
    })


    $(".buttons a[href='edit']").click(function (e) {
        e.preventDefault();
        let block = $(this).parent().parent()
        block.next().css("display", "flex");
        block.hide();
        $(".buttons a[href='accept']").click(function (e) {
            e.preventDefault()
            block.next().find("input").each(function () {
                $(this).attr("form", "vacancyEdit")
            })
            block.next().find("textarea").each(function () {
                $(this).attr("form", "vacancyEdit")
            })
            localStorage.setItem("scrollPos", $(window).scrollTop())
            $("#vacancyEdit").submit()
        })
        $(".buttons a[href='delete']").click(function (e) {
            e.preventDefault()
            block.next().css("display", "none");
            block.css("display", "flex");
        })
    })

    $("span#city-span").click(function () {
        let div = $(this)
        $("#modal").css("display", "flex")
        $(".backdrop").css({
            "left": "0",
            "opacity": "1"
        })
        $("#modal-span").hide()
        $("#link").hide()
        $("#modal #cityModal").css("display", "block")
        list("#modal #cityModal .country .country-label ")

        let string = ""
        $("#save").click(function () {
            $("#modal input").each(function () {
                if ($(this).val() !== "" && $(this).attr("id").startsWith("city"))
                    string = string + $(this).val() + "$"
            })
            div.find("input").val(string)

            $("#modal").css("display", "none")
            $(".backdrop").css({
                "left": "-100%",
                "opacity": "0"
            })
        })
    })


    $(".backdrop").click(function () {
        $("#modal").css("display", "none")
        $(".backdrop").css({
            "left": "-100%",
            "opacity": "0"
        })
    })
})

function photoForm(){
    $("#photoForm").submit()
}





