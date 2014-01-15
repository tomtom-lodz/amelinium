<html>
<head>
	<title><g:message code="springSecurity.login.title"/></title>
	<meta name="layout" content="main" />

	<g:set var="layout_nomainmenu"		value="${true}" scope="request"/>
	<g:set var="layout_nosecondarymenu"	value="${true}" scope="request"/>
</head>

<body>

<section id="login" class="first">
<div class="row">
	<div class="col-md-3"></div>
	<div class="col-md-6">
		<form role="form" id='loginForm' class='form-horizontal offset-top' action='${postUrl}' method='POST' autocomplete='off'>
				<div class="form-group ${hasErrors(bean: _DemoPageInstance, field: 'name', 'error')} ">
					<h3> Log In </h3>
				</div>
				<g:if test="${params.get('login_error')}">
					<div class="errors" role="status">
						${message(code: 'login.failed.label', default: 'Login or password is incorrect')}
					</div>
				</g:if>
				<div class="form-group ${hasErrors(bean: _DemoPageInstance, field: 'name', 'error')} ">
					<label for='username' class="control-label"><g:message code="springSecurity.login.username.label"/>:</label>
					<div class="controls">
						<input type='text' class='form-control col-md-4' name='j_username' id='username'/>
					</div>
				</div>
	
				<div class="form-group ${hasErrors(bean: _DemoPageInstance, field: 'name', 'error')} ">
					<label for='password' class="control-label"><g:message code="springSecurity.login.password.label"/>:</label>
					<div class="controls">
						<input type='password' class='form-control col-md-4' name='j_password' id='password'/>
					</div>
				</div>
				
				<div id="remember_me_holder" class="form-group">
					<label for='remember_me' class="control-label"><g:message code="springSecurity.login.remember.me.label"/></label>
					<div class="controls">
						<g:checkBox class="form-control col-md-4" name="${rememberMeParameter}" value="${hasCookie}" />
					</div>
				</div>
				<div class="form-group">
					<input type='submit' id="submit" class="btn btn-primary" value='Log In'/>
				</div>
		</form>
	</div>
	<div class="col-md-3"></div>
</div>
</section>

<script type='text/javascript'>
	(function() {
		document.forms['loginForm'].elements['j_username'].focus();
	})();
	changeBg();
</script>

</body>
</html>
