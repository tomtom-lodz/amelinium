
<%@ page import="amelinium1.grails.Project" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="main" />
	<g:set var="entityName" value="${message(code: 'project.label', default: 'Project')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>

<body>
	
<section id="list-project" class="first">

	<table class="table table-bordered margin-top-medium">
		<thead>
			<tr>
			
				<g:sortableColumn property="name" title="${message(code: 'project.name.label', default: 'Name')}" />
			
				<th><g:message code="project.revision.label" default="Revision" /></th>
			
				<g:sortableColumn property="status" title="${message(code: 'project.status.label', default: 'Status')}" />
			
			</tr>
		</thead>
		<tbody>
		<g:each in="${projectInstanceList}" status="i" var="projectInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td><g:link action="show" id="${projectInstance.id}">${fieldValue(bean: projectInstance, field: "name")}</g:link></td>
			
				<td>${fieldValue(bean: projectInstance, field: "revision")}</td>
			
				<td>${fieldValue(bean: projectInstance, field: "status")}</td>
			
			</tr>
		</g:each>
		</tbody>
	</table>
	<div class="container">
		<bs:paginate total="${projectInstanceTotal}" />
	</div>
</section>

</body>

</html>
