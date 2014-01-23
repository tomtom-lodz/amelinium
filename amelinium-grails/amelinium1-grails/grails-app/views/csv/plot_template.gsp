<%@ page import="amelinium1.grails.Csv"%>
<%@ page import="amelinium1.grails.Project"%>
<!DOCTYPE html>
<html>
<head>
<g:set var="entityName" value="${message(code: 'csv.label', default: 'Csv')}" />
<g:set var="projectName"
	value="${message(code: 'project.label', default: 'Project')}" />
<title><g:message code="default.show.label" args="[entityName]" /></title>
<r:require modules="bootstrap" />
<r:layoutResources />
<link rel="stylesheet"
	href="${resource(dir: 'js/jqplot', file: 'jquery.jqplot.min.css')}"
	type="text/css">
<body>
	<div class="scroller">
		<div class="wrapper">
			<div class="content">
				<g:if test="${flash.message}">
					<div class="message" role="status">
						${flash.message}
					</div>
				</g:if>
				<div id="chart1" style="height: 600px; width: 100%;"></div>
				<div>
					${burnuptable}
				</div>
				<script class="code" type="text/javascript">
					
				${chart}
					
				</script>
				<!-- Don't touch this! -->
				<g:javascript src="jqplot/jquery.jqplot.min.js" />
				<!-- End Don't touch this! -->
				<!-- Additional plugins go here -->
				<g:javascript src="jqplot/plugins/jqplot.dateAxisRenderer.min.js" />
				<g:javascript src="jqplot/plugins/jqplot.highlighter.min.js" />
				<g:javascript src="jqplot/plugins/jqplot.cursor.min.js" />
				<g:javascript src="jqplot/plugins/jqplot.enhancedLegendRenderer.js" />
				<!-- End additional plugins -->
			</div>
		</div>
	</div>
	<g:javascript src="overflow-scroller.js" />
</body>
</html>
