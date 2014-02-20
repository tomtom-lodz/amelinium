package amelinium1.grails;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails{

	private String username = "";
	private String password = "";
	private String Dn = "";
	private ArrayList<GrantedAuthority> authrorities;
	public CustomUserDetails(String username, String password, String dn){
    	authrorities = new ArrayList<GrantedAuthority>();
    	authrorities.add((new SimpleGrantedAuthority("ROLE_USER")));
    	this.username = username;
    	this.password = password;
    	this.Dn = dn;
		
	}
	
	public String getDn() {
		return Dn;
	}

	public void setDn(String dn) {
		Dn = dn;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authrorities;
	}
	
	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
