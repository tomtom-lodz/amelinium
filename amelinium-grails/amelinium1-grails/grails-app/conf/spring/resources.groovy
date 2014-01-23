// Place your Spring DSL code here
beans = {
		ldapAuthProvider(org.springframework.security.ldap.authentication.LdapAuthenticationProvider,
		ref("ldapAuthenticator"),
		ref("authoritiesPopulator")
		)
	
		authoritiesPopulator(amelinium1.grails.CustomAuthoritiesPopulator)
}
