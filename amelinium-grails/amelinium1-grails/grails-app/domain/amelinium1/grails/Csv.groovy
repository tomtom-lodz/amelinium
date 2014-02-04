package amelinium1.grails

import java.util.Date;

class Csv {

	int ver
	String text
    Date dateCreated
    Date lastUpdated
    String editedBy
    
    static constraints = {
        text widget: 'textarea'
        editedBy nullable:true
    }
    
    static mapping = {
        text sqlType:"MEDIUMTEXT"
    }
}
