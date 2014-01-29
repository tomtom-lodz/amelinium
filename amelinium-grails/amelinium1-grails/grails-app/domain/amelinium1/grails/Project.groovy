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
        sprintLength nullable:true, blank: false, matches: ["[0-9]+"]
        velocity nullable:true, blank: false, matches: ["[0-9]+"]
        scopeIncrease nullable:true, blank: false, matches: ["[0-9]+"]
        createdBy nullable:true, size:2..100
        editedBy nullable:true, size:2..100
    }
	// taken care of first element when null project
	static Project getProjectInstance(query, map){
		return Project.executeQuery(query, map).first()
	}
}
