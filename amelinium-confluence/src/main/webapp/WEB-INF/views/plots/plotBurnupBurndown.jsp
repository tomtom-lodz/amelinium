<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
<title>Aaaaaa</title>
<link class="include" rel="stylesheet" type="text/css" href="<c:url value="/resources/jqplot/jquery.jqplot.min.css"/>" />
<!--[if lt IE 9]><script language="javascript" type="text/javascript" src="<c:url value="/resources/jqplot/excanvas.js"/>"></script><![endif]-->
<script class="include" language="javascript" type="text/javascript" src="<c:url value="/resources/jqplot/jquery.min.js"/>"></script>
</head>
<body>

<script class="include" type="text/javascript" src="<c:url value="/resources/jqplot/jquery.jqplot.min.js"/>"></script>

<div id="<c:out value="${chartName1}"/>" style="height:600px; width:1024px;"></div>
<script class="code" type="text/javascript">
<c:out value="${chartBody1}" escapeXml="false"/>
</script>

<div id="<c:out value="${chartName2}"/>" style="height:600px; width:1024px;"></div>
<script class="code" type="text/javascript">
<c:out value="${chartBody2}" escapeXml="false"/>
</script>

<script class="include" language="javascript" type="text/javascript" src="<c:url value="/resources/jqplot/plugins/jqplot.dateAxisRenderer.min.js"/>"></script>
<script class="include" language="javascript" type="text/javascript" src="<c:url value="/resources/jqplot/plugins/jqplot.highlighter.min.js"/>"></script>
<script class="include" language="javascript" type="text/javascript" src="<c:url value="/resources/jqplot/plugins/jqplot.cursor.min.js"/>"></script>
<script class="include" language="javascript" type="text/javascript" src="<c:url value="/resources/jqplot/plugins/jqplot.enhancedLegendRenderer.js"/>"></script>

</body>
</html>