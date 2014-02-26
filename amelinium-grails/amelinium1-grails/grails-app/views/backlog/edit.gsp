<%@ page import="amelinium1.grails.Backlog"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<g:set var="entityName"
	value="${message(code: 'backlog.label', default: 'Backlog')}" />
<g:set var="projectName"
	value="${message(code: 'project.label', default: 'Project')}" />
<title><g:message code="default.edit.label" args="[entityName]" /></title>
<g:javascript src="../tinymce/js/tinymce/tinymce.min.js"/>
<r:script>
    tinyMCE.init({
        mode: "exact",
        elements:"text-area",
        resize: false,
        relative_urls : true,
        content_css : "../../static/css/tinymce_body.css",
        menubar : false,
        plugins: "code table link preview",
        tools: "inserttable",
        toolbar : "undo redo | header1 paragraph | bullist outdent indent | italic strikethrough | link table | code preview | helpme",
        setup: function(editor) {
        editor.addButton('header1', {
            text: 'Release',
            icon: false,
            onclick: function() {
                editor.execCommand('FormatBlock', false, 'h1');
            }
        });
        editor.addButton('paragraph', {
            text: 'Feature',
            icon: false,
            onclick: function() {
                editor.execCommand('FormatBlock', false, 'p');
            }
        });
        editor.addButton('helpme', {
            text: 'I HAVE NO IDEA WHAT IM DOING!',
            icon: false,
            onclick: function() {
                editor.windowManager.open({
    				title: "Help",
    				url: '../../static/help/help.html',
    				width: 800,
    				height: 600
				});
            }
        });
    	},
        theme: "modern",
        statusbar : false,
        fix_list_elements : true,
        plugin_preview_width : "800",
        plugin_preview_height : "600"
    });
</r:script>
</head>
<body>
	<div class="content">
		<div class="edit-wrapper offset-left offset-bottom">
			<ol class="breadcrumb">
				<li><a href="${createLink(uri: '/')}"><g:message
							code="default.home.label" default="home" /></a></li>
				<li><g:link class="list" action="show"
						id="${projectInstance?.id}">
						${projectInstance?.name} - backlog
				</g:link></li>
				<li></li>
			</ol>
			<h1>Edit - ${projectInstance?.name} - backlog</h1>
		</div>
		<g:if test="${flash.message}">
			<div class="message" role="status">
				${flash.message}
			</div>
		</g:if>
		<g:hasErrors bean="${backlogInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${backlogInstance}" var="error">
					<li
						<g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
							error="${error}" /></li>
				</g:eachError>
			</ul>
		</g:hasErrors>
		<g:form method="post">
			<g:hiddenField name="id" value="${projectInstance?.id}" />
			<g:hiddenField name="version" value="${projectInstance?.version}" />
			<fieldset class="form">
				<g:render template="form" />
			</fieldset>
			<fieldset class="form-buttons pull-right">
				<g:actionSubmit class="btn btn-primary" action="update"
					value="${message(code: 'default.button.update.label', default: 'Update')}" />
				<g:actionSubmit class="btn btn-success" action="save"
					value="${message(code: 'default.button.save.label', default: 'Save')}" />
				<g:link class="cancel" action="show" id="${projectInstance?.id}">
					<g:message code="default.button.cancel.label" default="Cancel" />
				</g:link>
			</fieldset>
		</g:form>
	</div>
	<span class="for-footer"></span>
	<g:javascript src="bg.js"/>
	<g:if test="${flash.message}">
		<g:javascript src="text-area-flash.js"/>
	</g:if>
	<g:else>
		<g:javascript src="text-area.js"/>
	</g:else>
</body>
</html>
