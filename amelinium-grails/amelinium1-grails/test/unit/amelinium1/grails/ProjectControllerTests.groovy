package amelinium1.grails

import org.junit.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.validation.Errors
import grails.plugin.springsecurity.SpringSecurityService;

import grails.test.mixin.*

@TestFor(ProjectController)
@Mock([Project,Backlog,Revision,Csv])
class ProjectControllerTests
{	
	class Dn {
		public getDn(){
			return "dn=Daniel Hajduk,cn=somecn";
		}
	}
	
	SpringSecurityService springSecurityService = [getPrincipal : {-> new Dn() }] as SpringSecurityService
	
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

    void testIndex() {
		//when
        controller.index()
        
		//then
		assert "/project/list" == response.redirectedUrl
    }

    void testList() {
		//when
        def model = controller.list()

		//then
        assert model.projectInstanceList.size() == 0
        assert model.projectInstanceTotal == 0
		assert model.projectsMax == 10
		assert model.sorted == "name"
		assert model.ordered == "asc"
    }

	void testShowBacklog(){
		//when
		def model = controller.showBacklog(1);
		
		//then
		assert "/backlog/show/1" == response.redirectedUrl
	}
	
	void testShowCsv(){
		//when
		def model = controller.showCsv(1);
		
		//then
		assert "/csv/show/1" == response.redirectedUrl
	}
	
    void testCreate() {
		//when
        def model = controller.create()
		
		//then
        assert model.projectInstance != null
    }

    void testSave() {
		//given
		controller.springSecurityService = springSecurityService
		controller.projectService = [createProject : {String a, String b, Integer c, Integer d, Integer e -> new Project()}] as ProjectService
		populateValidParams(params)
		
		//when
        controller.save()
		
		//then
        assert response.redirectedUrl == '/project/list'
		assert controller.flash.message != null
    }
	
	void testSaveError(){
		//given
		populateValidParams(params)
		Project project = new Project();
		project.errors.rejectValue("name", null)
		controller.springSecurityService = springSecurityService
		controller.projectService = [createProject : {String a, String b, Integer c, Integer d, Integer e -> project}] as ProjectService
		
		//when
		controller.save()
		
		//then
		assert view == '/project/create'
		assert model.projectInstance != null
	}
	
	void testShow(){
		//given
		populateValidParams(params)
		def project = new Project(params)
		assert project.save() != null
		params.id = project.id

		//when
		def model = controller.show()

		//then
		assert model.projectInstance == project
	}

    void testShowNotFound() {
		//when
        controller.show()

		//then
        assert flash.message != null
        assert response.redirectedUrl == '/project/list'
    }

    void testEdit() {
		//when
        controller.edit()

		//then
        assert flash.message != null
        assert response.redirectedUrl == '/project/list'
    }
	
	void testEditError(){		
		//given
		populateValidParams(params)
		def project = new Project(params)
		assert project.save() != null
		params.id = project.id

		//when
		def model = controller.edit()

		//then
		assert model.projectInstance == project
	}

    void testUpdateProjectNotFound() {
		//when
        controller.update()

		//then
        assert flash.message != null
        assert response.redirectedUrl == '/project/list'
    }
	
	void testUpdateVersionError(){
		//given
		populateValidParams(params)
		controller.springSecurityService = springSecurityService
		controller.projectService = new ProjectService();
		def project = controller.projectService.createProject("red", "red", 0, 0, 0);
		params.id = project.id
		params.version = 0
		
		//when
		controller.update(params.id, params.version)
		
		//then
		assert view == "/project/edit"
		assert model.projectInstance != null
	}
	
	void testUpdate(){
		//given
        populateValidParams(params)
		controller.springSecurityService = springSecurityService
		controller.projectService = new ProjectService();
		def project = controller.projectService.createProject("red", "red", 0, 0, 0);
		params.id = project.id
        
		//when
		controller.update()

		//then
        assert response.redirectedUrl == "/project/list"
        assert flash.message != null
    }

    void testDeleteProjectNotFound() {
		//when
        controller.delete()
        
		//then
		assert flash.message != null
        assert response.redirectedUrl == '/project/list'
    }
	
	void testDelete(){
		//given
        populateValidParams(params)
        def project = new Project(params)
        assert project.save() != null
        assert Project.count() == 1
        params.id = project.id

		//when
        controller.delete()

		//then
        assert Project.count() == 0
        assert Project.get(project.id) == null
        assert response.redirectedUrl == '/project/list'
    }
	
	void testListRevisionProjectNotFound(){
		//when
		controller.listRevision()
		
		//then
		assert flash.message != null
        assert response.redirectedUrl == '/project/list'
	}
	
	void testListRevision(){
		//given 
		populateValidParams(params)
		def project = new Project(params)
		assert project.save() != null
		params.id = project.id
		
		//when
		controller.listRevision()
		
		//then
		assert response.redirectedUrl == '/revision/list/1?name=My+app&status=Open&sprintLength=14&velocity=10&scopeIncrease=1&max=10&order=desc&sort=name&version=1&display=project'
		assert params.display == 'project'
	}
}
