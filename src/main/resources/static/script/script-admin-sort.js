$(function () {

    if (sortVal === "id") {
        $("tr.head td.id-head").addClass("sort")
    } else {
        $("tr.head td." + sortVal + "-sort").addClass("sort")
    }

    $("tr.head td").click(function () {
        if (!$(this).hasClass("nosort")) {
            let $class = $(this).attr("class")
            let sort = $class.substring(0, $class.indexOf("-"))

            $("#sort").val(sort)
            $("#sortAdminForm").submit()
        }
    })
})






