<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/jquery-ui-1.8.21.custom.css"
	type="text/css" media="all" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/google_jsapi.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-ui-1.8.21.custom.min.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Planning Tool</title>
</head>
<body>
	<div id="mainLayout">
		<ul>
			<li><a href="${pageContext.request.contextPath}/web/project<c:out value="${project.id}"/>/chart" title="chart"><span>Chart</span></a></li>
			<li><a href="${pageContext.request.contextPath}/web/project<c:out value="${project.id}"/>/backlog" title="backlog"><span>Backlog</span></a></li>
			<li><a href="${pageContext.request.contextPath}/web/project<c:out value="${project.id}"/>/chart/history0" title="chart-history"><span>Chart History</span></a></li>
			<li><a href="${pageContext.request.contextPath}/web/project<c:out value="${project.id}"/>/backlog/history" title="backlog-history"><span>Backlog History</span></a></li>
		</ul>
	</div>

	<script type="text/javascript">
		google.load('visualization', '1', {
			packages : [ 'table','corechart' ]
		});
		$(function() {
			var backlogHistoryHref="${pageContext.request.contextPath}/web/project<c:out value="${project.id}"/>/backlog/history";
			$("#mainLayout").tabs();
			$("#backlog-history").load(backlogHistoryHref);	
		});
		$("#backlog-history").click(function(){
			location.hash = $("#backlog-history").attr("name");
		});
	</script>
</body>
</html>