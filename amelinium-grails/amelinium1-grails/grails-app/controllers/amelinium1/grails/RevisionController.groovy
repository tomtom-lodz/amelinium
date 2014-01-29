package amelinium1.grails

import org.springframework.dao.DataIntegrityViolationException

class RevisionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    String displayType
	
    def index() {
        redirect(action: "list", params: params)
    }

    def list(Long id) {
        if(!params.order){
            params.order = "desc"
        }
        if(!params.sort){
            params.sort = "id"
        }
        def projectInstance = Project.get(id)
        if (!projectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), id])
            redirect(controller:"project", action: "list")
            return
        }

        def revisions = Revision.findAllByProject(projectInstance, params)
        def displayType = params.display

        [revisionInstanceList: revisions, revisionInstanceTotal: revisions.size(), projectInstance: projectInstance, display:displayType]
    }
}
