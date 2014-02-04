package amelinium1.grails



import org.junit.*

import grails.plugin.springsecurity.SpringSecurityService;
import grails.test.mixin.*

@TestFor(BacklogController)
@Mock([Backlog,Csv,Project,Revision])
class BacklogControllerTests {

    def populateValidParams(params) {
        assert params != null
       		params["name"] = "My app"
			params["status"] = "Open"
			params["sprintLength"] = "14"
			params["velocity"] = "10"
			params["scopeIncrease"] = "1"
			params["max"] = "10"
			params["order"] = "desc"
			params["sort"] = "name"
			params["id"] = 1
			params["version"] = 1
    }
	
	class Dn {
		public getDn(){
			return "dn=Daniel Hajduk,cn=somecn";
		}
	}
	
	String [] html = ["html1","html2testestestest"]
	ProjectService projectService = [updateBacklog : {Long a, String b, String c, String d, String e -> new Project()}] as ProjectService
	SpringSecurityService springSecurityService = [getPrincipal : {-> new Dn() }] as SpringSecurityService;
	CoreService coreService = [recalculateCsv: {String a,String b, boolean c, boolean d, boolean e, boolean f -> "recalculated"},
									createCsvChartAndTable: {String a, boolean b,double c, double d, String e -> html},
									serializeText: {String a -> "html"}] as CoreService;

    void testShowProjectNotFound() {
		//when
		controller.show()
		
		//then
		assert flash.message != null
		assert response.redirectedUrl == "/project/list"
    }
	
	void testShow() {
		//given
		populateValidParams(params)
		ProjectService service = new ProjectService()
		Project project = service.createProject("aaa", "aaa", 1, 1, 1,true,true,true)
		params.id = project.id
		controller.coreService = coreService
		
		//when
		def model = controller.show()
		
		//then
		assert model.backlogInstance == project.revision.backlog
		assert model.projectInstance == project
		assert model.text == "html"
	}

    void testEditProjectNotFound() {
		//when
		controller.edit()
		
		//then
		assert flash.message != null
		assert response.redirectedUrl == "/project/list"
    }
	
	void testEdit() {
		//given
		populateValidParams(params)
		ProjectService service = new ProjectService()
		Project project = service.createProject("aaa", "aaa", 1, 1, 1,true,true,true)
		params.id = project.id
		controller.coreService = coreService
		
		//when
		def model = controller.edit()
		
		//then
		assert model.backlogInstance == project.revision.backlog
		assert model.projectInstance == project
	}
   

    void testUpdateProjectNotFound() {
		//when
		controller.update()
		
		//then
		assert flash.message != null
		assert response.redirectedUrl == "/project/list"
    }

	void testUpdateVersionError() {
		//given
		populateValidParams(params)
		ProjectService service = new ProjectService()
		Project project = service.createProject("aaa", "aaa", 1, 1, 1,true,true,true)
		params.id = project.id
		params["version"] = 0

		//when
		controller.update()
		
		//then
		assert view == "/backlog/edit"
		assert model.backlogInstance == project.revision.backlog
		assert model.projectInstance == project
		assert model.backlogInstance.errors != null
	}
	
	void testUpdate() {
		//given
		populateValidParams(params)
		ProjectService service = new ProjectService()
		Project project = service.createProject("aaa", "aaa", 1, 1, 1,true,true,true)
		params.id = project.id
		controller.projectService = projectService
		controller.springSecurityService = springSecurityService

		//when
		controller.update()
		
		//then
		assert response.redirectUrl == "/backlog/show/1"
		assert flash.message != null
	}
	
	void testRestoreBacklogNotFound(){
		//when
		controller.restore()
		
		//then
		assert flash.message != null
		assert response.redirectedUrl == "/project/list"
	}
	
	void testRestoreProjectNotFound(){
		//given
		populateValidParams(params)
		Backlog backlog = new Backlog()
		backlog.text = "test"
		assert backlog.save() != null
		Project.metaClass.static.getProjectInstance = {query, map -> null}
		
		//when
		controller.restore()
		
		//then
		assert flash.message != null
		assert response.redirectedUrl == "/project/list"
	}
	
	void testRestore(){
		//given
		populateValidParams(params)
		ProjectService service = new ProjectService()
		Project project = service.createProject("aaa", "aaa", 1, 1, 1,true,true,true)
		controller.projectService = projectService
		controller.springSecurityService = springSecurityService
		Project.metaClass.static.getProjectInstance = {query, map -> project}
		
		//when
		controller.restore()
		
		//then
		assert response.redirectedUrl == "/backlog/show/1"
	}
	
	void testShowRevisionBacklogNotFound(){
		//when
		controller.showRevision()
		
		//then
		assert flash.message != null
		assert response.redirectedUrl == "/project/list"
	}
	
	void testShowRevisionProjectNotFound(){
		//given
		populateValidParams(params)
		Backlog backlog = new Backlog()
		backlog.text = "test"
		assert backlog.save() != null
		Project.metaClass.static.getProjectInstance = {query, map -> null}
		
		//when
		controller.showRevision()
		
		//then
		assert flash.message != null
		assert response.redirectedUrl == "/project/list"
	}
	
	void testShowRevision(){
		//given
		populateValidParams(params)
		ProjectService service = new ProjectService()
		Project project = service.createProject("aaa", "aaa", 1, 1, 1,true,true,true)
		controller.coreService = coreService
		Project.metaClass.static.getProjectInstance = {query, map -> project}
		
		//when
		controller.showRevision()
		
		//then
		assert view == "/backlog/show"
		assert model.backlogInstance == project.revision.backlog
		assert model.projectInstance == project
		assert model.text == "html"
	}
	
	void testListRevisionProjectNotFound(){
		//when
		controller.listRevision()
		
		//then
		assert flash.message != null
		assert response.redirectedUrl == "/project/list"
	}
	
	void testListRevision(){
		//given
		populateValidParams(params)
		ProjectService service = new ProjectService()
		Project project = service.createProject("aaa", "aaa", 1, 1, 1,true,true,true)
		
		//when
		controller.listRevision()
		
		//then
		assert response.redirectedUrl == "/revision/list/1?name=My+app&status=Open&sprintLength=14&velocity=10&scopeIncrease=1&max=10&order=desc&sort=name&version=1&display=backlog"
	}
	
	void testRecalculateProjectNotFound(){
		//when
		controller.recalculate()
		
		//then
		assert flash.message != null
		assert response.redirectedUrl == "/project/list"
	}
	
	void testRecalculate(){
		//given
		populateValidParams(params)
		ProjectService service = new ProjectService()
		Project project = service.createProject("aaa", "aaa", 1, 1, 1,true,true,true)
		controller.coreService = coreService
		controller.projectService = projectService
		controller.springSecurityService = springSecurityService
		
		//when
		controller.recalculate()
		
		//then
		assert flash.message != null
		assert response.redirectedUrl == "/backlog/show/1"
	}
}
