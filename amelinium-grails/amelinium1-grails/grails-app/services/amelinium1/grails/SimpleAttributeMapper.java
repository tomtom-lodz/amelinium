package amelinium1.grails;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.springframework.ldap.core.AttributesMapper;

public class SimpleAttributeMapper implements AttributesMapper {

	@Override
	public Object mapFromAttributes(Attributes attributes) throws NamingException {
		return attributes.get("cn");	
	}

}
