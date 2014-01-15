package amelinium1.grails

class Project {

    Date dateCreated
    Date lastUpdated
	String name
	Revision revision
	String status
    Integer sprintLength
    Integer velocity
    Integer scopeIncrease
    String createdBy
    String editedBy
	
	static hasMany = [revisions: Revision]
	static mappedBy = [revisions: 'project']
	
    static constraints = {
		name size: 2..100, blank: false, unique: true
		revision nullable: true
		status nullable: true, inList: ["Open","Closed"]
        sprintLength nullable:true, matches: ["[0-9]+"]
        velocity nullable:true, matches: ["[0-9]+"]
        scopeIncrease nullable:true, matches: ["[0-9]+"]
        createdBy nullable:true
        editedBy nullable:true
    }
}
