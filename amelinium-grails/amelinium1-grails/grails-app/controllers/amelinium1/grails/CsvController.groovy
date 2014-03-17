package amelinium1.grails

import grails.plugin.springsecurity.annotation.Secured;

import java.util.regex.Matcher
import java.util.regex.Pattern;

import org.springframework.dao.DataIntegrityViolationException;

class CsvController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def ProjectService projectService
    def springSecurityService
    def CoreService coreService
	
	def Pattern digit = Pattern.compile("\\d+")

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
	
	@Secured(['ROLE_USER'])
	def save(Long id, Long version){
		def projectInstance = Project.get(id)
		if (!projectInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), id])
			redirect(controller:"project", action: "list")
			return
		}
		def csvInstance = projectInstance.revision.csv
		if (version != null) {
			if (projectInstance.revision.ver > version) {
				csvInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						[message(code: 'csv.label', default: 'Csv')] as Object[],
						"Another user has updated this Csv while you were editing")
				render(view: "edit", model: [csvInstance: csvInstance, projectInstance: projectInstance])
				return
			}
		}
		projectService.updateCsv(projectInstance.id, params.text, params.comment, springSecurityService.getPrincipal().getDn().split(",")[0].substring(3))

		flash.message = message(code: 'default.saved.message', args: [
			message(code: 'csv.label', default: 'Csv'),
			"of project \""+projectInstance.name+"\""
		])
		redirect(action: "edit", id: projectInstance.id)
	}
	
	@Secured(['ROLE_USER'])
    def update(Long id, Long version) {
        def projectInstance = Project.get(id)
        if (!projectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), id])
            redirect(controller:"project", action: "list")
            return
        }
        def csvInstance = projectInstance.revision.csv
        if (version != null) {
            if (projectInstance.revision.ver > version) {
                csvInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'csv.label', default: 'Csv')] as Object[],
                        "Another user has updated this Csv while you were editing")
                render(view: "edit", model: [csvInstance: csvInstance, projectInstance: projectInstance])
                return
            }
        }
        projectService.updateCsv(projectInstance.id, params.text, params.comment, springSecurityService.getPrincipal().getDn().split(",")[0].substring(3))

        flash.message = message(code: 'default.updated.message', args: [
            message(code: 'csv.label', default: 'Csv'),
            "of project \""+projectInstance.name+"\""
        ])
        redirect(action: "show", id: projectInstance.id)
    }
	
	@Secured(['ROLE_USER'])
    def updateProject(Long id){
        def projectInstance = Project.get(id)
        if (!projectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), id])
            redirect(action: "plotFromProject", id: projectInstance.id)
            return
        }
		
		Matcher sprintLength = digit.matcher(params.sprintLength)
		Matcher velocity = digit.matcher(params.velocity)
		Matcher scopeIncrease = digit.matcher(params.scopeIncrease)
		
		if(!sprintLength.find()||!velocity.find()||!scopeIncrease.find()) {
			flash.message = message(code: 'default.invalid.params', default: 'Cannot update project parameters with non-digits!')
			redirect(action: "plotFromProject", id: projectInstance.id)
			return
		}
		else if(params.sprintLength.toInteger()<1||params.velocity.toInteger()<1||params.scopeIncrease.toInteger()<0)
		{
			flash.message = message(code: 'default.invalid.params', default: 'Cannot update project parameters with invalid values!')
			redirect(action: "plotFromProject", id: projectInstance.id)
			return
		}
		
        projectInstance.sprintLength = params.sprintLength.toInteger()
        projectInstance.velocity = params.velocity.toInteger()
        projectInstance.scopeIncrease = params.scopeIncrease.toInteger()

        if (!projectInstance.save(flush: true)) {
            render(view: "edit", model: [projectInstance: projectInstance])
            return
        }

        redirect(action: "plotFromProject", id: projectInstance.id)
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
        Project projectInstance = Project.getProjectInstance('select p from Project p inner join p.revisions r where r.csv=:csv', [csv: csvInstance])
        if (!projectInstance) {
            flash.message = message(code: 'csv.didnotfindproject', default: 'Did not find project for Csv')
            redirect(controller:"project", action: "list")
            return
        }

        String restoredFrom = "Restored from revision - "+csvInstance.ver

        projectService.updateCsv(projectInstance.id, csvInstance.text, restoredFrom, springSecurityService.getPrincipal().getDn().split(",")[0].substring(3))

        redirect(action: "show", id: projectInstance.id)
    }

    def showRevision(Long id) {
        def csvInstance = Csv.get(id)
        if (!csvInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'csv.label', default: 'Csv'), id])
            redirect(controller:"project", action: "list")
            return
        }

        Project projectInstance = Project.getProjectInstance('select p from Project p inner join p.revisions r where r.csv=:csv', [csv: csvInstance])
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
            redirect(controller:"project", action: "list")
            return
        }

        params.put("display", 'csv')

        redirect(controller: "Revision", action:"list", id:projectInstance.id, params:params)
    }
	
    @Secured(['ROLE_USER'])
    def plotFromProject(Long id){
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

        String [] chartAndTable = coreService.createCsvChartAndTable(csvInstance.text, projectInstance.isCumulative, dailyVelocity, dailyScopeIncrease,"chart1")

        String newTable = "<table class=\"table\""+chartAndTable[1].substring(17);
        String chart1 = chartAndTable[0];
        
        render(view: "plot", model: [csvInstance: csvInstance, projectInstance: projectInstance, chart:chart1, burnuptable:newTable])
    }
    
	@Secured(['ROLE_USER'])
	def plotFromCsv(Long id){
		def csvInstance = Csv.get(id)
        if (!csvInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'csv.label', default: 'Csv'), id])
            redirect(controller:"project", action: "list")
            return
        }

        Project projectInstance = Project.getProjectInstance('select p from Project p inner join p.revisions r where r.csv=:csv', [csv: csvInstance])
        if (!projectInstance) {
            flash.message = message(code: 'csv.didnotfindproject', default: 'Did not find project for Csv')
            redirect(controller:"project", action: "list")
            return
        }

		if(csvInstance.text.size()<1) {
			flash.message = message(code: 'default.empty.csv', default: 'Cannot create plot, csv is empty!')
			redirect(controller:"project", action: "list")
			return
		}

		double dailyVelocity = projectInstance.velocity*1.0/projectInstance.sprintLength
		double dailyScopeIncrease = projectInstance.scopeIncrease*1.0/projectInstance.sprintLength

		String [] chartAndTable = coreService.createCsvChartAndTable(csvInstance.text, projectInstance.isCumulative, dailyVelocity, dailyScopeIncrease,"chart1")

		String newTable = "<table class=\"table\""+chartAndTable[1].substring(17);
		String chart1 = chartAndTable[0];
		
		render(view: "plot", model: [csvInstance: csvInstance, projectInstance: projectInstance, chart:chart1, burnuptable:newTable])
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
		String requestUrl = request.requestURL
		String backlogUrl = requestUrl.replaceFirst("grails/csv/recalculate.dispatch", "backlog/show/"+id);
        String updatedJournal = coreService.recalculateCsv(backlogUrl, csvInstance.text, projectInstance.isCumulative,
										 projectInstance.addNewFeatureGroups, true, projectInstance.multilineFeature);
									 
        projectService.updateCsv(projectInstance.id, updatedJournal, "Recalculated csv", springSecurityService.getPrincipal().getDn().split(",")[0].substring(3))
        
        flash.message = message(code: 'default.recalculated.message', args: [
            message(code: 'csv.label', default: 'Csv'),
            "of project \""+projectInstance.name+"\""
        ])

        redirect(action: "show", id: projectInstance.id)
    }
	
	def plotApiCsv(Long id){
		def csvInstance = Csv.get(id)
        if (!csvInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'csv.label', default: 'Csv'), id])
			render(view: "plot_template")
			return
        }

        Project projectInstance = Project.getProjectInstance('select p from Project p inner join p.revisions r where r.csv=:csv', [csv: csvInstance])
		if(!projectInstance){
			flash.message = message(code: 'default.fetch.project.error', default: "Couldn't find project in database")
			render(view: "plot_template")
			return
		}
				
		if(csvInstance.text.size()<1) {
			flash.message = message(code: 'default.empty.csv', default: 'Cannot create plot, csv is empty!')
			render(view: "plot_template")
			return
		}

		double dailyVelocity = projectInstance.velocity*1.0/projectInstance.sprintLength
		double dailyScopeIncrease = projectInstance.scopeIncrease*1.0/projectInstance.sprintLength

		String [] chartAndTable = coreService.createCsvChartAndTable(csvInstance.text, projectInstance.isCumulative, dailyVelocity, dailyScopeIncrease,"chart1")

		String chart1 = chartAndTable[0];
		
		render(view: "plot_template", model: [chart:chart1])
	}
	
	def plotApiProject(Long id){
		def projectInstance = Project.get(id)
        if (!projectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), id])
			render(view: "plot_template")
			return
        }
        def csvInstance = projectInstance.revision.csv
				
		if(csvInstance.text.size()<1) {
			flash.message = message(code: 'default.empty.csv', default: 'Cannot create plot, csv is empty!')
			render(view: "plot_template")
			return
		}

		double dailyVelocity = projectInstance.velocity*1.0/projectInstance.sprintLength
		double dailyScopeIncrease = projectInstance.scopeIncrease*1.0/projectInstance.sprintLength

		String [] chartAndTable = coreService.createCsvChartAndTable(csvInstance.text, projectInstance.isCumulative, dailyVelocity, dailyScopeIncrease,"chart1")

		String chart1 = chartAndTable[0];
		
		render(view: "plot_template", model: [chart:chart1])
	}
}
