<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<form:form action="editChart" method="post" id="chartedit">
	<table cellpadding="1" cellspacing="1">
		<tr>
			<td><b>Wiki Markup</b></td>
		</tr>
		<tr>
			<td><textarea name="editbox" id="editbox" cols="100" rows="50"></textarea></td>
		</tr>
		<tr>
			<td align="right"><input type="button" value="Save" onclick='$("#chart_tab_content").load("${pageContext.request.contextPath}/planning/chart");'></input></td>
		
		</tr>
	</table>
</form:form>