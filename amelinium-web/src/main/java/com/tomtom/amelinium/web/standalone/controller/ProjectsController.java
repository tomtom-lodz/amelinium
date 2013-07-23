package com.tomtom.amelinium.web.standalone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.tomtom.amelinium.db.beans.Project;
import com.tomtom.amelinium.db.logic.ProjectDbLogic;
import com.tomtom.amelinium.db.results.ResultProject;
import com.tomtom.amelinium.projectservice.corrector.ProjectCorrector;
import com.tomtom.amelinium.web.standalone.model.ProjectNameForm;

/**
 * Controller for all the actions concerning projects as wholes. Backlogs and
 * charts are handled by different controllers.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
@Controller
public class ProjectsController {

	@Autowired
	private ProjectDbLogic projectDbLogic;// = new ProjectDbLogic();
	@Autowired
	private ProjectCorrector projectCorrector;

	@RequestMapping("/projects/")
	public ModelAndView mainLayoutHandler() {
		List<Project> projectList = projectDbLogic.getAllProjects();
		ModelAndView model =  new ModelAndView("standalone/projectsPage");
		model.addObject("projects",projectList);
		model.addObject("size",projectList.size());
		return model;

	}

	/**
	 * Deletes a project from the database. Redirects to the projects list.
	 * Deleted project corresponds to the path variable id.
	 * 
	 * @param id
	 *            id of the project to be deleted
	 */
	@RequestMapping(value = "/project/{id}/delete", method = RequestMethod.GET)
	public String deleteProject(@PathVariable int id) {
		projectCorrector.deleteProject(id);

		return "redirect:/web/projects/";
	}

	/**
	 * Displays rename form. Displayed form contains current name of the
	 * project. Project to be renamed corresponds to the path variable id.
	 * 
	 * @param id
	 *            id of the project to be renamed
	 */
	@RequestMapping(value = "/project/{id}/rename", method = RequestMethod.GET)
	public ModelAndView renameProject(@PathVariable int id) {
		ResultProject project = projectDbLogic.getCurrentRevision(id);
		ModelAndView model = new ModelAndView("standalone/renameProjectPage");
		model.addObject("command", new ProjectNameForm(project.getName()));
		return model;
	}

	/**
	 * Renames a project in the database. Redirects to the projects list.
	 * Renamed project corresponds to the path variable id.
	 * 
	 * @param id
	 *            id of the project to be renamed
	 * @param form
	 *            spring form containing new name for the project
	 */
	@RequestMapping(value = "/project/{id}/rename", params = "rename", method = RequestMethod.POST)
	public String renameProject(@PathVariable int id,
			final @ModelAttribute("command") ProjectNameForm form,
			final Errors errors, final ModelMap modelMap,
			final SessionStatus status) {
		projectDbLogic.updateName(id, form.getProjectName());
		return "redirect:/web/projects/";
	}
}
