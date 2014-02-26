<%@ page import="amelinium1.grails.Csv"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<g:set var="entityName" value="${message(code: 'csv.label', default: 'Csv')}" />
<g:set var="projectName"
	value="${message(code: 'project.label', default: 'Project')}" />
<title><g:message code="default.edit.label" args="[entityName]" /></title>
</head>
<body>
	<div class="content">
		<div class="offset-left">
			<ol class="breadcrumb">
				<li><a href="${createLink(uri: '/')}"><g:message
							code="default.home.label" default="home" /></a></li>
				<li><g:link class="list" action="show" id="${projectInstance?.id}">
						${projectInstance?.name} - csv
				</g:link></li>
				<li></li>
			</ol>
			<h1>
				Edit - ${projectInstance?.name} - csv
			</h1>
		</div>
		<div class="content offset-top">
			<g:if test="${flash.message}">
				<div class="message" role="status">
					${flash.message}
				</div>
			</g:if>
			<g:hasErrors bean="${csvInstance}">
				<ul class="errors" role="alert">
					<g:eachError bean="${csvInstance}" var="error">
						<li
							<g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
								error="${error}" /></li>
					</g:eachError>
				</ul>
			</g:hasErrors>
			<g:form method="post">
				<g:hiddenField name="id" value="${projectInstance?.id}" />
				<g:hiddenField name="version" value="${projectInstance?.version}" />
				<fieldset class="form">
					<g:render template="form" />
				</fieldset>
				<fieldset class="form-buttons pull-right">
					<g:actionSubmit class="btn btn-primary" action="update"
						value="${message(code: 'default.button.update.label', default: 'Update')}" />
					<g:actionSubmit class="btn btn-success" action="save"
						value="${message(code: 'default.button.save.label', default: 'Save')}" />
					<g:link class="cancel" action="show" id="${projectInstance?.id}">
						<g:message code="default.button.cancel.label" default="Cancel" />
					</g:link>
				</fieldset>
			</g:form>
		</div>
	</div>
	<span class="for-footer"></span>
	<g:javascript src="bg.js"/>
	<g:if test="${flash.message}">
		<g:javascript src="csv-text-area-flash.js"/>
	</g:if>
	<g:else>
		<g:javascript src="csv-text-area.js"/>
	</g:else>
</body>
</html>
