package amelinium1.grails

import groovy.swing.factory.WidgetFactory;

class Backlog {
	
	int ver
	String text
	Comment comment
	
    static constraints = {
		text widget: 'textarea', maxSize: 5000
		comment nullable:true
    }
}
