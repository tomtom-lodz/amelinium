package amelinium1.grails

import grails.plugin.springsecurity.annotation.Secured;

import org.springframework.dao.DataIntegrityViolationException;

class CsvController {

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
        def csvInstance = projectInstance.revision.csv
        String text = coreService.serializeText(csvInstance.text)

        [csvInstance: csvInstance, projectInstance: projectInstance, text:text]
    }
    @Secured(['ROLE_USER'])
    def edit(Long id) {
        def projectInstance = Project.get(id)
        if (!projectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), id])
            redirect(controller:"project", action: "list")
            return
        }
        def csvInstance = projectInstance.revision.csv

        [csvInstance: csvInstance, projectInstance: projectInstance]
    }

    def update(Long id, Long version) {
        def projectInstance = Project.get(id)
        if (!projectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), id])
            redirect(controller:"project", action: "list")
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
        projectService.updateCsv(projectInstance.id, params.text, params.comment, springSecurityService.getCurrentUser().getUsername())

        flash.message = message(code: 'default.updated.message', args: [
            message(code: 'csv.label', default: 'Csv'),
            "of project \""+projectInstance.name+"\""
        ])
        redirect(action: "show", id: projectInstance.id)
    }

    def updateProject(Long id, Long version){
        def projectInstance = Project.get(id)
        if (!projectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), id])
            redirect(controller:"project", action: "list")
            return
        }
        projectInstance.sprintLength = params.sprintLength.toInteger()
        projectInstance.velocity = params.velocity.toInteger()
        projectInstance.scopeIncrease = params.scopeIncrease.toInteger()

        if (!projectInstance.save(flush: true)) {
            render(view: "edit", model: [projectInstance: projectInstance])
            return
        }

        redirect(action: "plot", id: projectInstance.id)
        return
    }

    @Secured(['ROLE_USER'])
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

        String restoredFrom = "Restored from revision - "+csvInstance.ver

        projectService.updateCsv(projectInstance.id, csvInstance.text, restoredFrom, springSecurityService.getCurrentUser().getUsername())

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

        String text = coreService.serializeText(csvInstance.text);

        render(view:"show", model: [csvInstance: csvInstance, projectInstance: projectInstance, text:text]);
    }

    def listRevision(Long id) {
        def projectInstance = Project.get(id)
        if (!projectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), id])
            redirect(action: "list")
            return
        }

        params.put("display", 'csv')

        redirect(controller: "Revision", action:"list", id:projectInstance.id, params:params)
    }
    @Secured(['ROLE_USER'])
    def plot(Long id){
        def projectInstance = Project.get(id)
        if (!projectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), id])
            redirect(controller:"project", action: "list")
            return
        }
        def csvInstance = projectInstance.revision.csv

        if(csvInstance.text.size()<1) {
            flash.message = message(code: 'default.empty.csv', default: 'Cannot create plot, csv is empty!')
            redirect(controller:"project", action: "list")
            return
        }

        double dailyVelocity = projectInstance.velocity*1.0/projectInstance.sprintLength
        double dailyScopeIncrease = projectInstance.scopeIncrease*1.0/projectInstance.sprintLength

        boolean isCumulative
        if(params.cumulative){
            isCumulative = params.cumulative
        }
        else {
            isCumulative = false;
        }

        String [] chartAndTable = coreService.createCsvChartAndTable(csvInstance.text, isCumulative, dailyVelocity, dailyScopeIncrease,"chart1")

        /*        BacklogChunk merged2 = subtractor.subtractBurnedFromMerged(merged);
         BurndownModel burndownModel = burndownModelFactory.createModel(merged2, dailyVelocity+dailyScopeIncrease); // ????? effectiveVelocity
         String chart2 = burndownGenerator.generateBurndown(burndownModel, "chart2", "Burndown chart");*/

        String newTable = "<table class=\"table\""+chartAndTable[1].substring(17);
        String chart1 = chartAndTable[0];
        
        render(view: "plot", model: [csvInstance: csvInstance, projectInstance: projectInstance, chart:chart1, burnuptable:newTable])//chart2:chart2])
    }
    
    @Secured(['ROLE_USER'])
    def recalculate(Long id){
        def projectInstance = Project.get(id)
        if (!projectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), id])
            redirect(controller:"project", action: "list")
            return
        }
        def backlogInstance = projectInstance.revision.backlog
        def csvInstance = projectInstance.revision.csv
        String updatedJournal;
        try {
            updatedJournal =  coreService.recalculateCsv(backlogInstance.text, csvInstance.text, false, true, true, true);
        } catch (IndexOutOfBoundsException e) {
            updatedJournal = coreService.recalculateCsv(backlogInstance.text, csvInstance.text, true, true, true, true);
        }
        println updatedJournal

        projectService.updateCsv(projectInstance.id, updatedJournal, "Recalculated csv", springSecurityService.getCurrentUser().getUsername())
        
        flash.message = message(code: 'default.recalculated.message', args: [
            message(code: 'csv.label', default: 'Csv'),
            "of project \""+projectInstance.name+"\""
        ])

        redirect(action: "show", id: projectInstance.id)
    }
}
