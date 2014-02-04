package amelinium1.grails

import java.util.Date;

import groovy.swing.factory.WidgetFactory;

class Backlog {
	
	int ver
	String text
    Date dateCreated
    Date lastUpdated
    String editedBy
    String state
	
    static constraints = {
		text widget: 'textarea'
        editedBy nullable:true
        state nullable:true, inList:["Recalculated", "Not recalculated"]
    }
    
    static mapping = {
        text sqlType:"MEDIUMTEXT"
    }
    
}
