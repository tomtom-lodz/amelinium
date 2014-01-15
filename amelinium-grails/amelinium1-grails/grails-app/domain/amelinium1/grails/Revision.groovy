package amelinium1.grails

import java.util.Date;

class Revision {

	int ver
	Backlog backlog
	Csv csv
    Date dateCreated
    Date lastUpdated
    String changedBy
    String comment
    
	static belongsTo = [project: Project]
	
    static constraints = {
    }
}
