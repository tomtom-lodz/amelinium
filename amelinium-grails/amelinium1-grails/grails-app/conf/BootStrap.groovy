import amelinium1.grails.ProjectService;
import amelinium1.grails.Project;
import grails.util.Environment;

class BootStrap {

    def init = { servletContext ->
        def currentEnv = Environment.current

        if (currentEnv == Environment.DEVELOPMENT) {
			def projectService = new ProjectService()
			projectService.createProject("project1")
			projectService.createProject("project2")
			projectService.createProject("project3")
			projectService.createProject("project4")
			projectService.createProject("project5")
			projectService.createProject("project6")
			projectService.createProject("project7")
			projectService.createProject("project8")
			projectService.createProject("project9")
			projectService.createProject("project10")
			projectService.createProject("project11")
        } else if (currentEnv == Environment.TEST) {

        } else if (currentEnv == Environment.PRODUCTION) {
        }
    }
    def destroy = {
    }
}
