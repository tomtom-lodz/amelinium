<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="chart_history_tab_content" >
<b>CHART HISTORY</b>
<br />
<a
	href="${pageContext.request.contextPath}/web/project<c:out value="${projectId}"/>/chart/history<c:out value="${translationNewer}"/>">newer</a>
&nbsp;
<table cellspacing="10">
	<tbody>
		<tr>
			<th>Project Revision</th>
			<th>Chart Revision</th>
			<th>Date</th>
			<th>Operations</th>
		</tr>
		<c:forEach var="snapshot" items="${history}">

			<tr>
				<td><c:out value="${snapshot.revision}" /></td>
				<td style="color: grey"><a
					href="${pageContext.request.contextPath}/web/project<c:out value="${projectId}"/>/chart/r<c:out value="${snapshot.chartSnapshot.revision}"/>">r.
						<c:out value="${snapshot.chartSnapshot.revision}" />
				</a> <br /> <i><c:out value="${snapshot.chartSnapshot.info}" /></i></td>
				<td><c:out value="${snapshot.backlogSnapshot.date}" /></td>
				<td><a
					href="${pageContext.request.contextPath}/web/project<c:out value="${projectId}"/>/chart/r<c:out value="${snapshot.chartSnapshot.revision}"/>/revert">restore
						this version</a></td>
			</tr>
		</c:forEach>

	</tbody>
</table>
<a
	href="${pageContext.request.contextPath}/web/project<c:out value="${projectId}"/>/chart/history/<c:out value="${translationOlder}"/>">older</a>
<br />
<br />
</div>