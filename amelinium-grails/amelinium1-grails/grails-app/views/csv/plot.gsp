<%@ page import="amelinium1.grails.Csv"%>
<%@ page import="amelinium1.grails.Project"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<g:set var="entityName" value="${message(code: 'csv.label', default: 'Csv')}" />
<g:set var="projectName"
	value="${message(code: 'project.label', default: 'Project')}" />
<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
	<div class="scroller">
		<div class="wrapper">
			<div>
				<ol class="breadcrumb">
					<li><a href="${createLink(uri: '/')}"><g:message
								code="default.home.label" default="home" /></a></li>
					<li><g:link action="show" id="${projectInstance?.id}">
							${projectInstance?.name} - csv
				</g:link></li>
					<li></li>
				</ol>
				<div class="buttons">
					<button class="button-reset-zoom-chart1 button-reset-zoom-chart2 btn btn-primary pull-right">ResetZoom</button>
					<g:link class="btn btn-primary pull-right offset-right"
						controller="backlog" action="show" id="${projectInstance?.id}">
						<g:message code="default.button.backlog.label" default="Backlog" />
					</g:link>
					<g:link class="btn btn-primary pull-right offset-right"
						action="show" id="${projectInstance?.id}">
						<g:message code="default.button.csv.label" default="Csv" />
					</g:link>
				</div>
			</div>
			<div class="content">
				<g:if test="${flash.message}">
					<div class="message" role="status">
						${flash.message}
					</div>
				</g:if>
				<g:if test="${projectInstance?.revision.backlog.state!='Recalculated'}">
					<div class="errors" role="status">
						<ul>
							<li>Backlog is not recalculated</li>
						</ul>
					</div>
				</g:if>
				<h1>
					${projectInstance?.name}
					- csv - plot
				</h1>
				<g:form method="post">
					<g:hiddenField name="id" value="${projectInstance?.id}" />
					<g:hiddenField name="version" value="${projectInstance?.version}" />
					<fieldset class="form offset-top">
						<g:render template="formPlot" />
					</fieldset>
				</g:form>
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
