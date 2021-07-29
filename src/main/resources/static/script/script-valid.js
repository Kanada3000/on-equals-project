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
        "correctNumber",
        function (value, element) {
            return /^[1-9][0-9]*$/.test(value)
        }
    );

    // $.validator.addMethod(
    //     'filesize',
    //     function (value, element, param) {
    //         return this.optional(element) || (element.files[0].size <= param * 1048576)
    //     }, 'Файл не повинен перевищувати {0}Mb');


    // $.validator.addMethod(
    //     "specSymbols",
    //     function (value) {
    //         var i = value.length,
    //             aRet = [];
    //
    //         while (i--) {
    //             var iC = value[i].charCodeAt();
    //             if (iC < 65 || iC > 127 || (iC>90 && iC<97)) {
    //                 aRet[i] = '&#'+iC+';';
    //             } else {
    //                 aRet[i] = value[i];
    //             }
    //         }
    //         return aRet.join('');
    //     });


    $("#form-reg-employer").submit(function (e) {
        if($("#description").length){
            let val  = $("#description").val()
            val = val.replace(/\(/g, "&#40;");
            val = val.replace(/\)+/g, '&#41;');
            val = val.replace(/\.+/g, '&#46;');
            val = val.replace(/:+/g, '&#58;');
            $("#description").val(val)
        }
        if($("#story").length){
            let val  = $("#story").val()
            val = val.replace(/\(/g, "&#40;");
            val = val.replace(/\)+/g, '&#41;');
            val = val.replace(/\.+/g, '&#46;');
            val = val.replace(/:+/g, '&#58;');
            $("#story").val(val)
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
            'type+': {
                required: true
            },
            'type++': {
                required: true
            },
            'type+++': {
                required: true
            },
            citString: {
                required: true,
                inTheListCity: true,
            },
            description: {
                required: true,
                maxlength: 1000,
            },
            story: {
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
            accept1: {
                required: true,
            },
            accept2: {
                required: true,
            }
        },
        messages: {
            category: {
                required: "Це поле обов'язкове для заповнення",
                inTheListCat: "Оберіть значення зі списку",
            },
            'type': {
                required: "Це поле обов'язкове для заповнення"
            },
            'type+': {
                required: "Це поле обов'язкове для заповнення"
            },
            'type++': {
                required: "Це поле обов'язкове для заповнення"
            },
            'type+++': {
                required: "Це поле обов'язкове для заповнення"
            },
            citString: {
                required: "Це поле обов'язкове для заповнення",
                inTheListCity: "Оберіть значення зі списку",
            },
            description: {
                required: "Це поле обов'язкове для заповнення",
                maxlength: "Це поле має містит до 1000 знаків",
            },
            story: {
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

    $("#sign-up").submit(function (e) {
    }).validate({
        rules: {
            name: {
                required: true,
                maxlength: 100,
            },
            username: {
                required: true,
                email: true,
            },
            password: {
                required: true,
                minlength: 8,
            },
            passwordrep: {
                equalTo: $("input[name=password]")
            }
        },
        messages: {
            name: {
                required: "Поле 'Ім'я' обов'язкове для заповнення",
                maxlength: "Поле 'Ім'я' має містити не більше 100 символів",
            },
            username: {
                required: "Поле 'Ел. пошта' обов'язкове для заповнення",
                email: "Введіть коректну назву пошти",
            },
            password: {
                required: "Поле 'Пароль' обов'язкове для заповнення",
                minlength: "Пароль має містити не менше 8 символів",
            },
            passwordrep: {
                equalTo: "Паролі не співпадають"
            }
        },
        ignore: '',
        errorLabelContainer: '#invalid',
    });
});



