function repaint(){
	document.body.style.backgroundColor = 'rgb(251,251,251)';
	$('HTML').css('background-color','rgb(251,251,251)');
	document.getElementsByClassName('breadcrumb')[0].style.background = 'rgb(251,251,251)';
}
$(document).ready(repaint);
$(window).resize(repaint);