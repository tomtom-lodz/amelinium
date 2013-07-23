<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>New Project - Name</title>
</head>
<body>
<u><h3 align="left">New Project</h3></u>
<left><form:form method="POST" action="new">
Project name: <form:input path="name"></form:input><br />
<form:checkbox path="allowMultilineFeatures"/>Allow multiline features<br />
<input type="submit" value="Next" name="_target1"></input>
<input type="submit" value="Cancel" name="_cancel"></input><br />
</form:form></left>
</body>
</html>