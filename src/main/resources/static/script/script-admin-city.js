$(function () {

    $("#admin #body").on('click', 'a.addCity', function (e) {
        e.preventDefault()
        let clone = $(this).prev().clone(false)
        let i = 1
        clone.find("input").each(function (index, value) {
            let input = $(this);
            if (input.val() === "") {
                i = 0;
            } else if (index === 1 && jQuery.inArray(input.val(), cityList) === -1) {
                i = 0;
            }
        });

        if (i === 1) {
            let li = clone.prev().find("input").val()
            clone.find(".select li:contains('" + li + "')").remove()
            clone.find("input").val("")
            clone.find("label").attr("for", $(this).find("label").attr("for") + "-")
            clone.attr("id", $(this).find(".list").attr("id") + "-")
            clone.find("input").attr("id", $(this).find(".list input").attr("id") + "-")
            clone.find(".select").attr("id", $(this).find(".select").attr("id") + "-")
            $(this).before(clone)
            list("")
        }

        e.stopImmediatePropagation();
        return false
    })
})






