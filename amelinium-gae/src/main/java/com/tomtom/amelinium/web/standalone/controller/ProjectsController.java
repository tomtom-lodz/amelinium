package com.tomtom.amelinium.web.standalone.controller;

import java.util.ArrayList;
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

import com.tomtom.amelinium.projectservice.model.Company;
import com.tomtom.amelinium.projectservice.model.Project;
import com.tomtom.amelinium.projectservice.model.ProjectDao;
import com.tomtom.amelinium.projectservice.snapshot.ProjectHistoryItem;
import com.tomtom.amelinium.web.standalone.helper.HistoryHelper;
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
	private Company company;

	@Autowired
	private ProjectDao projectDao;
	
	public void setCompany(Company company) {
		this.company = company;
	}
	
	public void setProjectsDao(ProjectDao projects) {
		this.projectDao = projects;
	}

	@RequestMapping("/projects/")
	public ModelAndView mainLayoutHandler() {
//		ArrayList<Project> projectList = company.getProjects();
		List<Project> projectList = projectDao.getAll();
		return new ModelAndView("standalone/projectsPage", "projects",
				projectList);
	}

	/**
	 * Deletes a project and redirects to the projects list. Deleted project
	 * corresponds to the path variable id. Id-s of the remaining projects are
	 * set to their indices in the list.
	 * 
	 * @param id
	 *            id of the project to be renamed
	 */
	@RequestMapping(value = "/project/{id}/delete", method = RequestMethod.GET)
	public String deleteProject(@PathVariable Long id) {
//		ArrayList<Project> projectList = company.getProjects();
//
//		int index = Integer.parseInt(id);
//		projectList.remove(index);
//
//		for (int i = 0; i < projectList.size(); i++) {
//			projectList.get(i).setId(i);
//		}
		
		projectDao.delete(id);

		return "redirect:/projects/";
	}

	/**
	 * Displays rename form. Displayed form contains current name of the
	 * project. Project to be renamed corresponds to the path variable id.
	 * 
	 * @param id
	 *            id of the project to be renamed
	 */
	@RequestMapping(value = "/project/{id}/rename", method = RequestMethod.GET)
	public ModelAndView renameProject(@PathVariable Long id) {
//		ArrayList<Project> projectList = company.getProjects();
//
//		int index = Integer.parseInt(id);
//		Project project = projectList.get(index);
		Project project = projectDao.get(id);

		ModelAndView model = new ModelAndView("standalone/renameProjectPage");
		model.addObject("command", new ProjectNameForm(project.getName()));
		return model;
	}

	/**
	 * Renames a project and redirects to the projects list. Renamed project
	 * corresponds to the path variable id.
	 * 
	 * @param id
	 *            id of the project to be renamed
	 * @param form
	 *            spring form containing new name for the project
	 */
	@RequestMapping(value = "/project/{id}/rename", params = "rename", method = RequestMethod.POST)
	public String renameProject(@PathVariable Long id,
			final @ModelAttribute("command") ProjectNameForm form,
			final Errors errors, final ModelMap modelMap,
			final SessionStatus status) {
//		ArrayList<Project> projectList = company.getProjects();
//
//		int index = Integer.parseInt(id);
//		Project project = projectList.get(index);
//		project.setName(form.getProjectName());
		
		Project project = projectDao.get(id);
		project.setName(form.getProjectName());
		projectDao.store(project);

		return "redirect:/projects/";
	}

	@RequestMapping(value = "/project/{id}/history/{part}", method = RequestMethod.GET)
	public ModelAndView showHistory(@PathVariable Long id,
			@PathVariable String part) {
		
		HistoryHelper helper = new HistoryHelper();
		
//		ArrayList<Project> projectList = this.company.getProjects();
//		int index = Integer.parseInt(id);
//		Project project = projectList.get(index);

		Project project = projectDao.get(id);
		
		int translation = Integer.parseInt(part);
		int finalIndex = helper.calculateFinalIndex(translation, project);
		int initialIndex = helper.calculateInitialIndex(finalIndex);
		
		ArrayList<ProjectHistoryItem> history = project.getPartOfHistory(
				initialIndex, finalIndex);
		
		int translationNewer = helper.calculateTranslationNewer(translation);
		int translationOlder = helper.calculateTranslationOlder(translation, project);

		ModelAndView model = new ModelAndView("standalone/projectHistoryPage");
		model.addObject("projectId", project.getId());
		model.addObject("history", history);
		model.addObject("translationNewer", translationNewer);
		model.addObject("translationOlder", translationOlder);

		return model;
	}
}
