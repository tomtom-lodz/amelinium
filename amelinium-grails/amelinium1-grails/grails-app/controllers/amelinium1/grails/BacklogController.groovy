package amelinium1.grails

import grails.plugin.springsecurity.annotation.Secured;

import org.springframework.dao.DataIntegrityViolationException



class BacklogController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def ProjectService projectService
    def springSecurityService
    def CoreService coreService

    def show(Long id) {
        def projectInstance = Project.get(id)
        if (!projectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), id])
            redirect(controller:"project", action: "list")
            return
        }
        def backlogInstance = projectInstance.revision.backlog
        
        String wiki = coreService.serializeText(backlogInstance.text)

        [backlogInstance: backlogInstance, projectInstance: projectInstance, text:wiki]
    }

    @Secured(['ROLE_USER'])
    def edit(Long id) {
        def projectInstance = Project.get(id)
        if (!projectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), id])
            redirect(controller:"project", action: "list")
            return
        }
        def backlogInstance = projectInstance.revision.backlog

        [backlogInstance: backlogInstance, projectInstance: projectInstance]
    }

    def update(Long id, Long version) {
        def projectInstance = Project.get(id)
        if (!projectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), id])
            redirect(controller:"project", action: "list")
            return
        }
        def backlogInstance = projectInstance.revision.backlog
        if (version != null) {
            if (projectInstance.revision.ver > version) {
                backlogInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'backlog.label', default: 'Backlog')] as Object[],
                        "Another user has updated this Backlog while you were editing")
                render(view: "edit", model: [backlogInstance: backlogInstance, projectInstance: projectInstance])
                return
            }
        }
        
        projectService.updateBacklog(projectInstance.id, params.text, params.comment, "Not recalculated", springSecurityService.getPrincipal().getDn().split(",")[0].substring(3))

        flash.message = message(code: 'default.updated.message', args: [
            message(code: 'backlog.label', default: 'Backlog'),
            "of project \""+projectInstance.name+"\""
        ])
        redirect(action: "show", id: projectInstance.id)
    }
    @Secured(['ROLE_USER'])
    def restore(Long id) {
        def backlogInstance = Backlog.get(id)
        if (!backlogInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'backlog.label', default: 'Backlog'), id])
            redirect(controller:"project", action: "list")
            return
        }

		Project projectInstance = Project.getProjectInstance('select p from Project p inner join p.revisions r where r.backlog=:backlog', [backlog: backlogInstance])
        if (!projectInstance) {
            flash.message = message(code: 'backlog.didnotfindproject', default: 'Did not find project for Backlog')
            redirect(controller:"project", action: "list")
            return
        }

        String restoredFrom = "Restored from revision - "+projectInstance.revision.ver
        
        projectService.updateBacklog(projectInstance.id, backlogInstance.text, restoredFrom, "Not recalculated", springSecurityService.getPrincipal().getDn().split(",")[0].substring(3))

        redirect(action: "show", id: projectInstance.id)
    }

    def showRevision(Long id) {
        def backlogInstance = Backlog.get(id)
        if (!backlogInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'backlog.label', default: 'Backlog'), id])
            redirect(controller:"project", action: "list")
            return
        }

		Project projectInstance = Project.getProjectInstance('select p from Project p inner join p.revisions r where r.backlog=:backlog', [backlog: backlogInstance])
        if (!projectInstance) {
            flash.message = message(code: 'backlog.didnotfindproject', default: 'Did not find project for Backlog')
            redirect(controller:"project", action: "list")
            return
        }
        
        String wiki = coreService.serializeText(backlogInstance.text)

        render(view:"show", model: [backlogInstance: backlogInstance, projectInstance: projectInstance, text:wiki]);
    }

    def listRevision(Long id) {
        def projectInstance = Project.get(id)
        if (!projectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), id])
            redirect(controller:"project", action: "list")
            return
        }

        params.put("display", 'backlog')

        redirect(controller: "Revision", action:"list", id:projectInstance.id, params:params)
    }

    def recalculate(Long id){

        def projectInstance = Project.get(id)
        if (!projectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), id])
            redirect(controller:"project", action: "list")
            return
        }
        def backlogInstance = projectInstance.revision.backlog
        def csvInstance = projectInstance.revision.csv

        String oldContent = backlogInstance.text + "\nBACKLOG END"
        String newBacklog = coreService.recalculateBacklog(oldContent)
        
        String updatedJournal;
        try {
            updatedJournal =  coreService.recalculateCsv(backlogInstance.text, csvInstance.text, false, true, true, true);
        } catch (IndexOutOfBoundsException e) {
            updatedJournal = coreService.recalculateCsv(backlogInstance.text, csvInstance.text, true, true, true, true);
        }
        
        projectService.updateBacklogAndCsv(projectInstance.id, newBacklog, updatedJournal, "Recalculate backlog and csv", "Recalculated", springSecurityService.getPrincipal().getDn().split(",")[0].substring(3))
        
        flash.message = message(code: 'default.recalculated.message', args: [
            message(code: 'backlog.label', default: 'Backlog'),
            "of project \""+projectInstance.name+"\""
        ])

        redirect(action: "show", id: projectInstance.id)
    }
}
