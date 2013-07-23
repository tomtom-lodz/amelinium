<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title><c:out value="${projectName}"/> Chart</title>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">
google.load('visualization', '1', {packages:['table','corechart']});
google.setOnLoadCallback(drawConfigurationTable,true);
google.setOnLoadCallback(drawChart,true);
google.setOnLoadCallback(drawRoadmap,true);
// google.setOnLoadCallback(drawBurnedTable,true);
google.setOnLoadCallback(drawLogTable,true);
function drawConfigurationTable() {
var data = google.visualization.arrayToDataTable([
                                                  
<c:out value="${jspChart.configuration}" escapeXml="false" />

	        var table = new google.visualization.Table(document.getElementById('configuration_div'));
	        table.draw(data, {showRowNumber: false});
	      }
function drawChart() {
var data = google.visualization.arrayToDataTable([
                                                  
<c:out value="${jspChart.chart}" escapeXml="false" />

]);

    var options = {
     // title: 'aaa',
       title: <c:out value="'${projectName}'" escapeXml="false" />,
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

<c:out value="${jspChart.roadmap}" escapeXml="false" />
                                                  
]);

    var table = new google.visualization.Table(document.getElementById('roadmap_div'));
    table.draw(data, {showRowNumber: false});
  }
function drawLogTable() {
var data = google.visualization.arrayToDataTable([

<c:out value="${jspChart.log}" escapeXml="false" />                                                  
                                                  
]);

	        var table = new google.visualization.Table(document.getElementById('log_div'));
	        table.draw(data, {showRowNumber: false});
	      }
function drawBurnedTable() {
	var data = google.visualization.arrayToDataTable([

	<c:out value="${jspChart.burnedTable}" escapeXml="false" />                                                  
	                                                  
	]);

		        var table = new google.visualization.Table(document.getElementById('burned_div'));
		        table.draw(data, {showRowNumber: false});
		      }
</script>
</head>
<body>

<c:set var="path" value="${pageContext.request.contextPath}"
		scope="page" />
	<c:if test="${path=='/'}">
		<c:set var="path" value="" scope="page" />
	</c:if>

<div id="chart_tab_content" >
	<div id="top_div" style="width: 900px">
<a href="${path}/web/projects/">HOME</a>
	<table cellpadding="5px" cellspacing="5px">
<tr>
<td><a href="${path}/web/project/<c:out value="${projectId}"/>/backlog/view">BACKLOG</a></td>
<td><a href="${path}/web/project/<c:out value="${projectId}"/>/chart/view">CHART</a></td>
<td><a href="${path}/web/project/<c:out value="${projectId}"/>/backlog/history/0">BACKLOG HISTORY</a></td>
<td><a href="${path}/web/project/<c:out value="${projectId}"/>/chart/history/0">CHART HISTORY</a></td>
</tr>

</table>
	
	</div>
	<div id="edit_div" style="width: 900px; margin-left: 870px">
	<a href="${path}/web/project/<c:out value="${projectId}"/>/chart/edit">EDIT</a><br/>
<%-- 	<a href="${path}/web/project/<c:out value="${projectId}"/>/chart/recalculate">RECALCULATE</a><br/> --%>
	
	</div>
		<p style="margin-left: 150px"><b><i><c:out value="${projectName}"/></i> chart</b></p>
		<p style="margin-left: 150px; color: red"><b><i><c:out value="${message}" escapeXml="false" /></i></b></p>    
	<div id="configuration_div" style="width: 400px; margin-left: 150px"></div>
	<div id="chart_div" style="width: 900px;">
	<h3 style="margin-left: 150px">Roadmap</h3>
	</div>
	
	<div id="roadmap_div" style="width: 600px; margin-left: 150px"></div>
	<div id="burned_div" style="width: 180px;  margin-left: 150px"></div>
	<p style="margin-left: 150px">Autocorrection log</p>
	<div id="log_div" style="width: 600px;  margin-left: 150px"></div>
	<p style="margin-left: 150px"><i><c:out value="${jspChart.logMessage}" escapeXml="false" /></i></p>    
</div>
</body>
</html>