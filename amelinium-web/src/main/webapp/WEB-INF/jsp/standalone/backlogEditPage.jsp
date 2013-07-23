<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>Edit <c:out value="${projectName}"/> Backlog</title>
</head>

<body>

	<c:set var="path" value="${pageContext.request.contextPath}"
		scope="page" />
	<c:if test="${path=='/'}">
		<c:set var="path" value="" scope="page" />
	</c:if>

	<a href="${path}/web/projects/">HOME</a>
<table cellpadding="5px" cellspacing="5px">
<tr>
<td><a href="${path}/web/project/<c:out value="${projectId}"/>/backlog/view">BACKLOG</a></td>
<td><a href="${path}/web/project/<c:out value="${projectId}"/>/chart/view">CHART</a></td>
<td><a href="${path}/web/project/<c:out value="${projectId}"/>/backlog/history/0">BACKLOG HISTORY</a></td>
<td><a href="${path}/web/project/<c:out value="${projectId}"/>/chart/history/0">CHART HISTORY</a></td>
</tr>

</table>

	<b><c:out value="${projectName}"/></b> Backlog<br/>
	<form:form action="view" method="post">
		<form:textarea name="editbox" id="editbox" path="text" cols="100"
			rows="50" />
		<br />
		<form:checkbox path="allowMultilineFeatures"/>Allow multiline features<br />
		<input type="submit" value="Update backlog" name="_updateBacklog" />
		<p style="color: red"><b><i><c:out value="${message}" escapeXml="false" /></i></b></p>    	
		<br />
	</form:form>
</body>
</html>

