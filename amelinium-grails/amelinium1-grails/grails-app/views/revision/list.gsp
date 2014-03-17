<%@ page import="amelinium1.grails.Revision"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<g:set var="entityName"
	value="${message(code: 'revision.label', default: 'Revision')}" />
<g:set var="projectName"
	value="${message(code: 'project.label', default: 'Project')}" />
<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
	<div class="scroller">
		<div class="wrapper">
			<ol class="breadcrumb">
				<li><a href="${createLink(uri: '/')}"><g:message
							code="default.home.label" default="home" /></a></li>
				<g:if test="${display=='project'}">
				</g:if>
				<g:elseif test="${display=='backlog'}">
					<li><g:link controller="backlog" action="show"
							id="${projectInstance?.id}">
							${projectInstance?.name} - backlog
				</g:link></li>
				</g:elseif>
				<g:else>
					<li><g:link controller="csv" action="show"
							id="${projectInstance?.id}">
							${projectInstance?.name} - csv
				</g:link></li>
				</g:else>
				<li></li>
			</ol>
			<g:if test="${display=='project'}">
				<div class="buttons">
					<g:link class="btn btn-primary pull-right"
						url="${createLink(uri: '/')}">
						<g:message code="default.go.back.label" default="Go back" />
					</g:link>
				</div>
				<h1>
					${projectInstance?.name}
					- history
				</h1>
			</g:if>
			<g:if test="${display=='backlog'}">
				<div class="buttons">
					<g:link class="btn btn-primary pull-right" controller="backlog"
						action="show" id="${projectInstance.id}">
						<g:message code="default.go.back.label" default="Go back" />
					</g:link>
				</div>
				<h1>
					${projectInstance?.name}
					- backlog - history
				</h1>
			</g:if>
			<g:if test="${display=='csv'}">
				<div class="buttons">
					<g:link class="btn btn-primary pull-right" controller="csv"
						action="show" id="${projectInstance.id}">
						<g:message code="default.go.back.label" default="Go back" />
					</g:link>
				</div>
				<h1>
					${projectInstance?.name}
					- csv - history
				</h1>
			</g:if>
			<div class="content offset-top">
				<g:if test="${flash.message}">
					<div class="message" role="status">
						${flash.message}
					</div>
				</g:if>
				<div class="table-resposive">
					<table class="table history">
						<thead>
							<tr>
								<g:if test="${display=='project'}">
									<th><g:message code="revision.revision.label"
											default="Project / Backlog / Csv revision" /></th>
								</g:if>
								<g:elseif test="${display=='csv'}">
									<th><g:message code="revision.revision.label"
											default="Project / Csv revision" /></th>
								</g:elseif>
								<g:else>
									<th><g:message code="revision.revision.label"
											default="Project / Backlog revision" /></th>
								</g:else>
								<th><g:message code="revision.date.label" default="Created" /></th>
								<th><g:message code="revision.date.label"
										default="Changed By" /></th>
								<th><g:message code="revision.operation.label"
										default="Operations" /></th>
							</tr>
						</thead>
						<tbody>
							<g:if test="${display=='project'}">
								<g:each in="${revisionInstanceList}" status="i"
									var="revisionInstance">
									<tr>
										<g:if test="${i==0}">
											<td>
												${revisionInstance.ver} / ${revisionInstance.backlog.ver} / ${revisionInstance.csv.ver}
												(CURRENT)
											</td>
										</g:if>
										<g:else>
											<td>
												${revisionInstance.ver} / ${revisionInstance.backlog.ver} / ${revisionInstance.csv.ver}
											</td>
										</g:else>
										<td><g:formatDate format="yyyy-MM-dd HH:mm"
												date="${revisionInstance.dateCreated}" /></td>
										<td>
											${revisionInstance.changedBy} <g:if
												test="${revisionInstance.comment!=""}">
												</br>
										(${revisionInstance.comment})								
										</g:if>
										</td>
										<g:if test="${i==0}">
											<td><g:link controller="backlog" action="show"
													id="${projectInstance.id}">View Current backlog</g:link> |
												<g:link controller="csv" action="show"
													id="${projectInstance.id}">View Current csv</g:link></td>
										</g:if>
										<g:else>
											<td><g:link controller="backlog" action="showRevision"
													id="${revisionInstance.backlog.id}">View backlog</g:link> |
												<g:link controller="backlog" action="restore"
													id="${revisionInstance.backlog.id}"
													onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');">Restore backlog</g:link>
												</br> <g:link controller="csv" action="showRevision"
													id="${revisionInstance.csv.id}">View csv</g:link> | <g:link
													controller="csv" action="restore"
													id="${revisionInstance.csv.id}"
													onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');">Restore csv</g:link>
												| <g:link controller="csv" action="plotFromCsv"
													id="${revisionInstance.csv.id}">Plot from revision</g:link>
											</td>
										</g:else>
									</tr>
								</g:each>
							</g:if>
							<g:if test="${display=='csv'}">
								<g:each in="${revisionInstanceList}" status="i"
									var="revisionInstance">
									<tr>
										<g:if test="${i==0}">
											<td>
												${revisionInstance.ver} / ${revisionInstance.csv.ver} (CURRENT)
											</td>
										</g:if>
										<g:else>
											<td>
												${revisionInstance.ver} / ${revisionInstance.csv.ver}
											</td>
										</g:else>
										<td><g:formatDate format="yyyy-MM-dd HH:mm"
												date="${revisionInstance.dateCreated}" /></td>
										<td>
											${revisionInstance.changedBy} <g:if
												test="${revisionInstance.comment!=""}">
												</br>
										(${revisionInstance.comment})								
										</g:if>
										</td>
										<g:if test="${i==0}">
											<td><g:link controller="csv" action="showRevision"
													id="${revisionInstance.csv.id}">View Current csv</g:link></td>
										</g:if>
										<g:else>
											<td><g:link controller="csv" action="showRevision"
													id="${revisionInstance.csv.id}">View</g:link> | <g:link
													controller="csv" action="restore"
													id="${revisionInstance.csv.id}"
													onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');">Restore</g:link>
												| <g:link controller="csv" action="plotFromCsv"
													id="${revisionInstance.csv.id}">Plot from revision</g:link>
											</td>
										</g:else>
									</tr>
								</g:each>
							</g:if>
							<g:if test="${display=='backlog'}">
								<g:each in="${revisionInstanceList}" status="i"
									var="revisionInstance">
									<tr>
										<g:if test="${i==0}">
											<td>
												${revisionInstance.ver} / ${revisionInstance.backlog.ver}
												(CURRENT)
											</td>
										</g:if>
										<g:else>
											<td>
												${revisionInstance.ver} / ${revisionInstance.backlog.ver}
											</td>
										</g:else>
										<td><g:formatDate format="yyyy-MM-dd HH:mm"
												date="${revisionInstance.dateCreated}" /></td>
										<td>
											${revisionInstance.changedBy} <g:if
												test="${revisionInstance.comment!=""}">
												</br>
										(${revisionInstance.comment})								
										</g:if>
										</td>
										<g:if test="${i==0}">
											<td><g:link controller="backlog" action="showRevision"
													id="${revisionInstance.backlog.id}">View Current backlog</g:link></td>
										</g:if>
										<g:else>
											<td><g:link controller="backlog" action="showRevision"
													id="${revisionInstance.backlog.id}">View</g:link> | <g:link
													controller="backlog" action="restore"
													id="${revisionInstance.backlog.id}"
													onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');">Restore</g:link>
											</td>
										</g:else>
									</tr>
								</g:each>
							</g:if>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<g:javascript src="overflow-scroller.js" />
</body>
</html>
