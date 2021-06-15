$(function () {
    let min = 0
    let max = 50000
    let step = 1000
    let min_val = min
    let max_val = max

    let slider_1 = document.getElementById("min");
    let output_1 = document.getElementById("min-output");
    slider_1.setAttribute("min", min)
    slider_1.setAttribute("max", max)
    slider_1.setAttribute("value", min)
    output_1.innerHTML = slider_1.value;
    let width = slider_1.offsetWidth
    let pos
    slider_1.setAttribute("step", step)
    output_1.setAttribute("style", "left: " + (((width - 40) * (min_val/(max+min))) + 20) + "px")

    let slider_2 = document.getElementById("max");
    let output_2 = document.getElementById("max-output");
    slider_2.setAttribute("min", min)
    slider_2.setAttribute("max", max)
    slider_2.setAttribute("value", max)
    output_2.innerHTML = slider_2.value;
    output_2.setAttribute("style", "left: " + (((width - 40) * (max_val/(max+min))) + 20) + "px")



    slider_1.oninput = function() {
        if(parseInt(this.value) >= parseInt(slider_2.value)){
            console.log("BEFORE ---- " + slider_2.value)
            slider_2.value = parseInt(this.value) + step
            console.log("AFTER ---- " + slider_2.value)
            output_2.innerHTML = slider_2.value;
            pos =  slider_2.value / (max - min)
            output_2.setAttribute("style", "left: " + (((width - 40) * pos) + 20) + "px")
        }
        output_1.innerHTML = this.value;
        pos =  this.value / (max - min)
        output_1.setAttribute("style", "left: " + (((width - 40) * pos) + 20) + "px")
    }

    slider_2.oninput = function() {
        if(parseInt(this.value) <= parseInt(slider_1.value)){
            slider_1.value = this.value - step
            output_1.innerHTML = slider_1.value;
            pos =  slider_1.value / (max - min)
            output_1.setAttribute("style", "left: " + (((width - 40) * pos) + 20) + "px")
        }
        output_2.innerHTML = this.value;
        pos =  this.value / (max - min)
        output_2.setAttribute("style", "left: " + (((width - 40) * pos) + 20) + "px")
    }


})






