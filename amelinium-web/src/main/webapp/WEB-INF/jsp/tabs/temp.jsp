<div id="chart_tab_content" >
		<p style="margin-left: 150px; color: red"><b><i><c:out value="${message}" escapeXml="false" /></i></b></p>    
	<div id="configuration_div" style="width: 400px; margin-left: 150px"></div>
	<div id="chart_div" style="width: 900px;"></div>
	<h3 style="margin-left: 150px">Roadmap</h3>
	<div id="roadmap_div" style="width: 600px; margin-left: 150px"></div>
	<div id="burned_div" style="width: 180px;  margin-left: 150px"></div>
	<p style="margin-left: 150px">Autocorrection log</p>
	<div id="log_div" style="width: 600px;  margin-left: 150px"></div>
	<p style="margin-left: 150px"><i><c:out value="${project.chartModel.log.message}" escapeXml="false" /></i></p>    
</div>
<script type="text/javascript">
// google.load('visualization', '1', {packages:['table','corechart']});
google.setOnLoadCallback(drawConfigurationTable,true);
google.setOnLoadCallback(drawChart,true);
google.setOnLoadCallback(drawRoadmap,true);
// google.setOnLoadCallback(drawBurnedTable,true);
google.setOnLoadCallback(drawLogTable,true);
function drawConfigurationTable() {
var data = google.visualization.arrayToDataTable([
                                                  
<c:out value="${project.cache.jspChart.configuration}" escapeXml="false" />

	        var table = new google.visualization.Table(document.getElementById('configuration_div'));
	        table.draw(data, {showRowNumber: false});
	      }
function drawChart() {
var data = google.visualization.arrayToDataTable([
                                                  
<c:out value="${project.cache.jspChart.chart}" escapeXml="false" />

]);

    var options = {
     // title: 'aaa',
       title: <c:out value="${project.chartModel.chartTitle}" escapeXml="false" />,
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

<c:out value="${project.cache.jspChart.roadmap}" escapeXml="false" />
                                                  
]);

    var table = new google.visualization.Table(document.getElementById('roadmap_div'));
    table.draw(data, {showRowNumber: false});
  }
function drawLogTable() {
var data = google.visualization.arrayToDataTable([

<c:out value="${project.cache.jspChart.log}" escapeXml="false" />                                                  
                                                  
]);

	        var table = new google.visualization.Table(document.getElementById('log_div'));
	        table.draw(data, {showRowNumber: false});
	      }
function drawBurnedTable() {
	var data = google.visualization.arrayToDataTable([

	<c:out value="${project.cache.jspChart.burnedTable}" escapeXml="false" />                                                  
	                                                  
	]);

		        var table = new google.visualization.Table(document.getElementById('burned_div'));
		        table.draw(data, {showRowNumber: false});
		      }
</script>