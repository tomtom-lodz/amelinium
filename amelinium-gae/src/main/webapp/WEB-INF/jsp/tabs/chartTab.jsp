<div id="chart_tab_content" >
	<div id="top_div" style="width: 900px; margin-left: 870px">
	<input type="button" value="Edit" onclick='$("#chart_tab_content").load("${pageContext.request.contextPath}/planning/chart/edit");'></input></div>
	<div id="configuration_div" style="width: 400px; margin-left: 150px"></div>
	<div id="chart_div" style="width: 900px;"></div>
	<h3 style="margin-left: 150px">Roadmap</h3>
	<div id="roadmap_div" style="width: 600px; margin-left: 150px"></div>
	<div id="burned_div" style="width: 180px;  margin-left: 150px"></div>
	<p style="margin-left: 150px">Autocorrection log</p>
	<div id="log_div" style="width: 600px;  margin-left: 150px"></div>
</div>
<script type="text/javascript">
google.setOnLoadCallback(drawConfigurationTable,true);
google.setOnLoadCallback(drawChart,true);
google.setOnLoadCallback(drawRoadmap,true);
google.setOnLoadCallback(drawLogTable,true);
function drawConfigurationTable() {
var data = google.visualization.arrayToDataTable([
[ 'Configuration' , ' '],
[ 'Current sprint (number)' , '14' ],
[ 'Sprint length (days)' , '7' ],
[ 'Zeroeth sprint (date)' , '23 Apr 2012' ],
[ 'Compute ideal (number (0="all", 5="last 5") or false)' , 'false' ],
[ 'Extend ideal (true, false)' , 'true' ],
[ 'Add missing feature groups to chart (true, false)' , 'false' ],
[ 'Add missing feature groups to roadmap (true, false)' , 'false' ],

]);

	        var table = new google.visualization.Table(document.getElementById('configuration_div'));
	        table.draw(data, {showRowNumber: false});
	      }
function drawChart() {
var data = google.visualization.arrayToDataTable([
[ ' ', 'burned story points', 'Olympics support', 'Group zero', 'End of developmnent group 1', 'Group one', 'Group two', 'Group three', 'Group four', 'ideal' ],
[ 0.0, 0.0, null, null, null, null, null, null, null, null ],
[ 3.0, 17.0, null, null, null, null, null, null, null, null ],
[ 6.0, 39.0, null, null, null, null, null, null, null, null ],
[ 9.0, 61.0, null, null, null, null, null, null, null, null ],
[ 10.0, 65.0, null, null, null, null, null, null, null, null ],
[ 11.0, 76.0, null, null, null, null, null, null, null, null ],
[ 12.0, 81.0, null, null, null, null, null, null, null, null ],
[ 13.0, 84.0, null, null, null, null, null, null, null, null ],
[ 14.0, 87.0, null, null, null, null, null, null, null, null ],
[ 12.0, null, 79.0, null, null, null, null, null, null, null ],
[ 13.0, null, 84.0, null, null, null, null, null, null, null ],
[ 13.0, null, 0.0, null, null, null, null, null, null, null ],
[ 12.0, null, null, 119.0, null, null, null, null, null, null ],
[ 13.0, null, null, 117.0, null, null, null, null, null, null ],
[ 14.0, null, null, 80.0, null, null, null, null, null, null ],
[ 13.0, null, null, 80.0, null, null, null, null, null, null ],
[ 13.0, null, null, 0.0, null, null, null, null, null, null ],
[ 26.0, null, null, null, 0.0, null, null, null, null, null ],
[ 26.0, null, null, null, 173.0, null, null, null, null, null ],
[ 0.0, null, null, null, null, 130.0, null, null, null, null ],
[ 10.0, null, null, null, null, 130.0, null, null, null, null ],
[ 11.0, null, null, null, null, 141.0, null, null, null, null ],
[ 12.0, null, null, null, null, 176.0, null, null, null, null ],
[ 13.0, null, null, null, null, 181.0, null, null, null, null ],
[ 14.0, null, null, null, null, 180.0, null, null, null, null ],
[ 28.0, null, null, null, null, 180.0, null, null, null, null ],
[ 28.0, null, null, null, null, 0.0, null, null, null, null ],
[ 0.0, null, null, null, null, null, 163.0, null, null, null ],
[ 10.0, null, null, null, null, null, 163.0, null, null, null ],
[ 11.0, null, null, null, null, null, 175.0, null, null, null ],
[ 12.0, null, null, null, null, null, 210.0, null, null, null ],
[ 13.0, null, null, null, null, null, 214.0, null, null, null ],
[ 14.0, null, null, null, null, null, 213.0, null, null, null ],
[ 33.0, null, null, null, null, null, 213.0, null, null, null ],
[ 33.0, null, null, null, null, null, 0.0, null, null, null ],
[ 0.0, null, null, null, null, null, null, 178.0, null, null ],
[ 10.0, null, null, null, null, null, null, 178.0, null, null ],
[ 11.0, null, null, null, null, null, null, 190.0, null, null ],
[ 12.0, null, null, null, null, null, null, 225.0, null, null ],
[ 13.0, null, null, null, null, null, null, 229.0, null, null ],
[ 14.0, null, null, null, null, null, null, 228.0, null, null ],
[ 35.0, null, null, null, null, null, null, 228.0, null, null ],
[ 35.0, null, null, null, null, null, null, 0.0, null, null ],
[ 0.0, null, null, null, null, null, null, null, 203.0, null ],
[ 10.0, null, null, null, null, null, null, null, 203.0, null ],
[ 11.0, null, null, null, null, null, null, null, 215.0, null ],
[ 12.0, null, null, null, null, null, null, null, 250.0, null ],
[ 13.0, null, null, null, null, null, null, null, 254.0, null ],
[ 14.0, null, null, null, null, null, null, null, 253.0, null ],
[ 39.0, null, null, null, null, null, null, null, 253.0, null ],
[ 39.0, null, null, null, null, null, null, null, 0.0, null ],
[ 0.0, null, null, null, null, null, null, null, null, 0.0 ],
[ 40.0, null, null, null, null, null, null, null, null, 266.0 ]]);

    var options = {
      title: 'Bzura burnup chart',
curveType: 'none',
              width: 1000, height: 600,
              vAxis: {maxValue: 10, gridlines: {count: 15}},
       hAxis: {maxValue: 10, gridlines: {count: 20}},
       backgroundColor: { fill:'transparent' }
    };

    var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
    chart.draw(data, options);
  }
function drawRoadmap() {
var data = google.visualization.arrayToDataTable([
[ 'Feature Group' , 'Sprint' , 'End of development' , 'Deployment to production' ],
[ 'Group one' , 28 , '04 Nov 2012' , 'unknown' ],
[ 'Group two' , 33 , '09 Dec 2012' , 'unknown' ],
[ 'Group three' , 35 , '23 Dec 2012' , 'unknown' ],
[ 'Group four' , 39 , '20 Jan 2013' , 'unknown' ]
]);

    var table = new google.visualization.Table(document.getElementById('roadmap_div'));
    table.draw(data, {showRowNumber: false});
  }
function drawLogTable() {
var data = google.visualization.arrayToDataTable([
[ 'Table' , 'matched?' , 'corrected?' , 'Feature Group from backlog' ],
[ ' A ' , false , false , ' ' ],
[ ' Group zero ' , true , false , ' Group zero -- a -- ' ],
[ ' End of developmnent group 1 ' , false , false , ' ' ],
[ ' Group one ' , true , true , ' Group one -- b -- ' ],
[ ' Group two ' , true , true , ' Group two -- c -- ' ],
[ ' Group three ' , true , true , ' Group three -- d -- ' ],
[ ' Group four ' , true , true , ' Group four -- e -- ' ]	  ]);

	        var table = new google.visualization.Table(document.getElementById('log_div'));
	        table.draw(data, {showRowNumber: false});
	      }
</script>