$('.button-podkresl')
		.click(
				function() {
					var markArea = $('#text-area').get(0);
					var startStr = markArea.value.substring(0,
							markArea.selectionStart);
					var oldStr = markArea.value.substring(
							markArea.selectionStart, markArea.selectionEnd);
					var endStr = markArea.value
							.substring(markArea.selectionEnd);
					var stories = oldStr.split("\n");
					var newStories = "";
					var index = 0;
					stories.forEach(function(entry) {
						index += 1;
						if (entry.substring(0, 1) == "*") {
							if (index < stories.length)
								newStories += "* -" + entry.substring(2)
										+ "-\n";
							else
								newStories += "* -" + entry.substring(2) + "-";
						} else {
							if (index < stories.length)
								newStories += "-" + entry + "-\n";
							else
								newStories += "-" + entry + "-";
						}
					});
					$('#text-area').val(startStr + newStories + endStr);
				});

$('.button-lista')
		.click(
				function() {
					var markArea = $('#text-area').get(0);
					var startStr = markArea.value.substring(0,
							markArea.selectionStart);
					var oldStr = markArea.value.substring(
							markArea.selectionStart, markArea.selectionEnd);
					var endStr = markArea.value
							.substring(markArea.selectionEnd);
					var stories = oldStr.split("\n");
					var newStories = "";
					var index = 0;
					stories.forEach(function(entry) {
						index += 1;
						if (entry.substring(0, 2) == "* ")
							;
						else {
							if (index < stories.length)
								newStories += "* " + entry + "\n";
							else
								newStories += "* " + entry;
						}
					});
					$('#text-area').val(startStr + newStories + endStr);
				});

$('.header-button').click(function(e) {
	var header = e.target.innerHTML
	var markArea = $('#text-area').get(0);
	var startStr = markArea.value.substring(0, markArea.selectionStart);
	var restStr = markArea.value.substring(markArea.selectionStart);
	switch (header){
		case "H1":
			$('#text-area').val(startStr + "h1. " + restStr);
			break;
		case "H2":
			$('#text-area').val(startStr + "h2. " + restStr);
			break;
		case "H3":
			$('#text-area').val(startStr + "h3. " + restStr);
			break;
		case "H4":
			$('#text-area').val(startStr + "h4. " + restStr);
			break;
		case "H5":
			$('#text-area').val(startStr + "h5. " + restStr);
			break;
	}
	$('#text-area').setSelectionRange(3, 3);
});

$(function() {
	var myButtons = { Close: function() { $(this).dialog('close'); }  };
	$("#dialog").dialog({
		autoOpen: false,
		width: 400,
		height: 600,
		draggable: false,
		resizable: false,
		dialogClass: 'noTitleStuff',
		buttons: myButtons
	});
	$("#help-dialog").on("click", function() {
		$("#dialog").dialog("open");
	});
});