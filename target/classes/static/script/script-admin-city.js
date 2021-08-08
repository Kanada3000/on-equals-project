$(function () {

    $("#admin #body").on('click', 'a.addCity', function (e) {
        e.preventDefault()
        let clone = $(this).prev().clone(false)
        let prevId = $(this).prev().attr("id")
        let prevInputId = $(this).prev().find("input").attr("id")
        let prevSelectId = $(this).prev().find(".select").attr("id")
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
            clone.attr("id", prevId + "-")
            clone.find("input").attr("id", prevInputId + "-")
            clone.find(".select").attr("id", prevSelectId + "-")
            $(this).before(clone)
            list("")
        }

        e.stopImmediatePropagation();
        return false
    })
})

function addCityfunc($this, $val) {
    let clone = $this.prev().clone(false)
    let prevId = $this.prev().attr("id")
    let prevInputId = $this.prev().find("input").attr("id")
    let prevSelectId = $this.prev().find(".select").attr("id")
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
        clone.find("label").attr("for", $(this).find("label").attr("for") + "-")
        clone.attr("id", prevId + "-")
        clone.find("input").attr("id", prevInputId + "-")
        clone.find(".select").attr("id", prevSelectId + "-")
        clone.find("input#" + prevInputId + "-").val($val)
        clone.find("input#" + prevInputId + "-").attr("value", $val)
        $this.before(clone)
        list("")
    }
}






