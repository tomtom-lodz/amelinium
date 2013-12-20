<%@ page import="amelinium1.grails.Backlog" %>
<%@ page import="amelinium1.grails.Comment" %>
<div class="fieldcontain ${hasErrors(bean: backlogInstance, field: 'text', 'error')} ">
	<ol class="property-list backlog">	
		<li class="fieldcontain">
				<ckeditor:editor name="text" toolbar="custom" >${backlogInstance.text?.decodeHTML()}</ckeditor:editor>
		</li>
		<li class="fieldcontain">
				<span id="comment-label" class="property-label"><g:message code="backlog.comment.label" default="Comment" /></span>
				<textarea name="comment" class="property-area-comment"><g:fieldValue bean="${backlogInstance.comment}" field="text"/></textarea>
		</li>		
	</ol>
</div>
