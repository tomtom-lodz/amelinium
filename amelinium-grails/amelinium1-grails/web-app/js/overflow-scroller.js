function updateScrollerSize()
{
	var height = $(window).height();
	$('.scroller').css('height',height-110);
}
$(document).ready(updateScrollerSize);
$(window).resize(updateScrollerSize);
$('html').css('overflow-y','hidden');