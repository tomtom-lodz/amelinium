package amelinium1.grails

import java.util.Collection;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator

class CustomAuthoritiesPopulator implements LdapAuthoritiesPopulator {

	@Override
	public Collection<? extends GrantedAuthority> getGrantedAuthorities(
			DirContextOperations user, String username) {
		ArrayList<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		list.add((new SimpleGrantedAuthority("ROLE_USER")));
		return list;
	}

}
