<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title><g:layoutTitle default="Amelinium" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<g:layoutHead />
<r:require modules="bootstrap" />
<r:require module="jquery-ui"/>
<r:layoutResources />
<link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}"
	type="text/css">
<link rel="stylesheet" href="${resource(dir: 'css', file: 'mobile.css')}"
	type="text/css">
<link rel="stylesheet"
	href="${resource(dir: 'js/jqplot', file: 'jquery.jqplot.min.css')}"
	type="text/css">
</head>
<body>
	<nav class="navbar navbar-inner" role="navigation">
		<g:link class="navbar-brand" url="${createLink(uri: '/')}">
			<g:img dir="images" file="tomtom.png" />
		</g:link>
		<div class="pull-right">
			<ul class="nav navbar-nav">
				<sec:ifLoggedIn>
					<li><g:link class="white-link" url="${createLink(uri: '/')}">
							${sec.loggedInUserInfo(field:"dn").split(",")[0].substring(3)}
						</g:link></li>
				</sec:ifLoggedIn>
				<li><a class="white-link" href="${createLink(uri: '/systeminfo')}">Info</a></li>
				<li class="dropdown "><a href="#" class="dropdown-toggle white-link"
					data-toggle="dropdown">Panel <b class="caret"></b>
				</a>
					<ul class="dropdown-menu">
						<sec:ifNotLoggedIn>
							<li><g:link controller="login" action="auth">Log in</g:link></li>
						</sec:ifNotLoggedIn>
						<sec:ifLoggedIn>
							<li><g:link controller="logout">Log out</g:link></li>
						</sec:ifLoggedIn>
					</ul></li>
			</ul>
		</div>
	</nav>
	<g:layoutBody />
	<g:javascript library="application" />
	<r:layoutResources />
	<footer class="navbar navbar-default navbar-fixed-bottom">
		<div class="container">
			<p class="navbar-text pull-left">
				Page created using
				<g:link url="http://getbootstrap.com/">bootstrap v3.0.3 </g:link>
				by Daniel Hajduk
			</p>
		</div>
	</footer>
</body>
</html>
