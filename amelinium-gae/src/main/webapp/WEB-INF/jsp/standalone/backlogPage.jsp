<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title><c:out value="${project.name}"/> Backlog</title>
</head>

<body>
	<div id="top_div" style="width: 900px">
	<a href="${pageContext.request.contextPath}/projects/">go back to project list</a> <br/> 
	<a href="${pageContext.request.contextPath}/project/<c:out value="${project.id}"/>/chart/view">view corresponding chart</a>
	
	</div>
	<div id="top_div" style="width: 900px; margin-left: 870px">
	<a href="${pageContext.request.contextPath}/project/<c:out value="${project.id}"/>/backlog/edit">EDIT</a>
</div>
	<c:out value="${project.backlogHtml}" escapeXml="false" />
</body>
</html>