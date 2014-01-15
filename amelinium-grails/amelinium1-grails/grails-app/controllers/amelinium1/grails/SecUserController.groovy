package amelinium1.grails

import org.springframework.dao.DataIntegrityViolationException

/**
 * SecUserController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class SecUserController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [secUserInstanceList: SecUser.list(params), secUserInstanceTotal: SecUser.count()]
    }

    def create() {
        [secUserInstance: new SecUser(params)]
    }

    def save() {
        def secUserInstance = new SecUser(params)
        if(SecUser.findByUsername(secUserInstance.username)){
            flash.message = message(code: 'default.user.exists.message', args: [secUserInstance.username])
            render(view: "create", model: [secUserInstance: secUserInstance])
            return
        }
        
        secUserInstance.accountExpired = false
        secUserInstance.accountLocked = false
        secUserInstance.passwordExpired = false
        
        if (!secUserInstance.save(flush: true)) {
            render(view: "create", model: [secUserInstance: secUserInstance])
            return
        }
        
        def secRole = SecRole.findByAuthority('ROLE_USER')
        SecUserSecRole.create secUserInstance, secRole, true

		flash.message = message(code: 'default.created.message', args: [message(code: 'secUser.label', default: 'SecUser'), secUserInstance.id])
        redirect(controller: "login", action: "auth", id: secUserInstance.id)
    }

    def show() {
        def secUserInstance = SecUser.get(params.id)
        if (!secUserInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'secUser.label', default: 'SecUser'), params.id])
            redirect(action: "list")
            return
        }

        [secUserInstance: secUserInstance]
    }

    def edit() {
        def secUserInstance = SecUser.get(params.id)
        if (!secUserInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'secUser.label', default: 'SecUser'), params.id])
            redirect(action: "list")
            return
        }

        [secUserInstance: secUserInstance]
    }

    def update() {
        def secUserInstance = SecUser.get(params.id)
        if (!secUserInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'secUser.label', default: 'SecUser'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (secUserInstance.version > version) {
                secUserInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'secUser.label', default: 'SecUser')] as Object[],
                          "Another user has updated this SecUser while you were editing")
                render(view: "edit", model: [secUserInstance: secUserInstance])
                return
            }
        }

        secUserInstance.properties = params

        if (!secUserInstance.save(flush: true)) {
            render(view: "edit", model: [secUserInstance: secUserInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'secUser.label', default: 'SecUser'), secUserInstance.id])
        redirect(action: "show", id: secUserInstance.id)
    }

    def delete() {
        def secUserInstance = SecUser.get(params.id)
        if (!secUserInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'secUser.label', default: 'SecUser'), params.id])
            redirect(action: "list")
            return
        }

        try {
            secUserInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'secUser.label', default: 'SecUser'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'secUser.label', default: 'SecUser'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
