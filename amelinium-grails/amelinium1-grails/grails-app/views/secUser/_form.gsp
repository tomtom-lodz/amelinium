<%@ page import="amelinium1.grails.SecUser"%>
<div
	class="control-group fieldcontain ${hasErrors(bean: secUserInstance, field: 'username', 'error')} required">
	<label for="username" class="control-label"><g:message
			code="secUser.username.label" default="Username" /><span
		class="required-indicator">*</span></label>
	<div class="controls">
		<g:textField name="username" required="" value="${secUserInstance?.username}" />
		<span class="help-inline"> ${hasErrors(bean: secUserInstance, field: 'username', 'error')}
		</span>
	</div>
</div>
<div
	class="control-group fieldcontain ${hasErrors(bean: secUserInstance, field: 'password', 'error')} required">
	<label for="password" class="control-label"><g:message
			code="secUser.password.label" default="Password" /><span
		class="required-indicator">*</span></label>
	<div class="controls">
		<g:passwordField type="password" name="password" required=""
			value="${secUserInstance?.password}" />
		<span class="help-inline"> ${hasErrors(bean: secUserInstance, field: 'password', 'error')}
		</span>
	</div>
</div>
