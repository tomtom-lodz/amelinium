
<%@ page import="amelinium1.grails.Project" %>
<!doctype html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="main" />
	<g:set var="entityName" value="${message(code: 'project.label', default: 'Project')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>

<body>

<section id="show-project" class="first">
	<div class="nav" role="navigation">
		<ul>
			<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
			<li><g:link class="list" action="show" id="${projectInstance?.id}">${projectInstance?.name}</g:link></li>
		</ul>
	</div>
	<table class="table">
		<tbody>
			<tr class="prop">
				<td valign="top" class="name"><g:message code="project.name.label" default="Name" /></td>		
				<td valign="top" class="value">${fieldValue(bean: projectInstance, field: "name")}</td>
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="project.revision.label" default="Revision" /></td>
				<td valign="top" class="value"><g:link controller="revision" action="show" id="${projectInstance?.revision?.id}">Show latest revision</g:link></td>
			</tr>

			<tr class="prop">
				<td valign="top" class="name"><g:message code="project.status.label" default="Status" /></td>				
				<td valign="top" class="value">${fieldValue(bean: projectInstance, field: "status")}</td>
			</tr>
		
		</tbody>
	</table>
</section>

</body>

</html>
