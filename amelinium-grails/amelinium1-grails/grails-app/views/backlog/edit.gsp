<%@ page import="amelinium1.grails.Backlog" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'backlog.label', default: 'Backlog')}" />
		<g:set var="projectName" value="${message(code: 'project.label', default: 'Project')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#edit-backlog" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link controller="project" class="list" action="show" id="${projectInstance?.id}">${projectInstance?.name}</g:link></li>
				<li><g:link class="list" action="show" id="${projectInstance?.id}">Backlog${backlogInstance?.ver}</g:link></li>
				<li><g:link class="edit" action="edit" id="${projectInstance?.id}">Edit</g:link></li>
			</ul>
		</div>
		<div id="edit-backlog" class="content scaffold-edit" role="main">
			<h1><g:message code="default.edit.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${backlogInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${backlogInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form method="post" >
				<g:hiddenField name="id" value="${projectInstance?.id}" />
				<g:hiddenField name="version" value="${projectInstance?.version}" />
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
				<fieldset class="buttons">
					<g:link class="cancel" action="show" id="${projectInstance?.id}"><g:message code="default.button.back.label" default="Back" /></g:link>
					<g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
