package amelinium1.grails

import org.springframework.dao.DataIntegrityViolationException

class BacklogController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	ProjectService projectService

    def show(Long id) {
		def projectInstance = Project.get(id)
        if (!projectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), id])
			redirect(controllerName:"project", action: "list")
            return
        }
		def backlogInstance = projectInstance.revision.backlog
		
		[backlogInstance: backlogInstance, projectInstance: projectInstance]
    }

    def edit(Long id) {
		def projectInstance = Project.get(id)
		if (!projectInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), id])
			redirect(controllerName:"project", action: "list")
			return
		}
		def backlogInstance = projectInstance.revision.backlog
		
        [backlogInstance: backlogInstance, projectInstance: projectInstance]
    }

    def update(Long id, Long version) {
		def projectInstance = Project.get(id)
		if (!projectInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), id])
			redirect(controllerName:"project", action: "list")
			return
		}
		def backlogInstance = projectInstance.revision.backlog
	    if (version != null) {
	        if (projectInstance.version > version) {
	            backlogInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
	                      [message(code: 'backlog.label', default: 'Backlog')] as Object[],
	                      "Another user has updated this Backlog while you were editing")
	            render(view: "edit", model: [backlogInstance: backlogInstance, projectInstance: projectInstance])
	            return
	        }
	    }
		projectService.updateBacklog(projectInstance, params.text, params.comment)
		
        flash.message = message(code: 'default.updated.message', args: [message(code: 'project.label', default: 'Project'), projectInstance.id])
		redirect(action: "show", id: projectInstance.id)
    }
	
    def restore(Long id) {
		def backlogInstance = Backlog.get(id)
        if (!backlogInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'backlog.label', default: 'Backlog'), id])
			redirect(controller:"project", action: "list")
            return
        }
		
		def projectInstance = Project.executeQuery(
			'select p from Project p inner join p.revisions r where r.backlog=:backlog',
			[backlog: backlogInstance]).first()

		if (!projectInstance) {
			flash.message = message(code: 'backlog.didnotfindproject', default: 'Did not find project for Backlog')
			redirect(controller:"project", action: "list")
			return
		}
		
		projectService.updateBacklog(projectInstance, backlogInstance.text)

		redirect(action: "show", id: projectInstance.id)
	}
	
	def showRevision(Long id) {
		def backlogInstance = Backlog.get(id)
		if (!backlogInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'backlog.label', default: 'Backlog'), id])
			redirect(controller:"project", action: "list")
			return
		}
		
		def projectInstance = Project.executeQuery(
			'select p from Project p inner join p.revisions r where r.backlog=:backlog',
			[backlog: backlogInstance]).first()
			
		if (!projectInstance) {
			flash.message = message(code: 'backlog.didnotfindproject', default: 'Did not find project for Backlog')
			redirect(controller:"project", action: "list")
			return
		}

		render(view:"show", model: [backlogInstance: backlogInstance, projectInstance: projectInstance]);
	}

	def listRevision(Long id)
	{
		def projectInstance = Project.get(id)
		if (!projectInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), id])
			redirect(action: "list")
			return
		}
		
		params.put("display", 'backlog')
		
		redirect(controller: "Revision", action:"list", id:projectInstance.id, params:params)
	}

}
