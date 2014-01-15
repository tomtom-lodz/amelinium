<%@ page import="amelinium1.grails.Project"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<g:set var="entityName"
	value="${message(code: 'project.label', default: 'Project')}" />
<title><g:message code="default.edit.label" args="[entityName]" /></title>
</head>
<body>
	<div class="wrapper">
		<div class="content">
			<div>
				<ol class="breadcrumb">
					<li><a href="${createLink(uri: '/')}"><g:message
								code="default.home.label" /></a></li>
					<li></li>
				</ol>
			</div>
			<h1>
				Edit -
				${projectInstance?.name}
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
				<g:form method="post">
					<g:hiddenField name="id" value="${projectInstance?.id}" />
					<g:hiddenField name="version" value="${projectInstance?.version}" />
					<span class="created"> Created - <g:formatDate
							format="yyyy-MM-dd HH:mm" date="${projectInstance?.dateCreated}" /> by <b>
							${projectInstance?.createdBy}
					</b> <g:if test="${projectInstance?.editedBy}">, last time edited - <g:formatDate
								format="yyyy-MM-dd HH:mm" date="${projectInstance?.lastUpdated}" /> by <b>
								${projectInstance?.editedBy}
							</b>
						</g:if>
					</span>
					<fieldset class="form offset-top">
						<g:render template="form" />
					</fieldset>
					<fieldset class="pull-right">
						<g:actionSubmit class="btn btn-primary" action="update"
							value="${message(code: 'default.button.update.label', default: 'Update')}" />
						<g:link action="list" id="${projectInstance.id}">
							<g:message code="default.button.cancel.label" default="Cancel" />
						</g:link>
					</fieldset>
				</g:form>
			</div>
		</div>
	</div>
	<g:javascript src="bg.js" />
</body>
</html>
