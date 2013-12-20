package amelinium1.grails

/**
 * Comment
 * A domain class describes the data object and it's mapping to the database
 */
class Comment {

    String name;
    String text;
	Date lastUpdated
    
	static	constraints = {
		text widget: 'textarea'
    }
	
}
