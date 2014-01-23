import amelinium1.grails.ProjectService;
import amelinium1.grails.Project;
import grails.util.Environment;

class BootStrap {

    def init = { servletContext ->
        def currentEnv = Environment.current

        if (currentEnv == Environment.DEVELOPMENT) {
			def projectService = new ProjectService()
			projectService.createProject("My grails project nr 0","admin",1,1,1)
			projectService.createProject("My grails project nr 1","admin",1,1,1)
			projectService.createProject("My grails project nr 2","admin",1,1,1)
			projectService.createProject("My grails project nr 3","admin",1,1,1)
			projectService.createProject("My grails project nr 4","admin",1,1,1)
			projectService.createProject("My grails project nr 5","admin",1,1,1)
			projectService.createProject("My grails project nr 6","admin",1,1,1)
			projectService.createProject("My grails project nr 7","admin",1,1,1)
			projectService.createProject("My grails project nr 8","admin",1,1,1)
			projectService.createProject("My grails project nr 9","admin",1,1,1)
			projectService.createProject("Not my grails project nr 1","admin",1,1,1)
            
        } else if (currentEnv == Environment.TEST) {

        } else if (currentEnv == Environment.PRODUCTION) {
        }
    }
    def destroy = {
    }
}
