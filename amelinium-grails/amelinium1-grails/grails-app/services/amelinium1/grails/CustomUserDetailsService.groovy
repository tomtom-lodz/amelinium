package amelinium1.grails

import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService{

	private LdapTemplate ldapTemplate;

	public CustomUserDetailsService(LdapTemplate template) {
		ldapTemplate = template;
	}

	@Override
	public UserDetails loadUserByUsername(String username)
	throws UsernameNotFoundException {

		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("uid", username));
		List<String> userList = ldapTemplate.search("", filter.encode(), new SimpleAttributeMapper());
		UserDetails user = new CustomUserDetails(username, "", userList.get(0).toString());

		return user;
	}
}