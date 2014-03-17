import amelinium1.grails.ProjectService;
import amelinium1.grails.Project;
import grails.util.Environment;

class BootStrap {

    def init = { servletContext ->
        def currentEnv = Environment.current

        if (currentEnv == Environment.DEVELOPMENT) {
			def projectService = new ProjectService()
			projectService.createProject("My grails project nr 0","admin",1,1,1,false,true,true)
            
        } else if (currentEnv == Environment.TEST) {

        } else if (currentEnv == Environment.PRODUCTION) {
        }
    }
    def destroy = {
    }
}
