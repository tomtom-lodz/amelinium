
<%@ page import="amelinium1.grails.Backlog"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<g:set var="entityName"
	value="${message(code: 'backlog.label', default: 'Backlog')}" />
<g:set var="projectName"
	value="${message(code: 'project.label', default: 'Project')}" />
<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
	<div class="scroller">
		<div class="wrapper">
			<div>
				<ol class="breadcrumb">
					<li><a href="${createLink(uri: '/')}"><g:message
								code="default.home.label" default="home" /></a></li>
					<li></li>
				</ol>
				<div class="buttons">
					<g:if test="${projectInstance?.status=='Open'}">
						<g:link class="btn btn-primary pull-right" action="edit"
							id="${projectInstance?.id}">
							<g:message code="default.button.edit.label" default="Edit" />
						</g:link>
					</g:if>
					<g:link class="btn btn-primary pull-right offset-right"
						action="listRevision" id="${projectInstance?.id}">
						<g:message code="default.button.history.label" default="History" />
					</g:link>
					<g:link class="btn btn-primary pull-right offset-right" controller="csv"
						action="show" id="${projectInstance?.id}">
						<g:message code="default.button.csv.label" default="Csv" />
					</g:link>
					<g:link class="btn btn-primary pull-right offset-right"
						action="recalculate" id="${projectInstance?.id}">
						<g:message code="default.button.recalculate.label"
							default="Update backlog and csv" />
					</g:link>
					<g:link class="btn btn-success pull-right offset-right" controller="csv"
						action="plotFromProject" id="${projectInstance?.id}">
						<g:message code="default.button.plot.label" default="View plot" />
					</g:link>
				</div>
			</div>
			<div class="content">
				<g:if test="${flash.message}">
					<div class="message" role="status">
						${flash.message}
					</div>
				</g:if>
				<g:if test="${backlogInstance?.state!='Recalculated'}">
					<div class="errors" role="status">
						<ul>
							<li>Backlog is not recalculated</li>
						</ul>
					</div>
				</g:if>
				<h1>
					${projectInstance?.name}
					- backlog
					<g:if test="${projectInstance.revision.backlog.ver!=backlogInstance.ver}"> - old revision</g:if>
				</h1>
				<ol class="show">
					<li class="text"><span class="created">Edited <g:formatDate
								format="yyyy-MM-dd HH:mm" date="${backlogInstance?.dateCreated}" />, by
							<b> ${backlogInstance.editedBy}
						</b></span>
						<div class="value offset-top">
							${text}
						</div></li>
				</ol>
			</div>
		</div>
	</div>
	<g:javascript src="overflow-scroller.js" />
</body>
</html>
