function jqUpdateSizeCsv(){
    var height = $(window).height();
    $('#text-area').css('height', height-300);
    if($('#text-area').height()<300)
    {
    	$('#text-area').css('height', 300);
    }

};
$(document).ready(jqUpdateSizeCsv);
$(window).resize(jqUpdateSizeCsv);