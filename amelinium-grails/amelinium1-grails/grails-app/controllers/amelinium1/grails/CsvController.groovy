package amelinium1.grails

import com.tomtom.amelinium.backlogservice.serializer.WikiToHTMLSerializer;
import com.tomtom.woj.amelinium.journal.converter.AbsoluteToCumulativeConverterInPlace
import com.tomtom.woj.amelinium.journal.io.BacklogJournalReader
import com.tomtom.woj.amelinium.journal.model.BacklogChunk
import com.tomtom.woj.amelinium.journal.operations.BacklogChunksMerger
import com.tomtom.woj.amelinium.journal.operations.DoneLinesRemover
import com.tomtom.woj.amelinium.journal.operations.MergedBacklogColumnSorter
import com.tomtom.woj.amelinium.journal.updating.BacklogAndJournalUpdater;
import com.tomtom.woj.amelinium.plots.burndown.BacklogJournalSubtractorIntoBurndown
import com.tomtom.woj.amelinium.plots.burndown.BurndownModel
import com.tomtom.woj.amelinium.plots.burndown.BurndownModelFactory
import com.tomtom.woj.amelinium.plots.burndown.BurndownPlotJavascriptGenerator
import com.tomtom.woj.amelinium.plots.burnup.BurnupModel
import com.tomtom.woj.amelinium.plots.burnup.BurnupModelFactory
import com.tomtom.woj.amelinium.plots.burnup.BurnupPlotJavascriptGenerator
import com.tomtom.woj.amelinium.plots.burnup.BurnupTableGenerator

import grails.plugin.springsecurity.annotation.Secured;

import org.joda.time.DateTime
import org.springframework.dao.DataIntegrityViolationException;

class CsvController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def ProjectService projectService
    def springSecurityService
    def BacklogJournalReader reader = new BacklogJournalReader();
    def AbsoluteToCumulativeConverterInPlace cumulativeConverter = new AbsoluteToCumulativeConverterInPlace();
    def BacklogChunksMerger merger = new BacklogChunksMerger();
    def BurnupModelFactory burnupModelFactory = new BurnupModelFactory();
    def BurnupPlotJavascriptGenerator burnupGenerator = new BurnupPlotJavascriptGenerator();
    def MergedBacklogColumnSorter columnSorter = new MergedBacklogColumnSorter();
    def DoneLinesRemover doneLinesRemover = new DoneLinesRemover();
    def BacklogJournalSubtractorIntoBurndown subtractor = new BacklogJournalSubtractorIntoBurndown();
    def BurndownModelFactory burndownModelFactory = new BurndownModelFactory();
    def BurndownPlotJavascriptGenerator burndownGenerator = new BurndownPlotJavascriptGenerator();
    def BacklogAndJournalUpdater journalUpdater = new BacklogAndJournalUpdater();
    def WikiToHTMLSerializer htmlSerializer = new WikiToHTMLSerializer();
    def BurnupTableGenerator burnupTableGenerator = new BurnupTableGenerator();
	
    def show(Long id) {
		def projectInstance = Project.get(id)
        if (!projectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), id])
			redirect(controller:"project", action: "list")
            return
        }
		def csvInstance = projectInstance.revision.csv
        
        String text = htmlSerializer.convert(csvInstance.text);

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
		projectService.updateCsv(projectInstance, params.text, params.comment, springSecurityService.getCurrentUser().getUsername())
        
        flash.message = message(code: 'default.updated.message', args: [message(code: 'csv.label', default: 'Csv'), "of project \""+projectInstance.name+"\""])
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
        
        String restoredFrom = "Restored from revision - "+projectInstance.revision.ver
		
		projectService.updateCsv(projectInstance, csvInstance.text, restoredFrom, springSecurityService.getCurrentUser().getUsername())

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
        
        String text = htmlSerializer.convert(csvInstance.text);

		render(view:"show", model: [csvInstance: csvInstance, projectInstance: projectInstance, text:text]);
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
    
    def plot(Long id){ 
        def projectInstance = Project.get(id)
        if (!projectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), id])
            redirect(controller:"project", action: "list")
            return
        }
        def csvInstance = projectInstance.revision.csv
        
        if(csvInstance.text.size()<1)
        {
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
        else
        {
            isCumulative = false;
        }
        
        ArrayList<BacklogChunk> chunks = reader.readFromStringNullAllowed(csvInstance.text);
        if(chunks.size()==0){
            flash.message = message(code: 'default.not.valid.csv.message')
            redirect(action: "show", id: projectInstance.id)
            return
        }
        
        if(!isCumulative) {
            cumulativeConverter.convertIntoCumulative(chunks);
        }
        BacklogChunk merged = merger.mergeCumulativeChunks(chunks);
        
        doneLinesRemover.removeDoneLinesFromCumulativeMerged(merged);
        
        columnSorter.sortColumns(merged);
        BurnupModel burnupModel = burnupModelFactory.createModel(merged, dailyVelocity, dailyScopeIncrease);
        String chart1 = burnupGenerator.generateBurnup(burnupModel, "chart1", "Burnup chart");
        
/*        BacklogChunk merged2 = subtractor.subtractBurnedFromMerged(merged);
        BurndownModel burndownModel = burndownModelFactory.createModel(merged2, dailyVelocity+dailyScopeIncrease); // ????? effectiveVelocity
        String chart2 = burndownGenerator.generateBurndown(burndownModel, "chart2", "Burndown chart");*/
        
        String burnupTable = burnupTableGenerator.generateTable(burnupModel);
        String newTable = "<table class=\"table\""+burnupTable.substring(17);
        
        render(view: "plot", model: [csvInstance: csvInstance, projectInstance: projectInstance, chart:chart1, burnuptable:newTable])//chart2:chart2])
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
        
        String updatedJournal = journalUpdater.generateUpdatedString(new DateTime(), backlogInstance.text, csvInstance.text, true, true, true, true);
        
        csvInstance.text = updatedJournal;
        
        flash.message = message(code: 'default.recalculated.message', args: [
            message(code: 'backlog.label', default: 'Backlog'),
            "of project \""+projectInstance.name+"\""
        ])

        redirect(action: "show", id: projectInstance.id)
    }
}
