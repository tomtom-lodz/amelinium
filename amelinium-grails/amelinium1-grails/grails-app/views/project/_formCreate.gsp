<%@ page import="amelinium1.grails.Project" %>

<div class="fieldcontain ${hasErrors(bean: projectInstance, field: 'name', 'error')} ">
	<div class="field">
		<g:textField name="name" value="${projectInstance?.name}"/>
	</div>
	<label class="block offset-top" for="sprintLength">
		<g:message code="project.sprint.length.label" default="Sprint length:" />
	</label>
	<g:textField type="number" name="sprintLength" value="${projectInstance?.sprintLength}"/> days
	<label class="block" for="velocity">
		<g:message code="project.velocity.label" default="Velocity:" />
	</label>
	<g:textField type="number" name="velocity" value="${projectInstance?.velocity}"/> per sprint
	<label class="block" for="scopeIncrease">
		<g:message code="project.scope.increase.label" default="Scope increase:" />
	</label>
	<g:textField type="number" name="scopeIncrease" value="${projectInstance?.scopeIncrease}"/> per sprint
</div>

