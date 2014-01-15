
<%@ page import="amelinium1.grails.Project"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<g:set var="entityName"
	value="${message(code: 'project.label', default: 'Project')}" />
<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
<div class="wrapper">
	<div>
		<ol class="breadcrumb">
			<li><a href="${createLink(uri: '/')}"><g:message
						code="default.home.label" /></a></li>
			<li><g:link class="list" action="show"
					id="${projectInstance?.id}">
					${projectInstance?.name}
				</g:link></li>
		</ol>
		<div class="buttons">
			<g:form>
				<g:hiddenField name="id" value="${projectInstance?.id}" />
				<g:actionSubmit class="btn btn-primary pull-right" action="close"
					value="${message(code: 'default.button.close.label', default: 'Close')}"
					onclick="return confirm('${message(code: 'default.button.close.confirm.message', default: 'Are you sure?')}');" />
				<g:if test="${projectInstance?.status=="Open"}">
					<g:link class="btn btn-primary pull-right" action="edit"
						id="${projectInstance?.id}">
						<g:message code="default.button.edit.label" default="Edit" />
					</g:link>
				</g:if>
			</g:form>
		</div>
	</div>
	<div class="show-project">
		<g:if test="${flash.message}">
			<div class="message" role="status">
				${flash.message}
			</div>
		</g:if>
		<ol class="property-list project">

			<g:if test="${projectInstance?.name}">
				<li class="fieldcontain"><span id="name-label"
					class="property-label"><g:message code="project.name.label"
							default="Name" /></span> <span class="property-value"
					aria-labelledby="name-label"><g:fieldValue
							bean="${projectInstance}" field="name" /></span></li>
			</g:if>

			<g:if test="${projectInstance?.revision.backlog}">
				<li class="fieldcontain"><span id="backlog-label"
					class="property-label"><g:message
							code="project.backlog.label" default="Backlog" /></span> <span
					class="property-value" aria-labelledby="backlog-label"> <g:link
							action="showBacklog" id="${projectInstance?.id}">Show backlog</g:link>
				</span></li>
			</g:if>

			<g:if test="${projectInstance?.revision.csv}">
				<li class="fieldcontain"><span id="csv-label"
					class="property-label"><g:message code="project.csv.label"
							default="Csv" /></span> <span class="property-value"
					aria-labelledby="csv-label"> <g:link action="showCsv"
							id="${projectInstance?.id}">Show csv</g:link>
				</span></li>
			</g:if>
			<li class="fieldcontain"><span id="revision-label"
				class="property-label"><g:message
						code="project.revision.label" default="Revision" /></span> <span
				class="property-value" aria-labelledby="revision-label"> <g:link
						action="listRevision" id="${projectInstance?.id}">Show revision</g:link>
			</span></li>
			<li class="fieldcontain"><span id="Status-label"
				class="property-label"><g:message code="project.status.label"
						default="Status" /></span> <span class="property-value"
				aria-labelledby="Status-label"> ${projectInstance?.status}
			</span></li>
		</ol>
	</div>
</div>
</body>
</html>
