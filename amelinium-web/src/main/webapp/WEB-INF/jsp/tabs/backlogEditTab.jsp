<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<b>${project.name}</b> Backlog<br/>
	<form:form action="project${project.id}/backlog" method="post">
		<form:textarea name="editbox" id="editbox" path="text" cols="100"
			rows="50" />
		<br />
		<form:checkbox path="allowMultilineFeatures"/>Allow multiline features<br />
		<input type="submit" value="Update backlog" name="_updateBacklog" onclick='$("#backlog_tab_content").load("${pageContext.request.contextPath}/web/project${project.id}/backlog");'/>
		<p style="color: red"><b><i>${message}</i></b></p>    	
		<br />
	</form:form>