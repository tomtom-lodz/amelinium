<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>Edit <c:out value="${project.name}" /> Chart
</title>
</head>

<body>
	<b><c:out value="${project.name}" /></b> Chart
	<br />
	<p style="color: red"><b><i><c:out value="${message}" escapeXml="false" /></i></b></p>	
	<form:form action="view" method="post">
		<form:textarea name="editbox" id="editbox" path="text" cols="100"
			rows="50" />
		<br />
		<input type="submit" value="Update chart" name="_updateChart" />
		<br />
	</form:form>
</body>
</html>