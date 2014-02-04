package amelinium1.grails



import org.junit.*

import grails.plugin.springsecurity.SpringSecurityService
import grails.test.GrailsMock
import grails.test.mixin.*

@TestFor(CsvController)
@Mock([Project, Csv, Backlog, Revision])
class CsvControllerTests {
	
	class Dn {
		public getDn(){
			return "dn=Daniel Hajduk,cn=somecn";
		}
	}
	// chart , table
	String [] html = ["html1","html2testestestest"]
	ProjectService projectService = [updateCsv : {Long a, String b, String c, String d -> new Project()}] as ProjectService
	SpringSecurityService springSecurityService = [getPrincipal : {-> new Dn() }] as SpringSecurityService;
	CoreService coreService = [recalculateCsv: {String a,String b, boolean c, boolean d, boolean e, boolean f -> "recalculated"},
									createCsvChartAndTable: {String a, boolean b,double c, double d, String e -> html},
									serializeText: {String a -> "html"}] as CoreService;
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

    void testShowProjectNotFound() {
		//when
        controller.show()

		//then
        assert flash.message != null
        assert response.redirectedUrl == '/project/list'
    }
	
	void testShow(){
		//given
		populateValidParams(params)
		ProjectService service = new ProjectService()
		Project project = service.createProject("aaa", "aaa", 1, 1, 1,true,true,true)
		params.id = project.id
		controller.coreService = coreService
		
		//when
        def model = controller.show()

		//then
        assert model.csvInstance != null
		assert model.projectInstance == project
		assert model.text == "html"
    }

    void testEditProjectNotFound() {
		//when
        controller.edit()

		//then
        assert flash.message != null
        assert response.redirectedUrl == '/project/list'
    }
	
	void testEdit(){
		//given
        populateValidParams(params)
		ProjectService service = new ProjectService()
		Project project = service.createProject("aaa", "aaa", 1, 1, 1,true,true,true)
		params.id = project.id

		//when
        def model = controller.edit()

		//then
        assert model.csvInstance == project.revision.csv
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
		ProjectService service = new ProjectService()
		Project project = service.createProject("aaa", "aaa", 1, 1, 1,true,true,true)
		params.id = project.id
		params.version = 0
		
		//when
		controller.update()
		
		//then
		assert view == "/csv/edit"
		assert model.csvInstance == project.revision.csv
		assert model.projectInstance == project
	}
	
	void testUpdate(){
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
		assert response.redirectedUrl == "/csv/show/1"
		assert flash.message != null
	}
	
	void testUpdateProjectNotFoundProject(){
		//when
		controller.updateProject()

		//then
		assert flash.message != null
		assert response.redirectedUrl == '/project/list'
	}
	
	void testUpdateProject(){
		//given
		populateValidParams(params)
		ProjectService service = new ProjectService()
		Project project = service.createProject("aaa", "aaa", 1, 1, 1,true,true,true)
		params.id = project.id
		
		//when
		def model = controller.updateProject()
		
		//then
		assert params.id == project.id
		assert response.redirectedUrl == '/csv/plotFromProject/1'
	}
	
	void testRestoreCsvNotFound(){
		//when
		controller.restore()
		
		//then
		assert flash.message != null
		assert response.redirectedUrl == '/project/list'
	}

	void testRestore(){
		//given
		populateValidParams(params)
		Csv csv = new Csv()
		csv.ver = 1
		csv.text = "test"
		csv.editedBy = "test"
		assert csv.save() != null
		Project project = new Project(params);
		assert project.save() != null
		Project.metaClass.static.getProjectInstance = {query, map -> project}
		controller.projectService = projectService
		controller.springSecurityService = springSecurityService
		
		//when
		controller.restore()
		
		//then
		assert response.redirectedUrl == '/csv/show/1'
	}
	
	void testRestoreProjectNotFound(){
		//given
		populateValidParams(params)
		Csv csv = new Csv()
		csv.ver = 1
		csv.text = "test"
		csv.editedBy = "test"
		assert csv.save() != null
		Project.metaClass.static.getProjectInstance = {query, map -> null}
		controller.projectService = projectService
		controller.springSecurityService = springSecurityService
		
		//when
		controller.restore()
		
		//then
		assert response.redirectedUrl == "/project/list"
		assert flash.message != null
	}
	
	void testShowRevisionCsvNotFound(){
		//when
		controller.showRevision()
		
		//then
		assert response.redirectedUrl == "/project/list"
		assert flash.message != null
	}
	
	void testShowRevisionProjectNotFound(){
		//given
		populateValidParams(params)
		Csv csv = new Csv()
		csv.ver = 1
		csv.text = "test"
		csv.editedBy = "test"
		assert csv.save() != null
		Project.metaClass.static.getProjectInstance = {query, map -> null}
		
		//when
		controller.showRevision()
		
		//then
		assert response.redirectedUrl == "/project/list"
		assert flash.message != null
	}
	
	void testShowRevision(){
		//given
		populateValidParams(params)
		Csv csv = new Csv()
		csv.ver = 1
		csv.text = "test"
		csv.editedBy = "test"
		assert csv.save() != null
		Project project = new Project(params);
		assert project.save() != null
		Project.metaClass.static.getProjectInstance = {query, map -> project}
		controller.coreService = coreService
		
		//when
		controller.showRevision()
		
		//then
		assert view == "/csv/show"
		assert model.csvInstance == csv
		assert model.projectInstance == project
		assert model.text == coreService.serializeText("");
		
	}
	
	void testListRevisionProjectNotFound(){
		//when 
		controller.listRevision()
		
		//then
		assert response.redirectedUrl == "/project/list"
		assert flash.message != null
	}
	
	void testListRevision(){
		//given
		populateValidParams(params)
		Project project = new Project(params);
		assert project.save() != null
		
		//when
		def model = controller.listRevision()
		
		//then
		assert response.redirectedUrl == "/revision/list/1?name=My+app&status=Open&sprintLength=14&velocity=10&scopeIncrease=1&max=10&order=desc&sort=name&version=1&display=csv"
	}
	
	void testPlotFromProjectNotFound(){
		//when
		controller.plotFromProject()
		
		//then
		assert response.redirectedUrl == "/project/list"
		assert flash.message != null
	}
	
	void testPlotFromProjectCsvTextSize(){
		//given
		populateValidParams(params)
		ProjectService service = new ProjectService();
		Project project = service.createProject("aaa", "aaa", 1, 1, 1,true,true,true)
		
		//when
		controller.plotFromProject()
		
		//then
		assert response.redirectedUrl == "/project/list"
		assert flash.message != null
	}
	
	void testPlotFromProject(){
		//given
		populateValidParams(params)
		params["cumulative"] = true
		ProjectService service = new ProjectService();
		Project project = service.createProject("aaa", "aaa", 1, 1, 1,true,true,true)
		project.revision.csv.text = "test"
		controller.coreService = coreService
		
		//when
		controller.plotFromProject()
		
		//then
		assert view == "/csv/plot"
		assert model.csvInstance == project.revision.csv
		assert model.projectInstance == project
		assert model.chart == "html1"
		assert model.burnuptable == "<table class=\"table\""+"t"		
	}
	
	void testPlotFromCsvNotFound(){
		//when
		controller.plotFromCsv()
		
		//then
		assert flash.message != null
		assert response.redirectedUrl == "/project/list"
	}
	
	void testPlotFromCsvProjectNotFound(){
		//given
		populateValidParams(params)
		Csv csv = new Csv()
		csv.ver = 1
		csv.text = "test"
		csv.editedBy = "test"
		assert csv.save() != null
		Project.metaClass.static.getProjectInstance = {query, map -> null}
		
		//when
		controller.plotFromCsv()
		
		//then
		assert flash.message != null
		assert response.redirectedUrl == "/project/list"
	}
	
	void testPlotFromCsvTextNotValid(){
		//given
		populateValidParams(params)
		ProjectService service = new ProjectService();
		Project project = service.createProject("aaa", "aaa", 1, 1, 1,true,true,true)
		project.revision.csv.text = ""
		Project.metaClass.static.getProjectInstance = {query, map -> project}
		
		//when
		controller.plotFromCsv()
		
		//then
		assert flash.message != null
		assert response.redirectedUrl == "/project/list" 
	}
	
	void testPlotFromCsv(){
		//given
		populateValidParams(params)
		params["cumulative"] = true
		ProjectService service = new ProjectService();
		Project project = service.createProject("aaa", "aaa", 1, 1, 1,true,true,true)
		project.revision.csv.text = "test"
		controller.coreService = coreService
		
		//when
		controller.plotFromProject()
		
		//then
		assert view == "/csv/plot"
		assert model.csvInstance == project.revision.csv
		assert model.projectInstance == project
		assert model.chart == "html1"
		assert model.burnuptable == "<table class=\"table\""+"t"		
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
		ProjectService service = new ProjectService();
		Project project = service.createProject("aaa", "aaa", 1, 1, 1,true,true,true)
		controller.projectService = projectService
		controller.coreService = coreService
		controller.springSecurityService = springSecurityService
		
		//when
		controller.recalculate()
	
		//then
		assert flash.message != null
		assert response.redirectedUrl == "/csv/show/1"
		
	}
	
	void testPlotApiCsvNotFound(){
		params.id = 1
		//when
		controller.plotApiCsv()
		
		//then
		assert flash.message != null
		assert view == "/csv/plot_template"
		
	}
	
	void testPlotApiCsvProjectNotFound(){
		//given
		populateValidParams(params)
		Csv csv = new Csv()
		csv.ver = 1
		csv.text = "test"
		csv.editedBy = "test"
		assert csv.save() != null
		Project.metaClass.static.getProjectInstance = {query, map -> null}
		
		
		//when
		controller.plotApiCsv()
		
		//then
		assert flash.message != null
		assert view == "/csv/plot_template"
	
	}
	void testPlotApiCsvTextNotValid(){
		//given
		populateValidParams(params)
		Csv csv = new Csv()
		csv.ver = 1
		csv.text = ""
		csv.editedBy = "test"
		assert csv.save() != null
		Project.metaClass.static.getProjectInstance = {query, map -> new Project(params)}
		
		
		//when
		controller.plotApiCsv()
		
		//then
		assert flash.message != null
		assert view == "/csv/plot_template"
	
	}
	void testPlotApiCsv(){
		//given
		populateValidParams(params)
		ProjectService service = new ProjectService()
		Project project = service.createProject("aaa", "aaa", 1, 1, 1,true,true,true)
		project.revision.csv.text = "test"
		params["cumulative"] = true
		controller.coreService = coreService
		Project.metaClass.static.getProjectInstance = {query, map -> new Project(params)}
		
		//when
		controller.plotApiCsv()
		
		//then
		assert flash.message == null
		assert view == "/csv/plot_template"
		assert model.chart == "html1"
	}

	void testPlotApiProjectNotFound(){
		//when
		controller.plotApiProject()
		
		//then
		assert flash.message != null
		assert view == "/csv/plot_template"
	}
	
	void testPlotApiProjectCsvTextNotValid(){
		//given
		populateValidParams(params)
		ProjectService service = new ProjectService()
		Project project = service.createProject("aaa", "aaa", 1, 1, 1,true,true,true)
		project.revision.csv.text = ""
		
		//when
		controller.plotApiProject()
		
		//then
		assert flash.message != null
		assert view == "/csv/plot_template"
	}
	
	void testPlotApiProject(){
		//given
		populateValidParams(params)
		ProjectService service = new ProjectService()
		Project project = service.createProject("aaa", "aaa", 1, 1, 1,true,true,true)
		project.revision.csv.text = "test"
		params["cumulative"] = true
		controller.coreService = coreService
		Project.metaClass.static.getProjectInstance = {query, map -> new Project(params)}
		
		//when
		controller.plotApiCsv()
		
		//then
		assert flash.message == null
		assert view == "/csv/plot_template"
		assert model.chart == "html1"
	}
}
