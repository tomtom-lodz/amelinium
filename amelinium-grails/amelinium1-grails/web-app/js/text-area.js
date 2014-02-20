function jqUpdateSize(){
    var height = $(window).height();
	var ed = tinymce.activeEditor;
	var width;
	var heightT;
    if($('#text-area').height()<300)
    {
    	heightT = '300';
    	width = '100%';
    }
    else
    {
    	heightT = height - 360;
    	width = '100%';
    }
    ed.theme.resizeTo(width, heightT);
};
$(document).ready(jqUpdateSize);
$(window).load(jqUpdateSize);
$(window).resize(jqUpdateSize);