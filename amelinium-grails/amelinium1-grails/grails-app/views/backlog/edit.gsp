<%@ page import="amelinium1.grails.Backlog"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<g:set var="entityName"
	value="${message(code: 'backlog.label', default: 'Backlog')}" />
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
				<li><g:link class="list" action="show"
						id="${projectInstance?.id}">
						${projectInstance?.name} - backlog
				</g:link></li>
				<li></li>
			</ol>
			<h1>Edit - ${projectInstance?.name} - backlog</h1>
		</div>
		<div class="btn-group offset-top">
  			<input type="button" class="button-podkresl btn btn-default" value="Strikethrough">
			<input type="button" class="button-lista btn btn-default" value="List">

  			<div class="btn-group">
    		<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
      			Headings
     			<span class="caret"></span>
    		</button>
    		<ul class="dropdown-menu">
      			<li><a href="#" class="header-button">H1</a></li>
      			<li class="divider"></li>
      			<li><a href="#" class="header-button">H2</a></li>
      			<li class="divider"></li>
      			<li><a href="#" class="header-button">H3</a></li>
      			<li class="divider"></li>
      			<li><a href="#" class="header-button">H4</a></li>
      			<li class="divider"></li>
      			<li><a href="#" class="header-button">H5</a></li>
    			</ul>
  			</div>
		</div>
		<g:if test="${flash.message}">
			<div class="message" role="status">
				${flash.message}
			</div>
		</g:if>
		<g:hasErrors bean="${backlogInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${backlogInstance}" var="error">
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
				<g:link class="cancel" action="show" id="${projectInstance?.id}">
					<g:message code="default.button.cancel.label" default="Cancel" />
				</g:link>
			</fieldset>
		</g:form>
	</div>
	<span class="for-footer"></span>
	<g:javascript src="bg.js"/>
	<g:javascript src="wikiedit.js"/>
	<g:javascript src="text-area.js"/>
</body>
</html>
