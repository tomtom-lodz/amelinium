package amelinium1.grails

import org.springframework.dao.DataIntegrityViolationException

class CsvController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	ProjectService projectService
	
    def show(Long id) {
		def projectInstance = Project.get(id)
        if (!projectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), id])
			redirect(controllerName:"project", action: "list")
            return
        }
		def csvInstance = projectInstance.revision.csv

        [csvInstance: csvInstance, projectInstance: projectInstance]
    }

    def edit(Long id) {
		def projectInstance = Project.get(id)
		if (!projectInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), id])
			redirect(controllerName:"project", action: "list")
			return
		}
		def csvInstance = projectInstance.revision.csv

        [csvInstance: csvInstance, projectInstance: projectInstance]
    }

    def update(Long id, Long version) {
		def projectInstance = Project.get(id)
		if (!projectInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), id])
			redirect(controllerName:"project", action: "list")
			return
		}
		def csvInstance = projectInstance.revision.csv
	    if (version != null) {
	        if (projectInstance.version > version) {
	            csvInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
	                      [message(code: 'csv.label', default: 'Csv')] as Object[],
	                      "Another user has updated this Csv while you were editing")
	            render(view: "edit", model: [csvInstance: csvInstance, projectInstance: projectInstance])
	            return
	        }
	    }
		projectService.updateCsv(projectInstance, params.text, params.comment)
        
        flash.message = message(code: 'default.updated.message', args: [message(code: 'project.label', default: 'Project'), projectInstance.id])
		redirect(action: "show", id: projectInstance.id)
    }
	
    def restore(Long id) {
		def csvInstance = Csv.get(id)
        if (!csvInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'csv.label', default: 'Csv'), id])
			redirect(controller:"project", action: "list")
            return
        }
		
		def projectInstance = Project.executeQuery(
			'select p from Project p inner join p.revisions r where r.csv=:csv',
			[csv: csvInstance]).first()

		if (!projectInstance) {
			flash.message = message(code: 'csv.didnotfindproject', default: 'Did not find project for Csv')
			redirect(controller:"project", action: "list")
			return
		}
		
		projectService.updateCsv(projectInstance, csvInstance.text)

		redirect(action: "show", id: projectInstance.id)
	}
	
	def showRevision(Long id) {
		def csvInstance = Csv.get(id)
        if (!csvInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'csv.label', default: 'Csv'), id])
			redirect(controller:"project", action: "list")
            return
        }
		
		def projectInstance = Project.executeQuery(
			'select p from Project p inner join p.revisions r where r.csv=:csv',
			[csv: csvInstance]).first()

		if (!projectInstance) {
			flash.message = message(code: 'csv.didnotfindproject', default: 'Did not find project for Csv')
			redirect(controller:"project", action: "list")
			return
		}

		render(view:"show", model: [csvInstance: csvInstance, projectInstance: projectInstance]);
	}
	
	def listRevision(Long id)
	{
		def projectInstance = Project.get(id)
		if (!projectInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), id])
			redirect(action: "list")
			return
		}
		
		params.put("display", 'csv')
		
		redirect(controller: "Revision", action:"list", id:projectInstance.id, params:params)
	}
}
