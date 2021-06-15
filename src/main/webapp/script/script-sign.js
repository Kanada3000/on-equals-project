$(function () {

    $("#sign .pass .image").click(function(){
        let type = $(this).prev().attr("type") === 'password' ? 'text': 'password'
        $(this).prev().attr("type",type)

        let eye = $(this).find($("img")).attr("src") === 'images/eye-open.png' ? 'images/eye-close.png' : "images/eye-open.png"
        $(this).find($("img")).attr("src", eye)
    })

});


