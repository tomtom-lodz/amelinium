<div id="chart_tab_content" >
<div id="top_div" style="width: 900px; margin-left: 870px">
	<input type="button" value="Edit" onclick='$("#chart_tab_content").load("${pageContext.request.contextPath}/web/project${project.id}/chart/edit");'></input></div>
		<p style="margin-left: 150px; color: red"><b><i>${message}</i></b></p>    
	<div id="configuration_div" style="width: 400px; margin-left: 150px"></div>
	<div id="chart_div" style="width: 900px;"></div>
	<h3 style="margin-left: 150px">Roadmap</h3>
	<div id="roadmap_div" style="width: 600px; margin-left: 150px"></div>
	<div id="burned_div" style="width: 180px;  margin-left: 150px"></div>
	<p style="margin-left: 150px">Autocorrection log</p>
	<div id="log_div" style="width: 600px;  margin-left: 150px"></div>
	<p style="margin-left: 150px"><i>${project.chartModel.log.message}</i></p>    
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
                                                  
${project.cache.jspChart.configuration}

	        var table = new google.visualization.Table(document.getElementById('configuration_div'));
	        table.draw(data, {showRowNumber: false});
	      }
function drawChart() {
var data = google.visualization.arrayToDataTable([
                                                  
${project.cache.jspChart.chart}

]);

    var options = {
     // title: 'aaa',
       title: ${project.chartModel.chartTitle},
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

${project.cache.jspChart.roadmap}
                                                  
]);

    var table = new google.visualization.Table(document.getElementById('roadmap_div'));
    table.draw(data, {showRowNumber: false});
  }
function drawLogTable() {
var data = google.visualization.arrayToDataTable([

${project.cache.jspChart.log}                                            
                                                  
]);

	        var table = new google.visualization.Table(document.getElementById('log_div'));
	        table.draw(data, {showRowNumber: false});
	      }
function drawBurnedTable() {
	var data = google.visualization.arrayToDataTable([

${project.cache.jspChart.burnedTable}                                                 
	                                                  
	]);

		        var table = new google.visualization.Table(document.getElementById('burned_div'));
		        table.draw(data, {showRowNumber: false});
		      }
</script>