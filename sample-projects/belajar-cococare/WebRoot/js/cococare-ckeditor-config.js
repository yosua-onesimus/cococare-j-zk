CKEDITOR.editorConfig = function(config) {
    config.toolbar = "MyToolbar";
    config.toolbar_MyToolbar = [
        ["Source"],
        ["SpecialChar", "Smiley", "Table", "Image", "-", "Link", "Unlink"],
        ["Bold", "Italic", "Underline", "Strike", "-", "Subscript", "Superscript"],
        ["Font", "FontSize", "-", "TextColor", "BGColor"],
        ["NumberedList", "BulletedList", "-", "JustifyLeft", "JustifyCenter", "JustifyRight", "JustifyBlock"]
    ];
};