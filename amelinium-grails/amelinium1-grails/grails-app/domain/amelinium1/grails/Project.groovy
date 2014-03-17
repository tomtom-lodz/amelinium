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
	boolean isCumulative
	boolean multilineFeature
	boolean addNewFeatureGroups
	
	static hasMany = [revisions: Revision]
	static mappedBy = [revisions: 'project']
	
    static constraints = {
		name size: 1..200, blank: false, unique: true
		revision nullable: true
		status nullable: true, inList: ["Open","Closed"]
        sprintLength nullable:true, blank: false, min:1
        velocity nullable:true, blank: false, min:1
        scopeIncrease nullable:true, blank: false, min:0
        createdBy nullable:true, size:1..200
        editedBy nullable:true, size:1..200
		isCumulative nullable: true, inList: [true,false]
		multilineFeature nullable: true, inList: [true,false]
		addNewFeatureGroups nullable: true, inList: [true,false]
    }
	
	static mapping = {
		status defaultValue:"Open"
		isCumulative sqlType:"BOOL"
		multilineFeature sqlType:"BOOL"
		addNewFeatureGroups sqlType:"BOOL"
	}
	
	// taken care of first element when null project
	static Project getProjectInstance(query, map){
		return Project.executeQuery(query, map).first()
	}
}
