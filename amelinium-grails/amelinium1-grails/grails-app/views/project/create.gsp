<%@ page import="amelinium1.grails.Project"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<g:set var="entityName"
	value="${message(code: 'project.label', default: 'Project')}" />
<title><g:message code="default.create.label" args="[entityName]" /></title>
</head>
<body>
	<div class="wrapper">
		<div>
			<ol class="breadcrumb">
				<li><a href="${createLink(uri: '/')}"><g:message
							code="default.home.label" default="home" /></a></li>
				<li><g:link action="create">
						<g:message code="default.new.label" args="[entityName]" />
					</g:link></li>
			</ol>
		</div>
		<h1 class="offset-bottom offset-top">
			<g:message code="default.new.label" args="[entityName]" />
		</h1>
		<div class="content">
			<g:if test="${flash.message}">
				<div class="message" role="status">
					${flash.message}
				</div>
			</g:if>
			<g:hasErrors bean="${projectInstance}">
				<ul class="errors" role="alert">
					<g:eachError bean="${projectInstance}" var="error">
						<li
							<g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
								error="${error}" /></li>
					</g:eachError>
				</ul>
			</g:hasErrors>
			<g:form action="save">
				<g:hiddenField name="user" value="${sec.username()}" />
				<fieldset class="form">
					<g:render template="formCreate" />
				</fieldset>
				<fieldset class="offset-top pull-right">
					<g:submitButton name="create" class="btn btn-primary"
						value="${message(code: 'default.button.create.label', default: 'Create')}" />
					<g:link action="index">
						<g:message code="default.button.cancel.label" default="Cancel" />
					</g:link>
				</fieldset>
			</g:form>
		</div>
	</div>
	<g:javascript src="bg.js" />
</body>
</html>
