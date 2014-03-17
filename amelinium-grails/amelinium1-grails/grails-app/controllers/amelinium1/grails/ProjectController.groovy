package amelinium1.grails

import java.util.Calendar;
import java.util.regex.Matcher
import java.util.regex.Pattern

import grails.plugin.springsecurity.annotation.Secured;

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.core.userdetails.User

class ProjectController {

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def ProjectService projectService
	def springSecurityService

	def Pattern digit = Pattern.compile("\\d+")

	def index() {

		redirect(action: "list", params: params)
	}

	def list() {
		if(!params.max) {
			params.max = 10
		}
		if(!params.order){
			params.order = 'asc'
		}
		if(!params.sort){
			params.sort = 'name'
		}

		[projectInstanceList: Project.list(params), projectInstanceTotal: Project.count(), projectsMax:params.max, sorted:params.sort, ordered:params.order]
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
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'project.label', default: 'Project'),
				id
			])
			redirect(action: "list")
			return
		}

		params.put("display", 'project')

		redirect(controller: "Revision", action:"list", id:projectInstance.id, params:params)
	}

	@Secured(['ROLE_USER'])
	def create() {
		params.multilineFeature = true
		params.addNewFeatureGroups = true
		[projectInstance: new Project(params)]
	}

	def save() {
		Matcher sprintLength = digit.matcher(params.sprintLength)
		Matcher velocity = digit.matcher(params.velocity)
		Matcher scopeIncrease = digit.matcher(params.scopeIncrease)
		
		if(!sprintLength.find()||!velocity.find()||!scopeIncrease.find()) {
			flash.message = message(code: 'default.invalid.params', default: 'Cannot create project with parameters using non-digits!')
			redirect(action:"create", params:params)
			return
		}
		else if(params.sprintLength.toInteger()<1||params.velocity.toInteger()<1||params.scopeIncrease.toInteger()<0) {
			flash.message = message(code: 'default.invalid.params', default: 'Cannot create project with parameters using invalid values!')
			redirect(action:"create", params:params)
			return
		}

		String ldapUserName = springSecurityService.getPrincipal().getDn().split(",")[0].substring(3)
		
		def projectInstance = projectService.createProject(params.name,
														   ldapUserName,
														   params.sprintLength.toInteger(),
														   params.velocity.toInteger(),
														   params.scopeIncrease.toInteger(),
														   params.cumulative.toBoolean().booleanValue(),
														   params.multilineFeature.toBoolean().booleanValue(),
														   params.addNewFeatureGroups.toBoolean().booleanValue())
		if (projectInstance.hasErrors()) {
			render(view: "create", model: [projectInstance: projectInstance])
			return
		}

		flash.message = message(code: 'default.created.message', args: [
			message(code: 'project.label', default: 'Project'),
			"\""+projectInstance.name+"\""
		])
		redirect(action: "list")
		return
	}

	def show(Long id) {
		def projectInstance = Project.get(id)
		if (!projectInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'project.label', default: 'Project'),
				id
			])
			redirect(action: "list")
			return
		}

		[projectInstance: projectInstance]
	}

	@Secured(['ROLE_USER'])
	def edit(Long id) {
		def projectInstance = Project.get(id)
		if (!projectInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'project.label', default: 'Project'),
				id
			])
			redirect(action: "list")
			return
		}
		[projectInstance: projectInstance]
	}

	def update(Long id, Long version) {
		def projectInstance = Project.get(id)
		if (!projectInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'project.label', default: 'Project'),
				id
			])
			redirect(action: "list")
			return
		}

		if (version != null) {
			if (projectInstance.revision.ver > version) {
				projectInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						[
							message(code: 'project.label', default: 'Project')] as Object[],
						"Another user has updated this Project while you were editing")
				render(view: "edit", model: [projectInstance: projectInstance])
				return
			}
		}

		projectInstance.name = params.name
		projectInstance.status = params.status
		projectInstance.editedBy = springSecurityService.getPrincipal().getDn().split(",")[0].substring(3)
		projectInstance.sprintLength = params.sprintLength.toInteger()
		projectInstance.velocity = params.velocity.toInteger()
		projectInstance.scopeIncrease = params.scopeIncrease.toInteger()
		projectInstance.isCumulative = params.cumulative.toBoolean().booleanValue()
		projectInstance.multilineFeature = params.multilineFeature.toBoolean().booleanValue()
		projectInstance.addNewFeatureGroups = params.addNewFeatureGroups.toBoolean().booleanValue()

		if (!projectInstance.save(flush: true)) {
			render(view: "edit", model: [projectInstance: projectInstance])
			return
		}

		flash.message = message(code: 'default.updated.message', args: [
			message(code: 'project.label', default: 'Project'),
			"\""+projectInstance.name+"\""
		])
		redirect(action: "list")
		return
	}
}
