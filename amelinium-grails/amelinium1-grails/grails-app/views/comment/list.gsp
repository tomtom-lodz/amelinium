
<%@ page import="amelinium1.grails.Comment" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'comment.label', default: 'Comment')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>

<body>
	
<section id="list-comment" class="first">

	<table class="table table-bordered margin-top-medium">
		<thead>
			<tr>
			
				<g:sortableColumn property="name" title="${message(code: 'comment.name.label', default: 'Name')}" />
			
				<g:sortableColumn property="value" title="${message(code: 'comment.value.label', default: 'Value')}" />
			
			</tr>
		</thead>
		<tbody>
		<g:each in="${commentInstanceList}" status="i" var="commentInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td><g:link action="show" id="${commentInstance.id}">${fieldValue(bean: commentInstance, field: "name")}</g:link></td>
			
				<td>${fieldValue(bean: commentInstance, field: "value")}</td>
			
			</tr>
		</g:each>
		</tbody>
	</table>
	<div class="container">
		<bs:paginate total="${commentInstanceTotal}" />
	</div>
</section>

</body>

</html>
