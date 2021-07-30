$(function (){
    CKEDITOR.replace( 'editor', {
        filebrowserImageUploadUrl: '/image/upload?_csrf=' + $('meta[name=csrf-token]').attr("content"),
        height: 400,
    });

    CKEDITOR.on("dialogDefinition", function(e){
        let dialogName = e.data.name
        let dialogDefinition = e.data.definition

        switch (dialogName){
            case 'image':
                dialogDefinition.removeContents('info')
                dialogDefinition.removeContents('Link')
                dialogDefinition.removeContents('advanced')
                break;
        }
    })

    $("#publish").click(function(){
        alert(CKEDITOR.instances.editor.getData())
    })
})