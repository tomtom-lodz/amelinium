package amelinium1.grails

import org.springframework.dao.DataIntegrityViolationException

class RevisionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	String displayType
    def index() {
        redirect(action: "list", params: params)
    }
		
    def list(Long id) {
        params.max = 10//Math.min(max ?: 10, 100)
		def projectInstance = Project.get(id)
		if (!projectInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), id])
			redirect(controllerName:"project", action: "list")
			return
		}
		params.sort='id'
		params.order='asc'
		def revisions = Revision.findAllByProject(projectInstance, params)
		def displayType = params.get("display")

        [revisionInstanceList: revisions, revisionInstanceTotal: revisions.size(), projectInstance: projectInstance, display:displayType]
    }

    def create() {
        [revisionInstance: new Revision(params)]
    }

    def save() {
        def revisionInstance = new Revision(params)
        if (!revisionInstance.save(flush: true)) {
            render(view: "create", model: [revisionInstance: revisionInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'revision.label', default: 'Revision'), revisionInstance.id])
        redirect(action: "show", id: revisionInstance.id)
    }

    def show(Long id) {
        def revisionInstance = Revision.get(id)
        if (!revisionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'revision.label', default: 'Revision'), id])
            redirect(action: "list")
            return
        }

        [revisionInstance: revisionInstance]
    }

    def edit(Long id) {
        def revisionInstance = Revision.get(id)
        if (!revisionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'revision.label', default: 'Revision'), id])
            redirect(action: "list")
            return
        }

        [revisionInstance: revisionInstance]
    }

    def update(Long id, Long version) {
        def revisionInstance = Revision.get(id)
        if (!revisionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'revision.label', default: 'Revision'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (revisionInstance.version > version) {
                revisionInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'revision.label', default: 'Revision')] as Object[],
                          "Another user has updated this Revision while you were editing")
                render(view: "edit", model: [revisionInstance: revisionInstance])
                return
            }
        }

        revisionInstance.properties = params

        if (!revisionInstance.save(flush: true)) {
            render(view: "edit", model: [revisionInstance: revisionInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'revision.label', default: 'Revision'), revisionInstance.id])
        redirect(action: "show", id: revisionInstance.id)
    }

    def delete(Long id) {
        def revisionInstance = Revision.get(id)
        if (!revisionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'revision.label', default: 'Revision'), id])
            redirect(action: "list")
            return
        }

        try {
            revisionInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'revision.label', default: 'Revision'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'revision.label', default: 'Revision'), id])
            redirect(action: "show", id: id)
        }
    }
}
