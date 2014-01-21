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
            def userRole = SecRole.findByAuthority('ROLE_USER') ?: new SecRole(authority: 'ROLE_USER').save(failOnError: true, flush:true)
            def adminRole = SecRole.findByAuthority('ROLE_ADMIN') ?: new SecRole(authority: 'ROLE_ADMIN').save(failOnError: true, flush:true)
            def secUser = SecUser.findByUsername("admin") ?: new SecUser(username:"admin", password:"nimda", accountExpired:false, accountLocked:false, passwordExpired:false).save(failOnError: true, flush:true)
            SecUserSecRole.create secUser, userRole, true
            
        } else if (currentEnv == Environment.TEST) {

        } else if (currentEnv == Environment.PRODUCTION) {
        }
    }
    def destroy = {
    }
}
