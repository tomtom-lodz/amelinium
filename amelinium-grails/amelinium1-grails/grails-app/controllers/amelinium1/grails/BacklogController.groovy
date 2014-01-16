package amelinium1.grails

import grails.plugin.springsecurity.annotation.Secured;

import com.tomtom.amelinium.backlogservice.builders.BacklogModelBuilder
import com.tomtom.amelinium.backlogservice.corrector.*;
import com.tomtom.amelinium.backlogservice.model.BacklogModel
import com.tomtom.amelinium.backlogservice.serializer.BacklogModelSerializer
import com.tomtom.amelinium.backlogservice.serializer.WikiToHTMLSerializer
import com.tomtom.amelinium.chartservice.factory.ChartServiceFactory
import com.tomtom.amelinium.common.LineProducer
import com.tomtom.woj.amelinium.journal.updating.BacklogAndJournalUpdater

import org.joda.time.DateTime;
import org.springframework.dao.DataIntegrityViolationException



class BacklogController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def ProjectService projectService
    def springSecurityService
    def LineProducer producer = new LineProducer();
    def BacklogModelBuilder builder = new BacklogModelBuilder();
    def BacklogModelCorrector backlogModelCorrector = new BacklogModelCorrector();
    def BacklogModelSerializer generator = new BacklogModelSerializer();
    def BacklogAndJournalUpdater journalUpdater = new BacklogAndJournalUpdater();
    def WikiToHTMLSerializer htmlSerializer = new WikiToHTMLSerializer();

    def show(Long id) {
        def projectInstance = Project.get(id)
        if (!projectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), id])
            redirect(controller:"project", action: "list")
            return
        }
        def backlogInstance = projectInstance.revision.backlog
        
        String wiki = htmlSerializer.convert(backlogInstance.text);

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
            if (projectInstance.version > version) {
                backlogInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'backlog.label', default: 'Backlog')] as Object[],
                        "Another user has updated this Backlog while you were editing")
                render(view: "edit", model: [backlogInstance: backlogInstance, projectInstance: projectInstance])
                return
            }
        }
        
        projectService.updateBacklog(projectInstance, params.text, params.comment, springSecurityService.getCurrentUser().getUsername())

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

        def projectInstance = Project.executeQuery(
                'select p from Project p inner join p.revisions r where r.backlog=:backlog',
                [backlog: backlogInstance]).first()

        if (!projectInstance) {
            flash.message = message(code: 'backlog.didnotfindproject', default: 'Did not find project for Backlog')
            redirect(controller:"project", action: "list")
            return
        }

        String restoredFrom = "Restored from revision - "+projectInstance.revision.ver
        
        projectService.updateBacklog(projectInstance, backlogInstance.text,restoredFrom,springSecurityService.getCurrentUser().getUsername())

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
        
        String wiki = htmlSerializer.convert(backlogInstance.text);

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

        ArrayList<String> lines = producer.readLinesFromString(oldContent);
        BacklogModel backlogModel = builder.buildBacklogModel(lines,true);
        backlogModel = backlogModelCorrector.correctModelPoints(backlogModel);
        String newContent = generator.serializeModel(backlogModel);

        backlogInstance.text = newContent
        backlogInstance.state = "Recalculated"
        
        //generateUpdatedString(DateTime dateTime, String backlogContent, String journalContent, boolean isCumulative, boolean addNewFeatureGroups, boolean overwriteExistingDate,boolean allowingMultilineFeatures)
        
        DateTime dateTime  = new DateTime().toDateMidnight().toDateTime();
        String updatedJournal;
        try {
            updatedJournal = journalUpdater.generateUpdatedString(dateTime, backlogInstance.text, csvInstance.text, false, true, true, true);
            
        } catch (Exception e) {
            updatedJournal = journalUpdater.generateUpdatedString(dateTime, backlogInstance.text, csvInstance.text, true, true, true, true);
        }
        
        csvInstance.text = updatedJournal;
        
        flash.message = message(code: 'default.recalculated.message', args: [
            message(code: 'backlog.label', default: 'Backlog'),
            "of project \""+projectInstance.name+"\""
        ])

        redirect(action: "show", id: projectInstance.id)
    }
}
