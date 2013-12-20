package amelinium1.grails

class Csv {

	int ver
	String text
	Comment comment
	
    static constraints = {
        text maxSize: 8000
		comment nullable:true
    }
}
