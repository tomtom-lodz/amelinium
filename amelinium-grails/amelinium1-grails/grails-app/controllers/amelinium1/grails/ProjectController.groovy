package amelinium1.grails

import org.springframework.dao.DataIntegrityViolationException

class ProjectController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    ProjectService projectService

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        //params.max = Math.min(max ?: 10, 100)
        params.max = 10
        [projectInstanceList: Project.list(params), projectInstanceTotal: Project.count()]
    }

    def close(Long id) {
        def projectInstance = Project.get(id)
        if (!projectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), id])
            redirect(action: "list")
            return
        }
        projectInstance.setStatus("Closed")
        redirect(action: "show", id: projectInstance.id)
    }

    def showBacklog(Long id) {
        redirect(controller: "Backlog", action: "show", id:id)
    }

    def showCsv(Long id) {
        redirect(controller: "Csv", action: "show", id:id);
    }

    def listRevision(Long id) {
        def projectInstance = Project.get(id)
        if (!projectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), id])
            redirect(action: "list")
            return
        }

        params.put("display", 'project')

        redirect(controller: "Revision", action:"list", id:projectInstance.id, params:params)
    }

    def create() {
        [projectInstance: new Project(params)]
    }

    def save() {
        def projectInstance = projectService.createProject(params.name)
        if (projectInstance.hasErrors()) {
            render(view: "create", model: [projectInstance: projectInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'project.label', default: 'Project'), projectInstance.id])
        redirect(action: "show", id: projectInstance.id)
    }

    def show(Long id) {
        def projectInstance = Project.get(id)
        if (!projectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), id])
            redirect(action: "list")
            return
        }

        [projectInstance: projectInstance]
    }

    def edit(Long id) {
        def projectInstance = Project.get(id)
        if (!projectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), id])
            redirect(action: "list")
            return
        }

        [projectInstance: projectInstance]
    }

    def update(Long id, Long version) {
        def projectInstance = Project.get(id)
        if (!projectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (projectInstance.version > version) {
                projectInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'project.label', default: 'Project')] as Object[],
                        "Another user has updated this Project while you were editing")
                render(view: "edit", model: [projectInstance: projectInstance])
                return
            }
        }

        projectInstance.name = params.name

        if (!projectInstance.save(flush: true)) {
            render(view: "edit", model: [projectInstance: projectInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'project.label', default: 'Project'), projectInstance.id])
        redirect(action: "show", id: projectInstance.id)
    }

    def delete(Long id) {
        def projectInstance = Project.get(id)
        if (!projectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), id])
            redirect(action: "list")
            return
        }

        try {
            projectInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'project.label', default: 'Project'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'project.label', default: 'Project'), id])
            redirect(action: "show", id: id)
        }
    }
}
