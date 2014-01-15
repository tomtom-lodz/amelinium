
<%@ page import="amelinium1.grails.SecUser" %>
<!doctype html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'secUser.label', default: 'SecUser')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>

<body>

<section id="show-secUser" class="first">

	<table class="table">
		<tbody>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secUser.username.label" default="Username" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secUserInstance, field: "username")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secUser.password.label" default="Password" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secUserInstance, field: "password")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secUser.accountExpired.label" default="Account Expired" /></td>
				
				<td valign="top" class="value"><g:formatBoolean boolean="${secUserInstance?.accountExpired}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secUser.accountLocked.label" default="Account Locked" /></td>
				
				<td valign="top" class="value"><g:formatBoolean boolean="${secUserInstance?.accountLocked}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secUser.enabled.label" default="Enabled" /></td>
				
				<td valign="top" class="value"><g:formatBoolean boolean="${secUserInstance?.enabled}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secUser.passwordExpired.label" default="Password Expired" /></td>
				
				<td valign="top" class="value"><g:formatBoolean boolean="${secUserInstance?.passwordExpired}" /></td>
				
			</tr>
		
		</tbody>
	</table>
</section>

</body>

</html>
