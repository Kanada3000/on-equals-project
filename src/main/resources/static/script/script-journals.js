$(function () {
    $("#questions .button").click(function (){
        let button = $(this)
        let span = $(this).parent().find("span")
        if(span.css("display") === "none"){
            span.show(function (){
                span.css("display","block")
            });

            $({deg: 0}).animate({deg: 45}, {
                duration: 300,
                step: function(now) {
                    button.css({
                        transform: 'rotate(' + now + 'deg)'
                    });
                }
            });
        } else {
            span.hide(200)
            $({deg: 45}).animate({deg: 0}, {
                duration: 300,
                step: function(now) {
                    button.css({
                        transform: 'rotate(' + now + 'deg)'
                    });
                }
            });
        }
    })
})



