$(function () {
    $.validator.addMethod(
        "inTheListCat",
        function (value, element) {
            return (jQuery.inArray(value, catList) !== -1);
        });

    $.validator.addMethod(
        "inTheListCity",
        function (value, element) {
            return (jQuery.inArray(value, cityList) !== -1);
        });

    $.validator.addMethod(
        "inTheListUser",
        function (value, element) {
            return (jQuery.inArray(value, userList) !== -1);
        });

    $.validator.addMethod(
        "correctNumber",
        function (value, element) {
            return /^[1-9][0-9]*$/.test(value)
        }
    );

    $("#editForm").submit(function (e) {
        if ($("#description").length) {
            let val = $("#description").val()
            val = val.replace(/\(/g, "&#40;");
            val = val.replace(/\)+/g, '&#41;');
            val = val.replace(/\.+/g, '&#46;');
            val = val.replace(/:+/g, '&#58;');
            $("#description").val(val)
        }
    }).validate({
        rules: {
            category: {
                required: true,
                inTheListCat: true,
            },
            'type': {
                required: true
            },
            'user': {
                required: true,
                inTheListUser: true,
            },
            city: {
                required: true,
                inTheListCity: true,
            },
            description: {
                required: true,
                maxlength: 1000,
            },
            salary: {
                required: true,
                number: true,
                correctNumber: true,
            },
            name: {
                required: true,
            },
            email: {
                required: true,
                email: true,
            },
            site: {
                url: true,
            },
            linkFacebook: {
                url: true,
            },
            linkInstagram: {
                url: true,
            },
            linkTwitter: {
                url: true,
            },
            linkLinkedIn: {
                url: true,
            },
            age: {
                required: true,
            },
            quantity: {
                required: true,
            },
            size: {
                required: true,
            },
        },
        messages: {
            category: {
                required: "Це поле обов'язкове для заповнення",
                inTheListCat: "Оберіть значення зі списку",
            },
            'type': {
                required: "Це поле обов'язкове для заповнення"
            },
            city: {
                required: "Це поле обов'язкове для заповнення",
                inTheListCity: "Оберіть значення зі списку",
            },
            'user': {
                required: "Це поле обов'язкове для заповнення",
                inTheListUser: "Оберіть значення зі списку",
            },
            description: {
                required: "Це поле обов'язкове для заповнення",
                maxlength: "Це поле має містит до 1000 знаків",
            },
            salary: {
                required: "Це поле обов'язкове для заповнення",
                number: "Необхідно вказати число",
                correctNumber: "Число не повинне починатися з 0",
            },
            name: {
                required: "Це поле обов'язкове для заповнення",
            },
            email: {
                required: "Це поле обов'язкове для заповнення",
                email: "Введіть коректну назву пошти",
            },
            site: {
                url: "Вставте коректний лінк",
            },
            linkFacebook: {
                url: "Вставте коректний лінк",
            },
            linkInstagram: {
                url: "Вставте коректний лінк",
            },
            linkTwitter: {
                url: "Вставте коректний лінк",
            },
            linkLinkedIn: {
                url: "Вставте коректний лінк",
            },
            age: {
                required: "Це поле обов'язкове для заповнення",
            },
            quantity: {
                required: "Це поле обов'язкове для заповнення",
            },
            size: {
                required: "Це поле обов'язкове для заповнення",
            },
            accept1: {
                required: "Підтвердіть згоду",
            },
            accept2: {
                required: "Підтвердіть згоду",
            }
        },
        ignore: '',
        errorPlacement: function (error, element) {
            if (element.parent().hasClass("list")) {
                if (element.parent().parent().hasClass("country-label")) {
                    error.insertAfter(element.parent().parent());
                } else error.insertAfter(element.parent());
            } else if (element.is(":radio")) {
                error.insertAfter(element.parent())
            } else if (element.parent().hasClass("label-inner")) {
                error.insertAfter(element.parent());
            } else if (element.parent().hasClass("accept")) {
                error.insertAfter(element.next())
            } else {
                error.insertAfter(element);
            }
        }
    });
});



