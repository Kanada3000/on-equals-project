$(function () {

    $(".addCity").click(function (e){
        e.preventDefault()
        $("#cityList").show()
    })

    $("#cityList li").click(function (e){
        e.preventDefault()
        $("#cityList").last().before(
            "<div class='block'>" +
            "<span>" + $(this).text() + " &#10008;</span>" +
            "<input type='hidden' name='city' value='" + $(this).text().trim() + "'>" +
            "</div>"
        )
        $("#cityList").hide()
        $("#cityList input").val('')
    })

    $(".city-block").on("click",".block",function (){
        $(this).remove()
    })

    $("#accept").click(function (){
        if($("#editForm").valid)
            $("#editForm").submit()
        console.log("button")
    })
});



