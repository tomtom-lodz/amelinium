<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
<title>Charts</title>
<link class="include" rel="stylesheet" type="text/css" href="<c:url value="/resources/jqplot/jquery.jqplot.min.css"/>" />
<!--[if lt IE 9]><script language="javascript" type="text/javascript" src="<c:url value="/resources/jqplot/excanvas.js"/>"></script><![endif]-->
<script class="include" language="javascript" type="text/javascript" src="<c:url value="/resources/jqplot/jquery.min.js"/>"></script>
</head>
<body>

<script class="include" type="text/javascript" src="<c:url value="/resources/jqplot/jquery.jqplot.min.js"/>"></script>

Sprint Length: <c:out value="${sprintLength}"/> days, Velocity: <c:out value="${velocity}"/>, Scope increase: <c:out value="${scopeIncrease}"/>

<c:if test="${renderBurnup}">
<div id="<c:out value="${chartName1}"/>" style="height:600px; width:100%;"></div>
<button class="button-reset-zoom-${chartName1}">Reset Zoom</button>
You can also double-click to reset the zoom.
<!--
Legend placement:
<select class="select-legend-placement-${chartName1}">
<option value="outside">Outside</option>
<option value="insideNe">Inside NE</option>
<option value="insideNw">Inside NW</option>
<option value="insideSe">Inside SE</option>
<option value="insideSw">Inside SW</option>
</select>
-->
<script class="code" type="text/javascript">
<c:out value="${chartBody1}" escapeXml="false"/>
</script>
</c:if>

<br/>

<c:if test="${renderBurnup}">
<br/>
<c:out value="${burnupTable}" escapeXml="false"/>
<br/>
</c:if>

<c:if test="${renderBurndown}">
<div id="<c:out value="${chartName2}"/>" style="height:600px; width:100%;"></div>
<button class="button-reset-zoom-${chartName2}">Reset Zoom</button>
You can also double-click to reset the zoom.
<!--
Legend placement:
<select class="select-legend-placement-${chartName2}">
<option value="outside">Outside</option>
<option value="insideNe">Inside NE</option>
<option value="insideNw">Inside NW</option>
<option value="insideSe">Inside SE</option>
<option value="insideSw">Inside SW</option>
</select>
 -->
<script class="code" type="text/javascript">
<c:out value="${chartBody2}" escapeXml="false"/>
</script>
</c:if>

<script class="include" language="javascript" type="text/javascript" src="<c:url value="/resources/jqplot/plugins/jqplot.dateAxisRenderer.min.js"/>"></script>
<script class="include" language="javascript" type="text/javascript" src="<c:url value="/resources/jqplot/plugins/jqplot.highlighter.min.js"/>"></script>
<script class="include" language="javascript" type="text/javascript" src="<c:url value="/resources/jqplot/plugins/jqplot.cursor.min.js"/>"></script>
<script class="include" language="javascript" type="text/javascript" src="<c:url value="/resources/jqplot/plugins/jqplot.enhancedLegendRenderer.js"/>"></script>

</body>
</html>