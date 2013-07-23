<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>History</title>
</head>
<body>

<c:set var="path" value="${pageContext.request.contextPath}"
		scope="page" />
	<c:if test="${path=='/'}">
		<c:set var="path" value="" scope="page" />
	</c:if>

<a href="${path}/web/projects/">go back to project list</a> <br/>
	<b>PROJECT HISTORY</b><br/>
<a href="${path}/web/project/<c:out value="${projectId}"/>/history/<c:out value="${translationNewer}"/>">newer</a>
	&nbsp;
	<table>
		<tbody>
			<tr>
				<th>Revision</th>
				<th>Date</th>
				<th>Operations</th>
			</tr>
<c:forEach var="snapshot" items="${history}">

				<tr>
					<td><c:out value="${snapshot.revision}"/></td>
					<td><c:out value="${snapshot.backlogSnapshot.date}"/></td>
					<td>
					<a
						href="${path}/web/project/<c:out value="${projectId}"/>/backlog/r<c:out value="${snapshot.backlogSnapshot.revision}"/>/view">view backlog</a>
						&nbsp;
							<a
						href="${path}/web/project/<c:out value="${projectId}"/>/backlog/r<c:out value="${snapshot.backlogSnapshot.revision}"/>/revert">revert backlog</a>
							&nbsp;
							<a
						href="${path}/web/project/<c:out value="${projectId}"/>/chart/r<c:out value="${snapshot.chartSnapshot.revision}"/>/view">view chart</a>
						&nbsp;
							<a
						href="${path}/web/project/<c:out value="${projectId}"/>/chart/r<c:out value="${snapshot.chartSnapshot.revision}"/>/revert">revert chart</a>
						
							</td>
				</tr>
	</c:forEach>
		
		</tbody>
	</table>
<a href="${path}/web/project/<c:out value="${projectId}"/>/history/<c:out value="${translationOlder}"/>">older</a>
<br/>
<br/>
</body>
</html>