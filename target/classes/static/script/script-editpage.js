$(function () {
    CKEDITOR.instances.editor.setData(longData)
    if (shortData !== null)
        CKEDITOR.instances.editor2.setData(shortData)
})