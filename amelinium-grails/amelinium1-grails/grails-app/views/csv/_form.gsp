<%@ page import="amelinium1.grails.Csv" %>

<div class="fieldcontain ${hasErrors(bean: csvInstance, field: 'text', 'error')} ">
	<ol class="property-list backlog">	
		<li class="fieldcontain">
				<ckeditor:editor name="text" toolbar="custom" >${csvInstance.text?.decodeHTML()}</ckeditor:editor>
		</li>
				
		<li class="fieldcontain">
				<span id="comment-label" class="property-label"><g:message code="backlog.comment.label" default="Comment" /></span>
				<textarea name="comment" class="property-area-comment"><g:fieldValue bean="${csvInstance.comment}" field="text"/></textarea>
		</li>		
	</ol>
</div>

