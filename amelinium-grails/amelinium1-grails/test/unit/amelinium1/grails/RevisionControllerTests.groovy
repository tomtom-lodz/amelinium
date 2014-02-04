package amelinium1.grails

import org.junit.*
import grails.test.mixin.*


@TestFor(RevisionController)
@Mock([Revision,Backlog,Csv,Project])
class RevisionControllerTests {

    def populateValidParams(params) {
		assert params != null
		params["name"] = "My app"
		params["status"] = "Open"
		params["sprintLength"] = "14"
		params["velocity"] = "10"
		params["scopeIncrease"] = "1"
		params["max"] = "10"
		params["order"] = "desc"
		params["sort"] = "id"
		params["id"] = 1
		params["version"] = 1
		params["display"] = "project"
    }

    void testIndex() {
		//when
        controller.index()
		
		//then
        assert "/revision/list" == response.redirectedUrl
    }

    void testListProjectNotFound() {
		//given
		populateValidParams(params)
		
		//when
        controller.list()

		//then
		assert response.redirectedUrl == '/project/list'		
    }
	
	void testList(){
		//given
		populateValidParams(params)
		ProjectService service = new ProjectService()
		def project = service.createProject("test", "test", 0, 0, 0,true,true,true);
		params.id = project.id
		
		//when
		def model = controller.list()
		
		//then
		assert model.revisionInstanceList.size() == 0
		assert model.revisionInstanceTotal == 0
		assert model.projectInstance == project
		assert model.display == "project"
	}
}
