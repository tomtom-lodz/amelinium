<div id="configuration_div" style="width: 400px; margin-left: 150px"></div>
<div id="chart_div" style="width: 900px;"></div>
<h3 style="margin-left: 150px">Roadmap</h3>
<div id="roadmap_div" style="width: 600px; margin-left: 150px"></div>
<p style="margin-left: 150px">Summary</p>
<div id="burned_div" style="width: 180px; margin-left: 150px"></div>
<div id="cumulative_div" style="width: 600px; margin-left: 150px"></div>
<p style="margin-left: 150px">Autocorrection log</p>
<div id="log_div" style="width: 600px; margin-left: 150px"></div>

<script type="text/javascript">
	google.setOnLoadCallback(drawConfigurationTable, true);
	google.setOnLoadCallback(drawChart, true);
	google.setOnLoadCallback(drawRoadmap, true);
	google.setOnLoadCallback(drawBurnedTable, true);
	google.setOnLoadCallback(drawCumulativeTable, true);
	google.setOnLoadCallback(drawLogTable, true);

	function drawConfigurationTable() {
		var data = google.visualization
				.arrayToDataTable([
						[ 'Configuration', ' ' ],
						[ 'Current sprint (number)', '15' ],
						[ 'Sprint length (days)', '7' ],
						[ 'Zeroeth sprint (date)', '1 Jun 2012' ],
						[
								'Compute ideal (number (0="all", 5="last 5") or false)',
								'5' ],
						[ 'Extend ideal (true, false)', 'false' ],
						[ 'Add missing feature groups to chart (true, false)',
								'false' ],
						[
								'Add missing feature groups to roadmap (true, false)',
								'false' ], ]);

		var table = new google.visualization.Table(document
				.getElementById('configuration_div'));
		table.draw(data, {
			showRowNumber : false
		});
	}

	function drawRoadmap() {
		var data = google.visualization.arrayToDataTable([
				[ 'Feature Group', 'Sprint', 'End of development',
						'Deployment to production' ],
				[ 'A', 23, '8 Jun 2012', 'not planned' ],
				[ 'B', 27, '3 Aug 2012', 'not planned' ],
				[ 'C', 31, '28 Sep 2012', 'not planned' ] ]);

		var table = new google.visualization.Table(document
				.getElementById('roadmap_div'));
		table.draw(data, {
			showRowNumber : false
		});
	}

	function drawBurnedTable() {
		var data = google.visualization.arrayToDataTable([ [
				'Burned story points', 252 ] ]);

		var table = new google.visualization.Table(document
				.getElementById('burned_div'));
		table.draw(data, {
			showRowNumber : false
		});
	}

	function drawCumulativeTable() {
		var data = google.visualization.arrayToDataTable([
				[ 'Feature Group', 'Story Points' ], [ 'Previous', 145 ],
				[ '-g1-', 150 ],
				[ '-g2-', 158 ],
				[ '-g3-', 159 ],
				[ 'AAA', 239 ], [ 'BBB', 310 ], [ 'CCC', 357 ],
				[ 'g4', 360 ], [ 'g5', 367 ],
				[ 'g6', 382 ],
				[ 'g7', 391 ],
				[ 'g8', 395 ], [ 'g9', 399 ],
				[ 'g10', 410 ]

		]);

		var table = new google.visualization.Table(document
				.getElementById('cumulative_div'));
		table.draw(data, {
			showRowNumber : false
		});
	}

	function drawLogTable() {
		var data = google.visualization.arrayToDataTable([
				[ 'Table', 'matched?', 'corrected?',
						'Feature Group from backlog' ],
				[ 'AAA', true, false, 'AAA - ' ],
				[ 'BBB', true, true, 'BBB - ' ],
				[ 'CCC', true, true, 'CCC - ' ] ]);

		var table = new google.visualization.Table(document
				.getElementById('log_div'));
		table.draw(data, {
			showRowNumber : false
		});
	}

	function drawChart() {
		var data = google.visualization.arrayToDataTable([
				[ ' ', 'burned story points', 'AAA', 'BBB', 'CCC', 'ideal' ],
				[ 0.0, 0.0, null, null, null, null ],
				[ 1.0, 15.0, null, null, null, null ],
				[ 2.0, 34.0, null, null, null, null ],
				[ 3.0, 52.0, null, null, null, null ],
				[ 4.0, 64.0, null, null, null, null ],
				[ 5.0, 74.0, null, null, null, null ],
				[ 6.0, 75.0, null, null, null, null ],
				[ 7.0, 90.0, null, null, null, null ],
				[ 8.0, 98.0, null, null, null, null ],
				[ 9.0, 114.0, null, null, null, null ],
				[ 10.0, 120.0, null, null, null, null ],
				[ 11.0, 133.0, null, null, null, null ],
				[ 12.0, 138.0, null, null, null, null ],
				[ 13.0, 148.0, null, null, null, null ],
				[ 14.0, 148.0, null, null, null, null ],
				[ 15.0, 162.0, null, null, null, null ],
				[ 16.0, 176.0, null, null, null, null ],
				[ 17.0, 190.0, null, null, null, null ],
				[ 18.0, 204.0, null, null, null, null ],
				[ 19.0, 213.0, null, null, null, null ],
				[ 20.0, 217.0, null, null, null, null ],
				[ 21.0, 226.0, null, null, null, null ],
				[ 22.0, 236.0, null, null, null, null ],
				[ 23.0, 252.0, null, null, null, null ],
				[ 25.0, 252.0, null, null, null, null ],
				[ 0.0, null, 220.0, null, null, null ],
				[ 17.0, null, 220.0, null, null, null ],
				[ 18.0, null, 227.0, null, null, null ],
				[ 21.0, null, 227.0, null, null, null ],
				[ 22.0, null, 239.0, null, null, null ],
				[ 23.0, null, 239.0, null, null, null ],
				[ 23.0, null, 0.0, null, null, null ],
				[ 0.0, null, null, 250.0, null, null ],
				[ 21.0, null, null, 250.0, null, null ],
				[ 22.0, null, null, 277.0, null, null ],
				[ 23.0, null, null, 290.0, null, null ],
				[ 24.0, null, null, 289.0, null, null ],
				[ 25.0, null, null, 310.0, null, null ],
				[ 29.0, null, null, 310.0, null, null ],
				[ 29.0, null, null, 0.0, null, null ],
				[ 0.0, null, null, null, 297.0, null ],
				[ 21.0, null, null, null, 297.0, null ],
				[ 22.0, null, null, null, 324.0, null ],
				[ 23.0, null, null, null, 337.0, null ],
				[ 24.0, null, null, null, 336.0, null ],
				[ 25.0, null, null, null, 357.0, null ],
				[ 33.0, null, null, null, 357.0, null ],
				[ 33.0, null, null, null, 0.0, null ],
				[ 0.0, null, null, null, null, 0.0 ],
				[ 34.0, null, null, null, null, 372.0 ]

		]);

		var options = {
			title : 'ACME',
			curveType : 'none',
			width : 1000,
			height : 600,
			vAxis : {
				maxValue : 10,
				gridlines : {
					count : 15
				}
			},
			hAxis : {
				maxValue : 10,
				gridlines : {
					count : 20
				}
			},
			backgroundColor : {
				fill : 'transparent'
			}
		};

		var chart = new google.visualization.LineChart(document
				.getElementById('chart_div'));
		chart.draw(data, options);
	}
</script>