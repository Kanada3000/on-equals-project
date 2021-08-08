$(function () {
    let size
    $("#download").change(function () {
            if ($("#download").val() !== "") {
                $("#footer-reg .file").css("display", "flex");
                $("#footer-reg .file span.file-name").text($(this).val().split('\\').pop())
                size = this.files[0].size
                $("#size").val(size)
                $("#footer-reg .file span.file-size").text((size / 1024).toFixed(2) + "КБ")
                $(this).parent().css("display", "none")
                $("#footer-reg .download.file-resume").css("display", "flex")
            }

        }
    )

    // $("#footer-reg .download.file-resume").click(function () {
    //     if ($("#download").val().split('.').pop().toLowerCase() === "pdf") {
    //         if (size / 1048576 < 10) {
    //             $("#formResume").submit();
    //         } else if (!$("#error2").length) {
    //             $("#invalid").append("<label id='error2' class='error'>Розмір перевищує ліміт (10МБ)</label>")
    //         }
    //     } else if (!$("#error").length) {
    //         $("#invalid").append("<label id='error' class='error'>Файл має бути в форматі pdf</label>")
    //     }
    //     return false
    // })

    $("#footer-reg .file .cancel").click(function () {
        $("#footer-reg .file").css("display", "none");
        $("#download").parent().css("display", "flex")
        $("#footer-reg .download.file-resume").css("display", "none")

        $("#error").remove()
        $("#error2").remove()
        $("#formResume").reset()
    })

    $("#profile-body .file .cancel").click(function (e) {
        e.preventDefault()
        if (!$(this).hasClass("download")) {
            $(this).parent().find("input").attr("form", "formFile")
            $("#formFile").submit();
        } else {
            $(this).parent().find("input").attr("form", "formDownload")
            $("#formDownload").submit();
        }
        e.stopImmediatePropagation()
    })

    $("#formResume button").click(function () {
        $("#form-type").val("file")
    })

});



