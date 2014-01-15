
<%@ page import="amelinium1.grails.SecUser" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'secUser.label', default: 'SecUser')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>

<body>
	
<section id="list-secUser" class="first">

	<table class="table table-bordered margin-top-medium">
		<thead>
			<tr>
			
				<g:sortableColumn property="username" title="${message(code: 'secUser.username.label', default: 'Username')}" />
			
				<g:sortableColumn property="password" title="${message(code: 'secUser.password.label', default: 'Password')}" />
			
				<g:sortableColumn property="accountExpired" title="${message(code: 'secUser.accountExpired.label', default: 'Account Expired')}" />
			
				<g:sortableColumn property="accountLocked" title="${message(code: 'secUser.accountLocked.label', default: 'Account Locked')}" />
			
				<g:sortableColumn property="enabled" title="${message(code: 'secUser.enabled.label', default: 'Enabled')}" />
			
				<g:sortableColumn property="passwordExpired" title="${message(code: 'secUser.passwordExpired.label', default: 'Password Expired')}" />
			
			</tr>
		</thead>
		<tbody>
		<g:each in="${secUserInstanceList}" status="i" var="secUserInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td><g:link action="show" id="${secUserInstance.id}">${fieldValue(bean: secUserInstance, field: "username")}</g:link></td>
			
				<td>${fieldValue(bean: secUserInstance, field: "password")}</td>
			
				<td><g:formatBoolean boolean="${secUserInstance.accountExpired}" /></td>
			
				<td><g:formatBoolean boolean="${secUserInstance.accountLocked}" /></td>
			
				<td><g:formatBoolean boolean="${secUserInstance.enabled}" /></td>
			
				<td><g:formatBoolean boolean="${secUserInstance.passwordExpired}" /></td>
			
			</tr>
		</g:each>
		</tbody>
	</table>
	<div class="container">
		<bs:paginate total="${secUserInstanceTotal}" />
	</div>
</section>

</body>

</html>
