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
	<label class="block" for="status">
		<g:message code="project.status.label" default="Status:" />
	</label>
	<g:select name="status" from="${projectInstance.constraints.status.inList}" value="${projectInstance.status}"/>
	<label class="block" for="cumulative">
		<g:message code="project.isCumulative.label" default="IsCumulative:" />
	</label>
	<g:select name="cumulative" from="${projectInstance.constraints.isCumulative.inList}" value="${projectInstance.isCumulative}"/>
	<label class="block" for="multilineFeature">
		<g:message code="project.multilineFeature.label" default="MultilineFeature:" />
	</label>
	<g:select name="multilineFeature" from="${projectInstance.constraints.multilineFeature.inList}" value="${projectInstance.multilineFeature}"/>
	<label class="block" for="addNewFeatureGroups">
		<g:message code="project.addNewFeatureGroups.label" default="AddNewFeatureGroups:" />
	</label>
	<g:select name="addNewFeatureGroups" from="${projectInstance.constraints.addNewFeatureGroups.inList}" value="${projectInstance.addNewFeatureGroups}"/>
</div>