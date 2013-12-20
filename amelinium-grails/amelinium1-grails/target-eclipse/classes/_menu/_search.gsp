<g:form controller="tech" action="search" method="post" class="navbar-form navbar-left" >
	<div class="form-group fieldcontain text-center">
		<input name="query" type="text" class="form-control search-query" placeholder="${message(code: 'search.placeholder', default: 'Search ...')}" value="${query}">
	</div>
</g:form>
