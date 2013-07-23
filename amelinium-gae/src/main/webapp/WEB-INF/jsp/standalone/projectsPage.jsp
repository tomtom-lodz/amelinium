<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>AMELINIUM Planning Tool</title>
</head>
<body>
	<b>PROJECT LIST</b>
	<table>
		<tbody>
			<tr>
				<th>Id</th>
				<th>Name</th>
				<th>Operations</th>
			</tr>
			<c:forEach var="p" items="${projects}">
				<tr>
					<td><c:out value="${p.id}" /></td>
					<td><c:out value="${p.name}" /></td>
					<td><a
						href="${pageContext.request.contextPath}/project/<c:out value="${p.id}"/>/backlog/view">view
							backlog</a> <a
						href="${pageContext.request.contextPath}/project/<c:out value="${p.id}"/>/chart/view">view
							chart</a> 
							<a
						href="${pageContext.request.contextPath}/project/<c:out value="${p.id}"/>/rename">rename</a>
						<a
						href="${pageContext.request.contextPath}/project/<c:out value="${p.id}"/>/history">show history</a>
							<a
						href="${pageContext.request.contextPath}/project/<c:out value="${p.id}"/>/delete">delete
							project</a></td>
				</tr>


			</c:forEach>
				<tr>
					<td></td>
					<td></td>
					<td><a href="/projects/new">new project</a></td>
				</tr>
		</tbody>
	</table>

</body>
</html>