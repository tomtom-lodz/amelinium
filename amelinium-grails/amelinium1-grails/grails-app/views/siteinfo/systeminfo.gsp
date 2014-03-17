<html>

<head>
    <title>System info</title>
    <meta name="layout" content="main">
</head>

<body>
<div class="wrapper">
	
	<section id="layout">
	    <h1><g:message code="default.systeminfo.resources"/></h1>
	    <ul class="list-group offset-top">
			<li class="list-group-item">Web Layout: <a href ="http://twitter.github.com/bootstrap/">Bootstrap</a>, from Twitter 
	        	Licensed under the Apache License v2.0. Documentation licensed under CC BY 3.0. 
	        	(@TwBootstrap , http://twitter.github.com/bootstrap/)</li>
			<li class="list-group-item">Icons: <a href ="http://fortawesome.github.com/Font-Awesome">Font Awesome</a>, by Dave Gandy. 
	        	Licensed under the <a href="http://fortawesome.github.com/Font-Awesome/#license">SIL Open Font License, MIT License, and CC BY 3.0 License.</a></li>
	        <li class="list-group-item">Datepicker: <a href ="https://github.com/eternicode/bootstrap-datepicker">Datepicker for Bootstrap</a>, 
	        	Copyright 2012 Stefan Petre, Improvements by Andrew Rowls, Licensed under the Apache License v2.0</li>
			<li class="list-group-item">Flag Icons: <a href="http://www.famfamfam.com/lab/icons/flags/">FamFamFam Flag Icons</a> by Mark James. They are 
				<i>"available for free use for any purpose with no requirement for attribution"</i>.</li>
	     </ul>
	</section>
	
	<section id="application">
	     <h1><g:message code="default.systeminfo.status"/></h1>
	     <ul class="list-group offset-top">
			<li class="list-group-item">App version: <g:meta name="app.version"/></li>
			<li class="list-group-item">Grails version: <g:meta name="app.grails.version"/></li>
			<li class="list-group-item">Groovy version: ${groovy.lang.GroovySystem.getVersion()}</li>
			<li class="list-group-item">JVM version: ${System.getProperty('java.version')}</li>
			<li class="list-group-item">Reloading active: ${grails.util.Environment.reloadingAgentEnabled}</li>
			<li class="list-group-item">Controllers: ${grailsApplication.controllerClasses.size()}</li>
			<li class="list-group-item">Domains: ${grailsApplication.domainClasses.size()}</li>
			<li class="list-group-item">Services: ${grailsApplication.serviceClasses.size()}</li>
			<li class="list-group-item">Tag Libraries: ${grailsApplication.tagLibClasses.size()}</li>
	     </ul>
	</section>
	
	<section id="plugins">
	     <h1><g:message code="default.systeminfo.plugins"/></h1>
	     <ul class="list-group offset-top">
	         <g:set var="pluginManager"
	                value="${applicationContext.getBean('pluginManager')}"></g:set>
	
	         <g:each var="plugin" in="${pluginManager.allPlugins.sort { it.name }}">
	             <li class="list-group-item">${plugin.name} - ${plugin.version}</li>
	         </g:each>
	
	     </ul>
	</section>
	<span class="for-footer"></span>
</div>
</body>

</html>
