<%@ page import="amelinium1.grails.Backlog" %>
<ol class="show">	
		<li class="text offset-bottom">
				<textarea id="text-area" name="text"><g:fieldValue bean="${backlogInstance}" field="text"/></textarea>
		</li>
		<li class="comment">
				<textarea name="comment" placeholder="Write a comment..."></textarea>
		</li>		
</ol>
