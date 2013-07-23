<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title><c:out value="${projectName}"/> backlog history</title>
</head>
<body>

<c:set var="path" value="${pageContext.request.contextPath}"
		scope="page" />
	<c:if test="${path=='/'}">
		<c:set var="path" value="" scope="page" />
	</c:if>

	<div id="top_div" style="width: 900px">
<a href="${path}/web/projects/">HOME</a>
	<table cellpadding="5px" cellspacing="5px">
<tr>
<td><a href="${path}/web/project/<c:out value="${projectId}"/>/backlog/view">BACKLOG</a></td>
<td><a href="${path}/web/project/<c:out value="${projectId}"/>/chart/view">CHART</a></td>
<td><a href="${path}/web/project/<c:out value="${projectId}"/>/backlog/history/0">BACKLOG HISTORY</a></td>
<td><a href="${path}/web/project/<c:out value="${projectId}"/>/chart/history/0">CHART HISTORY</a></td>
</tr>

</table>
	
	</div> <br/>
	<b><i><c:out value="${projectName}"/></i> backlog history</b><br/>
<a href="${path}/web/project/<c:out value="${projectId}"/>/backlog/history/<c:out value="${translationNewer}"/>">newer</a>
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
					<td><c:out value="${snapshot.projectRevision}"/></td>
					<td style="color: grey">
					<a
						href="${path}/web/project/<c:out value="${projectId}"/>/backlog/r<c:out value="${snapshot.revision}"/>/view">r. <c:out value="${snapshot.revision}"/></a>
						<c:if test="${snapshot.revertedFromRevision!=0}">
						<br/>
						
						<i>Reverted from r. <c:out value="${snapshot.revertedFromRevision}"/></i>
						</c:if>
						</td>
						<td><c:out value="${snapshot.dt}"/></td>
						<td>
							<a
						href="${path}/web/project/<c:out value="${projectId}"/>/backlog/r<c:out value="${snapshot.revision}"/>/revert">restore this version</a>
							
							</td>
<%-- 							<td style="color: grey"><i><c:out value="${snapshot.backlogSnapshot.info}"/></i></td> --%>
				</tr>
	</c:forEach>
		
		</tbody>
	</table>
<a href="${path}/web/project/<c:out value="${projectId}"/>/backlog/history/<c:out value="${translationOlder}"/>">older</a>
<br/>
<br/>
</body>
</html>