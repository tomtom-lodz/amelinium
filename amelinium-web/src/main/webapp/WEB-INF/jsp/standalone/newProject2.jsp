<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>New Project - Chart</title>
</head>

<body>
<u><h3 align="left">New Project</h3></u>
	<h3 align="left">CHART:</h3>
	<form:form method="POST" action="new">
		<form:textarea name="editbox" id="editbox" path="chartContent"
			cols="100" rows="50" />
		<br />
		<input type="submit" value="Previous" name="_target1" />
		<input type="submit" value="Finish" name="_finish" />
		<input type="submit" value="Cancel" name="_cancel" />
		<br />
	</form:form>
</body>
</html>