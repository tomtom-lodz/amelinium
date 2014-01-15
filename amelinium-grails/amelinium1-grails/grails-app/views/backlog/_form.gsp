<%@ page import="amelinium1.grails.Backlog" %>
<%@ page import="amelinium1.grails.Comment" %>
<ol class="show">	
		<li class="text offset-bottom">
				<!--<ckeditor:editor name="text" toolbar="custom">${backlogInstance.text?.decodeHTML()}</ckeditor:editor>-->
				<textarea id="text-area" name="text"><g:fieldValue bean="${backlogInstance}" field="text"/></textarea>
		</li>
		<li class="comment">
				<textarea name="comment" placeholder="Write a comment..."></textarea>
		</li>		
</ol>
