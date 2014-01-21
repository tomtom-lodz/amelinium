
<%@ page import="amelinium1.grails.Project"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<g:set var="entityName"
	value="${message(code: 'project.label', default: 'Project')}" />
<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
	<div class="scroller">
		<div class="wrapper">
			<div>
				<ol class="breadcrumb">
					<li><a href="${createLink(uri: '/')}"><g:message
								code="default.home.label" default="home" /></a></li>
				</ol>
				<div class="buttons">
					<g:link class="btn btn-primary pull-right" action="create">
						<g:message code="default.new.label" args="[entityName]" />
					</g:link>
				</div>
			</div>
			<div class="table-resposive">
				<g:if test="${flash.message}">
					<div class="message" role="status">
						${flash.message}
					</div>
				</g:if>
				<table class="table project">
					<thead>
						<tr>
							<g:if test="${sorted=='name'&&ordered=='desc'}">
								<g:sortableColumn property="name"
									title="${message(code: 'project.name.label', default: 'Project name')}"
									class="sorted-asc project" />
							</g:if>
							<g:else>
								<g:sortableColumn property="name"
									title="${message(code: 'project.name.label', default: 'Project name')}"
									class="sorted-desc project" />
							</g:else>
							<th><g:message code="project.backlog.label" default="Backlog" /></th>
							<th><g:message code="project.csv.label" default="Plot" /></th>
							<th><g:message code="project.revision.label"
									default="Project history" /></th>
							<th><g:message code="project.status.label" default="Status" /></th>
							<g:if test="${sorted=='lastUpdated'&&ordered=='desc'}">
								<g:sortableColumn property="lastUpdated"
									title="${message(code: 'project.updated.label', default: 'Last update')}"
									class="sorted-asc date" />
							</g:if>
							<g:else>
								<g:sortableColumn property="lastUpdated"
									title="${message(code: 'project.updated.label', default: 'Last update')}"
									class="sorted-desc date" />
							</g:else>
						</tr>
					</thead>
					<tbody>
						<g:each in="${projectInstanceList}" status="i" var="projectInstance">
							<tr>
								<td><g:link action="edit" id="${projectInstance.id}">
										${projectInstance.name}
									</g:link></td>
								<td><g:link action="showBacklog" id="${projectInstance?.id}">Backlog</g:link>
									| ${projectInstance?.revision.backlog.state}</td>
								<td><g:link controller="csv" action="plot"
										id="${projectInstance?.id}"> Plot</g:link> | <g:link action="showCsv"
										id="${projectInstance?.id}">Csv</g:link></td>
								<td><g:link action="listRevision" id="${projectInstance?.id}">History</g:link></td>
								<td>
									${projectInstance?.status}
								</td>
								<td><g:formatDate format="yyyy-MM-dd HH:mm"
										date="${projectInstance?.lastUpdated}" /></td>
							</tr>
						</g:each>
					</tbody>
				</table>
			</div>
			<div class="pages pull-right">
				<g:form action="list">
					<g:select name="max" from="${['5', '10', '20', '50']}" onchange="submit()"
						value="${projectsMax}" />
				</g:form>
			</div>
			<g:if test="${projectInstanceTotal>projectsMax.toInteger()}">
				<div class="pagination">
					<g:paginate total="${projectInstanceTotal}" />
				</div>
			</g:if>
		</div>
	</div>
	<g:javascript src="overflow-scroller.js" />
</body>
</html>
