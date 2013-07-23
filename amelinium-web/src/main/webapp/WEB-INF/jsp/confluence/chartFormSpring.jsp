<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>Update Chart</title>
</head>
<body>
<form:form action="updateChart" method="post">
<table cellpadding="1" cellspacing="1">
<tr><td colspan="2"><b>CHART</b></td></tr>
<tr>
<td>title</td>
<td><form:input path="chartTitle"></form:input></td>
</tr>
<tr>
<td>space</td>
<td><form:input path="chartSpace"></form:input></td>
</tr>

<tr><td colspan="2"><b>BACKLOG</b></td></tr>
<tr>
<td>title</td>
<td><form:input path="backlogTitle"></form:input></td>
</tr>
<tr>
<td>space</td>
<td><form:input path="backlogSpace"></form:input></td>
</tr>
<tr>
<td></td><td align="left"><form:checkbox path="allowMultilineFeatures"/>Allow multiline features</td>
</tr>
<tr>
<td></td><td align="right"><input type="submit" value="Submit"></input></td>
</tr>

<tr><td colspan="2"></td></tr>
</table>
</form:form>

</body>
</html>