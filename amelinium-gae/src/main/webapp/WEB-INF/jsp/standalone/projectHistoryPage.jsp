<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>History</title>
</head>
<body>
	<b>PROJECT HISTORY</b>
	<table>
		<tbody>
			<tr>
				<th>Revision</th>
				<th>Operations</th>
			</tr>
<c:forEach var="snapshot" items="${history}">

				<tr>
					<td><c:out value="${snapshot.revision}"/></td>
					<td>
					<a
						href="${pageContext.request.contextPath}/project/<c:out value="${projectId}"/>/backlog/r<c:out value="${snapshot.backlogSnapshot.revision}"/>/view">view backlog</a>
						&nbsp;
							<a
						href="${pageContext.request.contextPath}/project/<c:out value="${projectId}"/>/backlog/r<c:out value="${snapshot.backlogSnapshot.revision}"/>/revert">revert backlog</a>
							&nbsp;
							<a
						href="${pageContext.request.contextPath}/project/<c:out value="${projectId}"/>/chart/r<c:out value="${snapshot.chartSnapshot.revision}"/>/view">view chart</a>
						&nbsp;
							<a
						href="${pageContext.request.contextPath}/project/<c:out value="${projectId}"/>/chart/r<c:out value="${snapshot.chartSnapshot.revision}"/>/revert">revert chart</a>
						
							</td>
				</tr>
	</c:forEach>
		
		</tbody>
	</table>

</body>
</html>