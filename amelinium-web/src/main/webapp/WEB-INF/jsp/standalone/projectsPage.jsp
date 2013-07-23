<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>AMELINIUM Planning Tool</title>
</head>
<body>
	<c:set var="path" value="${pageContext.request.contextPath}"
		scope="page" />
	<c:if test="${path=='/'}">
		<c:set var="path" value="" scope="page" />
	</c:if>
	<a href="${path}/web/projects/new">NEW PROJECT</a>
	<br><br>

	<b>PROJECT LIST</b>
	<c:if test="${size!=0}">
		<table cellpadding="5px" cellspacing="5px">
			<tbody>
				<tr>
					<!-- 				<th>Id</th> -->
					<th>Name</th>
					<th>Operations</th>
					<th>Created</th>
					<th>Last Modified</th>
				</tr>
				<c:forEach var="p" items="${projects}">
					<tr>
						<%-- 					<td><c:out value="${p.id}" /></td> --%>
						<td><c:out value="${p.name}" />&nbsp;&nbsp;</td>

						<td><a
<%-- 					href="${amelinium.environment.reference.type}/web/project/<c:out value="${p.id}"/>/backlog/view">view --%>
							href="${path}/web/project/<c:out value="${p.id}"/>/backlog/view">view
								backlog</a> &nbsp; <a
							href="${path}/web/project/<c:out value="${p.id}"/>/chart/view">view
								chart</a> &nbsp;&nbsp; <a
							href="${path}/web/project/<c:out value="${p.id}"/>/rename">rename</a>
							&nbsp;&nbsp; <a
							href="${path}/web/project/<c:out value="${p.id}"/>/backlog/history/0">backlog
								history</a> &nbsp; <a
							href="${path}/web/project/<c:out value="${p.id}"/>/chart/history/0">chart
								history</a> &nbsp;&nbsp; <a
							href="${path}/web/project/<c:out value="${p.id}"/>/delete">delete
								project</a></td>
						<td><c:out value="${p.dtCreated}" /></td>
						<td><c:out value="${p.dtLastModified}" /></td>
					</tr>


				</c:forEach>
			</tbody>
		</table>
	</c:if>
</body>
</html>
