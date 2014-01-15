import amelinium1.grails.ProjectService;
import amelinium1.grails.Project;
import amelinium1.grails.SecRole
import amelinium1.grails.SecUser
import amelinium1.grails.SecUserSecRole
import grails.util.Environment;

class BootStrap {

    def init = { servletContext ->
        def currentEnv = Environment.current

        if (currentEnv == Environment.DEVELOPMENT) {
			def projectService = new ProjectService()
			projectService.createProject("project1","jasio",1,1,1)
			projectService.createProject("project2","jasio",1,1,1)
			projectService.createProject("project3","jasio",1,1,1)
			projectService.createProject("project4","jasio",1,1,1)
			projectService.createProject("project5","jasio",1,1,1)
			projectService.createProject("project6","jasio",1,1,1)
			projectService.createProject("project7","jasio",1,1,1)
			projectService.createProject("project8","jasio",1,1,1)
			projectService.createProject("project9","jasio",1,1,1)
			projectService.createProject("project10","jasio",1,1,1)
			projectService.createProject("project11","jasio",1,1,1)
            def userRole = SecRole.findByAuthority('ROLE_USER') ?: new SecRole(authority: 'ROLE_USER').save(failOnError: true, flush:true)
            def adminRole = SecRole.findByAuthority('ROLE_ADMIN') ?: new SecRole(authority: 'ROLE_ADMIN').save(failOnError: true, flush:true)
        } else if (currentEnv == Environment.TEST) {

        } else if (currentEnv == Environment.PRODUCTION) {
        }
    }
    def destroy = {
    }
}
