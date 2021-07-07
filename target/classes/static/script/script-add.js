$(function () {
    let clone = $("#form-reg-employer").clone()
    clone.unwrap()

    $("#reg2_sub .submit .add-vacancy").click(function (e) {
        e.preventDefault();
        // $("#form-reg-employer").validate()
        let last = $("#form-reg-employer").children().last()
        let hr = document.createElement('hr')
        hr.classList.add('delimiter')
        last.after(hr)
        clone.insertAfter(hr)
        e.stopImmediatePropagation()
    })

});



