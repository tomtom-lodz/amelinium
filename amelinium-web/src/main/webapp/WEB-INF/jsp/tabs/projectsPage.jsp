<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>AMELINIUM Planning Tool</title>
</head>
<body>
	<b>PROJECT LIST</b>
	<c:if test="${size!=0}">
	<table>
		<tbody>
			<tr>
				<th>Name</th>
				<th>Operations</th>
			</tr>
			<c:forEach var="p" items="${projects}">
				<tr>
					<td><c:out value="${p.name}" />&nbsp;&nbsp;</td>
					<td><a
						href="${pageContext.request.contextPath}/web/project<c:out value="${p.id}"/>">view
							project</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</c:if>
<a href="/amelinium/web/projects/new">new project</a>
</body>
</html>