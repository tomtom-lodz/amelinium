<div id="backlog_tab_content" >
<div id="top_div" style="width: 900px; margin-left: 870px">
	<input type="button" value="Edit" onclick='$("#backlog_tab_content").load("${pageContext.request.contextPath}/web/project${project.id}/backlog/edit");'></input></div>
${project.cache.htmlBacklog}
</div>