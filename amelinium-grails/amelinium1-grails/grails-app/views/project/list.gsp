
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
	<a href="#list-project" class="skip" tabindex="-1"><g:message
			code="default.link.skip.label" default="Skip to content&hellip;" /></a>
	<div class="nav" role="navigation">
		<ul>
			<li><a class="home" href="${createLink(uri: '/')}"><g:message
						code="default.home.label" /></a></li>
		</ul>
	</div>
	<div id="list-project" class="content scaffold-list" role="main">
		<h1>
			<g:message code="default.list.label" args="[entityName]" />
		</h1>
		<g:if test="${flash.message}">
			<div class="message" role="status">
				${flash.message}
			</div>
		</g:if>

		<table>
			<thead>
				<tr>

					<g:sortableColumn property="name"
						title="${message(code: 'project.name.label', default: 'Name')}" />

					<th><g:message code="project.backlog.label" default="Backlog" /></th>

					<th><g:message code="project.csv.label" default="Csv" /></th>

					<th><g:message code="project.revision.label"
							default="Revision" /></th>

				</tr>
			</thead>
			<tbody>
				<g:each in="${projectInstanceList}" status="i" var="projectInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

						<td><g:link action="show" id="${projectInstance.id}">
								${fieldValue(bean: projectInstance, field: "name")}
							</g:link></td>

						<td><g:link action="showBacklog"
								id="${projectInstance?.id}">Show backlog</g:link>
						</td>

						<td><g:link action="showCsv"
								id="${projectInstance?.id}">Show csv</g:link>
						</td>

						<td><g:link action="listRevision"
								id="${projectInstance?.id}">Show history</g:link></td>

					</tr>
				</g:each>
			</tbody>
		</table>
		<fieldset class="buttons">
			<g:link class="create" action="create">
					<g:message code="default.new.label" args="[entityName]" />
				</g:link>		
		</fieldset>
		<g:if test="${projectInstanceTotal>10}">
			<div class="pagination">
				<g:paginate total="${projectInstanceTotal}" />
			</div>
		</g:if>
	</div>
</body>
</html>
