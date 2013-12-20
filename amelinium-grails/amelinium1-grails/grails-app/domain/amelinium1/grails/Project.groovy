package amelinium1.grails

class Project {

	String name
	Revision revision
	String status
	
	static hasMany = [revisions: Revision]
	static mappedBy = [revisions: 'project']
	
    static constraints = {
		name size: 2..100, blank: false, unique: true
		revision nullable: true
		status nullable: true
    }
}
