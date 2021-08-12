$(function () {
    if (localStorage.getItem("alertMode") === "res") {
        localStorage.removeItem("alertMode")
        $("#alert").css("display", "flex")
        $("#alert .button").click(function (){
            $("#alert").css("display", "none")
        })
    } else if(localStorage.getItem("alertMode") === "vac") {
        localStorage.removeItem("alertMode")
        $("#alert").css("display", "flex")
        $("#alert .button").click(function (){
            $("#alert").css("display", "none")
        })
    }
});



