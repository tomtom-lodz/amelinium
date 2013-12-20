<%@ page import="amelinium1.grails.Project" %>



			<div class="control-group fieldcontain ${hasErrors(bean: projectInstance, field: 'name', 'error')} required">
				<label for="name" class="control-label"><g:message code="project.name.label" default="Name" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<g:textField name="name" maxlength="15" required="" value="${projectInstance?.name}"/>
					<span class="help-inline">${hasErrors(bean: projectInstance, field: 'name', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: projectInstance, field: 'revision', 'error')} ">
				<label for="revision" class="control-label"><g:message code="project.revision.label" default="Revision" /></label>
				<div class="controls">
					<g:select id="revision" name="revision.id" from="${amelinium1.grails.Revision.list()}" optionKey="id" value="${projectInstance?.revision?.id}" class="many-to-one" noSelection="['null': '']"/>
					<span class="help-inline">${hasErrors(bean: projectInstance, field: 'revision', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: projectInstance, field: 'status', 'error')} ">
				<label for="status" class="control-label"><g:message code="project.status.label" default="Status" /></label>
				<div class="controls">
					<g:textField name="status" value="${projectInstance?.status}"/>
					<span class="help-inline">${hasErrors(bean: projectInstance, field: 'status', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: projectInstance, field: 'revisions', 'error')} ">
				<label for="revisions" class="control-label"><g:message code="project.revisions.label" default="Revisions" /></label>
				<div class="controls">
					
<ul class="one-to-many">
<g:each in="${projectInstance?.revisions?}" var="r">
    <li><g:link controller="revision" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="revision" action="create" params="['project.id': projectInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'revision.label', default: 'Revision')])}</g:link>
</li>
</ul>

					<span class="help-inline">${hasErrors(bean: projectInstance, field: 'revisions', 'error')}</span>
				</div>
			</div>

