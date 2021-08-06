$(function () {
    $("#admin #body").on('click', 'td.edit-page', function () {
        window.location.href = ("/admin/page/edit/" + $(this).parent().find("td.id").text());
    })

    $("#admin #body").on('click', 'td.edit-story', function () {
        window.location.href = ("/admin/story/edit/" + $(this).parent().find("td.id").text());
    })

    $("#admin #body").on('click', 'td.edit-sticker', function () {
        window.location.href = ("/admin/sticker/edit/" + $(this).parent().find("td.id").text());
    })

    $("#admin #body").on("click", "a.first.page", function (e){
        e.preventDefault();
        window.location.href = "/admin/page/create";
        e.stopImmediatePropagation();
    })

    $("#admin #body").on("click", "a.first.story", function (e){
        e.preventDefault();
        window.location.href = "/admin/story/create";
        e.stopImmediatePropagation();
    })

    $("#admin #body").on("click", "a.first.sticker", function (e){
        e.preventDefault();
        window.location.href = "/admin/sticker/create";
        e.stopImmediatePropagation();
    })

    $("#admin #body").on("click", "a.first.career", function (e){
        e.preventDefault();
        window.location.href = "/admin/career/create";
        e.stopImmediatePropagation();
    })
})





