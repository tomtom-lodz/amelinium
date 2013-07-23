<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title><c:out value="${project.name}"/> Backlog - Revision <c:out value="${snapshot.backlogSnapshot.revision}"/> </title>
</head>

<body>
<b><i><c:out value="${project.name}"/> Backlog - Revision <c:out value="${snapshot.backlogSnapshot.revision}"/></i></b></br>
	<pre><c:out value="${snapshot.backlogSnapshot.wikiMarkupContent}" escapeXml="false" /></pre>
</body>
</html>