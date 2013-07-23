<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<b>BACKLOG HISTORY</b><br/>
<a href="${pageContext.request.contextPath}/web/project<c:out value="${projectId}"/>/backlog/history?part=<c:out value="${translationNewer}"/>">newer</a>
	&nbsp;
	<table cellspacing="10">
		<tbody>
			<tr>
				<th>Project Revision</th>
				<th>Backlog Revision</th>
				<th>Date</th>
				<th>Operations</th>

			</tr>
<c:forEach var="snapshot" items="${history}">

				<tr>
					<td><c:out value="${snapshot.revision}"/></td>
					<td style="color: grey">
					<a
						href="${pageContext.request.contextPath}/web/project<c:out value="${projectId}"/>/backlog/r<c:out value="${snapshot.backlogSnapshot.revision}"/>/view">r. <c:out value="${snapshot.backlogSnapshot.revision}"/></a>
						<br/>
						<i><c:out value="${snapshot.backlogSnapshot.info}"/></i>
						</td>
						<td><c:out value="${snapshot.backlogSnapshot.date}"/></td>
						<td>
							<a
						href="${pageContext.request.contextPath}/web/project<c:out value="${projectId}"/>/backlog/r<c:out value="${snapshot.backlogSnapshot.revision}"/>/revert">restore this version</a>
							
							</td>
<%-- 							<td style="color: grey"><i><c:out value="${snapshot.backlogSnapshot.info}"/></i></td> --%>
				</tr>
	</c:forEach>
		
		</tbody>
	</table>
<a href="${pageContext.request.contextPath}/web/project<c:out value="${projectId}"/>/backlog/history<c:out value="${translationOlder}"/>">older</a>
<br/>
<br/>