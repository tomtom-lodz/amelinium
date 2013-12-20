
<%@ page import="amelinium1.grails.Comment" %>
<!doctype html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'comment.label', default: 'Comment')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>

<body>

<section id="show-comment" class="first">

	<table class="table">
		<tbody>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="comment.name.label" default="Name" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: commentInstance, field: "name")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="comment.value.label" default="Value" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: commentInstance, field: "value")}</td>
				
			</tr>
		
		</tbody>
	</table>
</section>

</body>

</html>
