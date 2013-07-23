<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title><c:out value="${project.name}"/> Chart - Revision <c:out value="${snapshot.chartSnapshot.revision}"/> </title>
</head>

<body>
<b><i><c:out value="${project.name}"/> Chart - Revision <c:out value="${snapshot.chartSnapshot.revision}"/></i></b></br>
	<pre><c:out value="${snapshot.chartSnapshot.wikiMarkupContent}" escapeXml="false" /></pre>
</body>
</html>