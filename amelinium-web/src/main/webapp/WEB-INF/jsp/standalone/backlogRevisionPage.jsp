<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title><c:out value="${projectName}"/> Backlog - Revision <c:out value="${backlog.revision}"/> </title>
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
	
	</div>

<div id="edit_div" style="width: 900px; margin-left: 870px">
	<a href="${path}/web/project/<c:out value="${projectId}"/>/backlog/r<c:out value="${backlog.revision}"/>/revert">restore this version</a>
</div>

<b><i><c:out value="${projectName}"/> Backlog - Revision <c:out value="${backlog.revision}"/></i></b></br>
	<pre><c:out value="${backlog.content}" escapeXml="false" /></pre>
</body>
</html>