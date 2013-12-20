
<%@ page import="amelinium1.grails.Backlog" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'backlog.label', default: 'Backlog')}" />
		<g:set var="projectName" value="${message(code: 'project.label', default: 'Project')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-backlog" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link controller="project" class="list" action="show" id="${projectInstance?.id}">${projectInstance?.name}</g:link></li>
				<li><g:link class="list" action="show" id="${projectInstance?.id}">Backlog${backlogInstance?.ver}</g:link></li>
			</ul>
		</div>
		<div id="show-backlog" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list backlog">
				<li class="fieldcontain">
					<ckeditor:config readOnly="true"/>
					<ckeditor:editor name="text" toolbar="empty" >${backlogInstance.text?.decodeHTML()}</ckeditor:editor>
				</li>
				
				<li class="fieldcontain">
					<span id="ver-label" class="property-label"><g:message code="backlog.comment.label" default="Comment" /></span>
					<textarea readonly class="property-area-comment"><g:fieldValue bean="${backlogInstance.comment}" field="text"/></textarea>	
				</li>
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${backlogInstance?.id}" />
					<g:link controller="Project" class="cancel" action="show" id="${projectInstance?.id}"><g:message code="default.button.back.label" default="Back" /></g:link>
					<g:if test="${projectInstance?.status=="Open"}"><g:link class="edit" action="edit" id="${projectInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link></g:if>
					<g:link class="history" action="listRevision" id="${projectInstance?.id}"><g:message code="default.button.history.label" default="History" /></g:link>
				</fieldset>
			</g:form>
		</div>
		<g:javascript>
			alert( document.getElementById( 'editor1' ).value );
		</g:javascript>
	</body>
</html>
