package amelinium1.grails

class Revision {

	int ver
	Backlog backlog
	Csv csv
	
	static belongsTo = [project: Project]
	
    static constraints = {
    }
}
