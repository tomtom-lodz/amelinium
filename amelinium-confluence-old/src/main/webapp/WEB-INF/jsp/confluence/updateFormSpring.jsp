<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>Correct Backlog</title>
</head>
<body>
<u><h3 align="left">Correct Backlog</h3></u>
<left><form:form action="updateBacklog" method="post">
<table cellpadding="1" cellspacing="1">
<tr>
<td>title</td>
<td><form:input path="title"></form:input></td>
</tr>
<tr>
<td>space</td>
<td><form:input path="space"></form:input></td>
</tr>
<tr>
<td></td><td align="left"><form:checkbox path="allowMultilineFeatures"/>Allow multiline features</td>
</tr>
<tr>
<td></td><td align="right"><input type="submit" value="Submit"></input></td>
</tr>

<tr><td colspan="2"></td></tr>
</table>
</form:form></left>
</body>
</html>