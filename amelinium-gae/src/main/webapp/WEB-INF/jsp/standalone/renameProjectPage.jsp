<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>Rename Project</title>
</head>
<body>
<u><h3 align="left">Rename Project</h3></u>
<left><form:form method="POST" action="rename">
New name: <form:input path="projectName"></form:input><br />
<input type="submit" value="Save" name="rename"></input><br />
</form:form></left>
</body>
</html>