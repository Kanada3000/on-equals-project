/**
 * @license Copyright (c) 2003-2021, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see https://ckeditor.com/legal/ckeditor-oss-license
 */

CKEDITOR.editorConfig = function (config) {
    // Define changes to default configuration here. For example:
    // config.language = 'fr';
    // config.uiColor = '#AADC6E';

    config.contentsCss = '/css/pages-generator.css';

    config.extraPlugins = 'youtube';
    config.youtube_width = '640';
    config.youtube_height = '480';
    // config.youtube_responsive = true;

    config.filebrowserUploadMethod = 'form';
    config.image_previewText = ' ';

    config.fileTools_requestHeaders = {
        'X-Frame-Options': 'SAMEORIGIN',
    };
    config.enterMode = 2;
    config.enterMode = CKEDITOR.ENTER_BR;
    config.shiftEnterMode = CKEDITOR.ENTER_P;
    config.autoParagraph = false;
};
