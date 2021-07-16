$(function () {
    $("#body td.accept").click(function () {
        let i;
        if ($(this).hasClass("unapproved")) {
            i = "formUnapproved";
        } else i = "form";
        $(this).prev().attr("form", i)
        $(this).prev().attr("name", "path")
        $("#" + i).submit()
    })

    $("#body td.delete").click(function () {
        $(this).prev().prev().attr("form", "formDelete")
        $(this).prev().prev().attr("name", "path")
        $("#formDelete").submit()
    })

    $("#body td.file").click(function () {
        $(this).next().attr("form", "formDownload")
        $(this).next().attr("name", "path")
        $("#formDownload").submit()
    })
})



