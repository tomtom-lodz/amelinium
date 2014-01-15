function jqUpdateSize(){
    var height = $(window).height();
    $('#text-area').css('height', height-320);
    if($('#text-area').height()<300)
    {
    	$('#text-area').css('height', 300);
    }
  //  $(window).
};
$(document).ready(jqUpdateSize);
$(window).resize(jqUpdateSize);