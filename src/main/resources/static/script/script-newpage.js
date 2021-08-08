$(function () {
    CKEDITOR.replace('editor', {
        filebrowserImageUploadUrl: '/image/upload?_csrf=' + $('meta[name=csrf-token]').attr("content"),
        height: 400,
    });

    CKEDITOR.replace('editor2', {
        filebrowserImageUploadUrl: '/image/upload?_csrf=' + $('meta[name=csrf-token]').attr("content"),
        height: 200,
    });

    CKEDITOR.on("dialogDefinition", function (e) {
        var dialogName = e.data.name
        var dialogDefinition = e.data.definition

        switch (dialogName) {
            case 'image':
                dialogDefinition.removeContents('Link')
                dialogDefinition.removeContents('advanced')
                break;
        }
    })

    $("#publish").click(function () {
        $("#data").val(CKEDITOR.instances.editor.getData())
        $("#shortData").val(CKEDITOR.instances.editor2.getData())
        $("#pageForm").submit();
    })
})