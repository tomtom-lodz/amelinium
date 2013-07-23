<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>Edit <c:out value="${project.name}"/> Backlog</title>
</head>

<body>
	<b><c:out value="${project.name}"/></b> Backlog<br/>
	<form:form action="view" method="post">
		<form:textarea name="editbox" id="editbox" path="text" cols="100"
			rows="50" />
		<br />
		<form:checkbox path="allowMultilineFeatures"/>Allow multiline features<br />
		<input type="submit" value="Update backlog" name="_updateBacklog" />
		<p style="color: red"><b><i><c:out value="${message}" escapeXml="false" /></i></b></p>    	
		<br />
	</form:form>
</body>
</html>

