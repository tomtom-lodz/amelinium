
beans = {
		ldapAuthProvider(org.springframework.security.ldap.authentication.LdapAuthenticationProvider,
			ref("ldapAuthenticator"),
			ref("authoritiesPopulator")
		)
		
		authoritiesPopulator(amelinium1.grails.CustomAuthoritiesPopulator)
		
		
    if (grailsApplication.config.grails.plugin.springsecurity.rememberMe.persistent) {
		
		springSecurityAuthenticationSource(org.springframework.security.ldap.authentication.SpringSecurityAuthenticationSource)
		
		myContextSource(org.springframework.ldap.core.support.LdapContextSource){
			url = 'ldap://10.1.192.103:389'
			base = 'O=Teleatlas,C=Global'
			authenticationSource = ref('springSecurityAuthenticationSource')
		}
		
		myLdapTemplate(org.springframework.ldap.core.LdapTemplate, ref('myContextSource'))
		
		customUserDetailsService(amelinium1.grails.CustomUserDetailsService,
			ref("myLdapTemplate")
		)
			
		rememberMeServices(amelinium1.grails.MyPersistentTokenBasedRememberMeServices) {
			userDetailsService = ref('customUserDetailsService')
			key = grailsApplication.config.grails.plugin.springsecurity.rememberMe.key
			cookieName = grailsApplication.config.grails.plugin.springsecurity.rememberMe.cookieName
			alwaysRemember = grailsApplication.config.grails.plugin.springsecurity.rememberMe.alwaysRemember
			tokenValiditySeconds = grailsApplication.config.grails.plugin.springsecurity.rememberMe.tokenValiditySeconds
			parameter = grailsApplication.config.grails.plugin.springsecurity.rememberMe.parameter
			useSecureCookie = grailsApplication.config.grails.plugin.springsecurity.rememberMe.useSecureCookie // false

			tokenRepository = ref('tokenRepository')
			seriesLength = grailsApplication.config.grails.plugin.springsecurity.rememberMe.persistentToken.seriesLength // 16
			tokenLength = grailsApplication.config.grails.plugin.springsecurity.rememberMe.persistentToken.tokenLength // 16
		}
    }
	
}
