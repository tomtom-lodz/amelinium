<%@ page import="amelinium1.grails.Comment" %>



			<div class="control-group fieldcontain ${hasErrors(bean: commentInstance, field: 'name', 'error')} ">
				<label for="name" class="control-label"><g:message code="comment.name.label" default="Name" /></label>
				<div class="controls">
					<g:textField name="name" value="${commentInstance?.name}"/>
					<span class="help-inline">${hasErrors(bean: commentInstance, field: 'name', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: commentInstance, field: 'value', 'error')} ">
				<label for="value" class="control-label"><g:message code="comment.value.label" default="Value" /></label>
				<div class="controls">
					<g:textField name="value" value="${commentInstance?.value}"/>
					<span class="help-inline">${hasErrors(bean: commentInstance, field: 'value', 'error')}</span>
				</div>
			</div>

