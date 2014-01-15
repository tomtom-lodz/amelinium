<%@ page import="amelinium1.grails.Project"%>

<div class="form-plot offset-left ${hasErrors(bean: projectInstance, field: 'name', 'error')} " >
	<label class="offset-top" for="sprintLength">
		<g:message code="project.sprint.length.label" default="Sprint length:" />
	</label>
	<g:textField type="number" name="sprintLength" value="${projectInstance?.sprintLength}"/>
	<label class="" for="velocity">
		<g:message code="project.velocity.label" default="Velocity:" />
	</label>
	<g:textField type="number" name="velocity" value="${projectInstance?.velocity}"/>
	<label class="" for="scopeIncrease">
		<g:message code="project.scope.increase.label" default="Scope increase:" />
	</label>
	<g:textField type="number" name="scopeIncrease" value="${projectInstance?.scopeIncrease}"/>
</div>