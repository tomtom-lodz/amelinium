package amelinium1.grails

import static org.junit.Assert.*

import org.junit.*

class ProjectServiceTests {
	
	ProjectService projectService = new ProjectService()

    @Before
    void setUp() {
        // Setup logic here
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testCreateProject() {
		
//		log.info "aaa"
//
//		assert Project.count() == 0
//		def project=new Project(name:"aaa")
//		project.save(flush:true)
//		assertFalse project.hasErrors()
//		assert Project.count() == 1
//		
//		log.info "bbb"
		
		def project = projectService.createProject("name")
		assert Project.count() == 1
		assert project.revisions.last().ver == 1
    }
	
    @Test
    void testUpdateBacklog() {
		def project = projectService.createProject("name")
		projectService.updateBacklog(project, "new backlog 1")
		projectService.updateBacklog(project, "new backlog 2")
		assert Project.count() == 1
		assert project.revision.backlog.ver == 3 
		assert project.revision.csv.ver == 1
		assert project.revision.ver == 3
	}
	
    @Test
    void testUpdateCsv() {
		def project = projectService.createProject("name")
		projectService.updateCsv(project, "new csv 1")
		projectService.updateCsv(project, "new csv 2")
		assert Project.count() == 1
		assert project.revision.backlog.ver == 1
		assert project.revision.csv.ver == 3
		assert project.revision.ver == 3
	}
	
    @Test
    void testUpdateBacklogCsv() {
		def project = projectService.createProject("name")
		projectService.updateBacklogCsv(project, "new backlog 1", "new csv 1")
		projectService.updateBacklogCsv(project, "new backlog 2", "new csv 2")
		assert Project.count() == 1
		assert project.revision.backlog.ver == 3
		assert project.revision.csv.ver == 3
		assert project.revision.ver == 3
	}
}
