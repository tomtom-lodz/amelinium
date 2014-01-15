<%@ page import="amelinium1.grails.SecUser"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<g:set var="entityName"
	value="${message(code: 'secUser.label', default: 'User')}" />
<title><g:message code="default.create.label" args="[entityName]" /></title>
</head>
<body>
	<section id="create-secUser" class="first">
		<g:form action="save" class="form-horizontal">
			<g:hasErrors bean="${secUserInstance}">
				<div class="errors">
					<g:renderErrors bean="${secUserInstance}" as="list" />
				</div>
			</g:hasErrors>
			<g:if test="${flash.message}">
				<div class="errors" role="status">
					${flash.message}
				</div>
			</g:if>
			<fieldset class="form">
				<g:render template="form" />
			</fieldset>
			<div class="form-actions offset-top">
				<g:submitButton name="create" class="btn btn-primary"
					value="${message(code: 'default.button.create.label', default: 'Create')}" />
				<button class="btn" type="reset">
					<g:message code="default.button.reset.label" default="Reset" />
				</button>
			</div>
		</g:form>
	</section>
	<script type="text/javascript">
		changeBg();
	</script>
</body>
</html>
