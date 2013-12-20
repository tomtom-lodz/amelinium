<%@ page import="amelinium1.grails.Revision" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'revision.label', default: 'Revision')}" />
		<g:set var="projectName" value="${message(code: 'project.label', default: 'Project')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-revision" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link controller="project" class="list" action="show" id="${projectInstance?.id}">${projectInstance?.name}</g:link></li>
				<li><g:link class="list" action="list" id="${projectInstance?.id}">Revisions</g:link></li>
			</ul>
		</div>
		<div id="list-revision" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
						<th><g:message code="revision.revision.label" default="Revision" /></th>
						<g:if test="${display=='csv'}">
							<th><g:message code="revision.csv.label" default="Csv" /></th>
						</g:if>
						<g:else>
							<th><g:message code="revision.backlog.label" default="Backlog" /></th>
						</g:else>
						<g:if test="${display=='project'}">
							<th><g:message code="revision.csv.label" default="Csv" /></th>
						</g:if>
						<g:else>
							<th><g:message code="revision.project.label" default="Project" /></th>
						</g:else>
					</tr>
				</thead>
				<tbody>
				<g:if test="${display=='project'}">
				<g:each in="${revisionInstanceList}" status="i" var="revisionInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						<td>${revisionInstance.ver}</td>
						<td>${revisionInstance.backlog.ver}
						<g:link controller="backlog" action="showRevision" id="${revisionInstance.backlog.id}">show</g:link>
						<g:link controller="backlog" action="restore" id="${revisionInstance.backlog.id}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');">restore</g:link>
						</td>
						<td>${revisionInstance.csv.ver}
						<g:link controller="csv" action="showRevision" id="${revisionInstance.csv.id}">show</g:link>
						<g:link controller="csv" action="restore" id="${revisionInstance.csv.id}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');">restore</g:link>
						</td>
					</tr>
				</g:each>
				</g:if>
				<g:if test="${display=='csv'}">
				<g:each in="${revisionInstanceList}" status="i" var="revisionInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						<td>${revisionInstance.ver}</td>
						<td>${revisionInstance.csv.ver}
						<g:link controller="csv" action="showRevision" id="${revisionInstance.csv.id}">show</g:link>
						<g:link controller="csv" action="restore" id="${revisionInstance.csv.id}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');">restore</g:link>
						</td>
						<td>${projectInstance.name}
						</td>
					</tr>
				</g:each>
				</g:if>
				<g:if test="${display=='backlog'}">
				<g:each in="${revisionInstanceList}" status="i" var="revisionInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						<td>${revisionInstance.ver}</td>
						<td>${revisionInstance.backlog.ver}
						<g:link controller="backlog" action="showRevision" id="${revisionInstance.backlog.id}">show</g:link>
						<g:link controller="backlog" action="restore" id="${revisionInstance.backlog.id}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');">restore</g:link>
						</td>
						<td>${projectInstance.name}
						</td>
					</tr>
				</g:each>
				</g:if>
				</tbody>
			</table>
			<fieldset class="buttons">
				<g:hiddenField name="id" value="${revisionInstance?.id}" />
				<g:link controller="Project" class="cancel" action="show" id="${projectInstance?.id}"><g:message code="default.button.back.label" default="Back" /></g:link>
			</fieldset>
			<g:if test="${revisionInstanceTotal>10}">
				<div class="pagination">
					<g:paginate id="${revisionInstance?.id}" total="${revisionInstanceTotal}" />
				</div>
			</g:if>
		</div>
	</body>
</html>
