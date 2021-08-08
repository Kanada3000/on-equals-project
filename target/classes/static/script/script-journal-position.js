$(function () {
    let scroll = localStorage.getItem("scrollPos")
    if (scroll !== null) {
        $(window).scrollTop(scroll)
    }
    setTimeout(function () {
        localStorage.setItem("scrollPos", "0")
    }, 200);
});

function setPosition(){
    localStorage.setItem("scrollPos", $(window).scrollTop())
}


