CKEDITOR.editorConfig = function( config )
{
        config.templates_files = [ '/ckplug/js/ckeditor/templates/template.js' ];
        config.height='600px';
        config.width='100%';
        config.resize_enabled = false;
        config.toolbar_custom = [
        ['Styles','Format','Font','FontSize','TextColor','BGColor','Maximize'],
        ['Source'],
        ['Bold','Italic','Underline','Strike','-','Subscript','Superscript','-','SpellChecker', 'Scayt'],
        ['Table','HorizontalRule'],
        ['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'],
        ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock','Link','Unlink','Anchor','SpecialChar']
        ]
        config.toolbar_empty = [['Source']]
};