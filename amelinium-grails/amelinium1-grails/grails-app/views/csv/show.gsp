
<%@ page import="amelinium1.grails.Csv" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'csv.label', default: 'Csv')}" />
		<g:set var="projectName" value="${message(code: 'project.label', default: 'Project')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-csv" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link controller="project" class="list" action="show" id="${projectInstance?.id}">${projectInstance?.name}</g:link></li>
				<li><g:link class="list" action="show" id="${projectInstance?.id}">Csv${csvInstance?.ver}</g:link></li>
			</ul>
		</div>
		<div id="show-csv" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list csv">
				<li class="fieldcontain">
					<ckeditor:editor name="text" toolbar="empty" >${csvInstance.text?.decodeHTML()}</ckeditor:editor>
				</li>
				
				<li class="fieldcontain">
					<span id="ver-label" class="property-label"><g:message code="backlog.comment.label" default="Comment" /></span>
					<textarea readonly class="property-area-comment"><g:fieldValue bean="${csvInstance.comment}" field="text"/></textarea>	
				</li>
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${csvInstance?.id}" />
					<g:link controller="Project" class="cancel" action="show" id="${projectInstance?.id}"><g:message code="default.button.back.label" default="Back" /></g:link>
					<g:if test="${projectInstance?.status=="Open"}"><g:link class="edit" action="edit" id="${projectInstance?.id}"><g:message code="default.button.edit.label" default="Edit"/></g:link></g:if>
					<g:link class="history" action="listRevision" id="${projectInstance?.id}"><g:message code="default.button.history.label" default="History" /></g:link>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
