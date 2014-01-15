<%@ page import="amelinium1.grails.Csv" %>

<ol class="show">	
	<li class="text offset-bottom">
			<textarea id="text-area" name="text"><g:fieldValue bean="${csvInstance}" field="text"/></textarea>
	</li>
				
	<li class="comment">
			<textarea name="comment" placeholder="Write a comment..."></textarea>
	</li>		
</ol>

