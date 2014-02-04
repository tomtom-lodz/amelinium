package amelinium1.grails

import static org.junit.Assert.*

import org.junit.*

class ProjectServiceTests {
	
	ProjectService projectService = new ProjectService()

    @Test
    void testCreateProject() {
		//when
		def project = projectService.createProject("aa", "aa", 0, 0, 0,true,true,true)
		
		//then
		assert Project.count() == 1
		assert project.revisions.last().ver == 1
    }
	
    @Test
    void testUpdateBacklogTwice() {
		//given
		def project = projectService.createProject("aa", "aa", 0, 0, 0,true,true,true)
		
		//when
		projectService.updateBacklog(project.id, "text", "text", "Recalculated", "test")
		projectService.updateBacklog(project.id, "text2", "text2", "Recalculated", "test")
		
		//update set on test
		//then
		assert Project.count() == 1
		assert project.revision.backlog.ver == 3 
		assert project.revision.csv.ver == 1
		assert project.revision.ver == 3
	}
	
    @Test
    void testUpdateCsvTwice() {
		//given
		def project = projectService.createProject("aa", "aa", 0, 0, 0,true,true,true)
		
		//when
		projectService.updateCsv(project.id, "text", "text", "test")
		projectService.updateCsv(project.id, "text2", "text2", "test")
		
		//then
		assert Project.count() == 1
		assert project.revision.backlog.ver == 1
		assert project.revision.csv.ver == 3
		assert project.revision.ver == 3
	}
	
    @Test
    void testUpdateBacklogCsvTwice() {
		//given
		def project = projectService.createProject("aa", "aa", 0, 0, 0,true,true,true)
		
		//when
		projectService.updateBacklogAndCsv(project.id, "text", "text", "text", "Recalculated", "test")
		projectService.updateBacklogAndCsv(project.id, "text2", "text2", "text2", "Recalculated", "test")
		
		//then
		assert Project.count() == 1
		assert project.revision.backlog.ver == 3
		assert project.revision.csv.ver == 3
		assert project.revision.ver == 3
	}
}
