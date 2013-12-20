<%@ page import="amelinium1.grails.Project" %>



<div class="fieldcontain ${hasErrors(bean: projectInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="project.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${projectInstance?.name}"/>
</div>

<%--<div class="fieldcontain ${hasErrors(bean: projectInstance, field: 'backlog', 'error')} required">
	<label for="backlog">
		<g:message code="project.backlog.label" default="Backlog" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="backlog" name="backlog.id" from="${amelinium1.grails.Backlog.list()}" optionKey="id" required="" value="${projectInstance?.backlog?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: projectInstance, field: 'csv', 'error')} required">
	<label for="csv">
		<g:message code="project.csv.label" default="Csv" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="csv" name="csv.id" from="${amelinium1.grails.Csv.list()}" optionKey="id" required="" value="${projectInstance?.csv?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: projectInstance, field: 'revision', 'error')} required">
	<label for="revision">
		<g:message code="project.revision.label" default="Revision" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="revision" name="revision.id" from="${amelinium1.grails.Revision.list()}" optionKey="id" required="" value="${projectInstance?.revision?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: projectInstance, field: 'revisions', 'error')} ">
	<label for="revisions">
		<g:message code="project.revisions.label" default="Revisions" />
		
	</label>
	<g:select name="revisions" from="${amelinium1.grails.Revision.list()}" multiple="multiple" optionKey="id" size="5" value="${projectInstance?.revisions*.id}" class="many-to-many"/>
</div>

--%>