package com.tomtom.amelinium.web.standalone.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tomtom.amelinium.projectservice.config.Messages;
import com.tomtom.amelinium.projectservice.corrector.ProjectCorrector;
import com.tomtom.amelinium.projectservice.model.Company;
import com.tomtom.amelinium.projectservice.model.Project;
import com.tomtom.amelinium.projectservice.model.ProjectDao;
import com.tomtom.amelinium.projectservice.snapshot.ProjectHistoryItem;
import com.tomtom.amelinium.web.standalone.helper.HistoryHelper;
import com.tomtom.amelinium.web.standalone.model.TextAreaFormBacklog;

/**
 * Controller for all the actions concerning backlogs.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
@Controller
public class BacklogController {

	@Autowired
	private Company company;
	
	@Autowired
	private ProjectDao projectDao;
	
	private ProjectCorrector corrector = new ProjectCorrector();
	
	public void setCompany(Company company) {
		this.company = company;
	}

	public void setProjectsDao(ProjectDao projects) {
		this.projectDao = projects;
	}
	
	/**
	 * Displays backlog page. Displayed backlog corresponds to the project
	 * specified by the path variable.
	 * 
	 * @param id
	 *            path variable of a project which backlog is to be displayed
	 */
	@RequestMapping(value = "/project/{id}/backlog/view", method = RequestMethod.GET)
	public ModelAndView mainLayoutHandler(@PathVariable Long id) {
//		ArrayList<Project> projectList = company.getProjects();
//		int index = Integer.parseInt(id);
//		Project project = projectList.get(index);
		
		Project project = projectDao.get(id);
		
		return new ModelAndView("standalone/backlogPage", "project", project);

	}

	/**
	 * Displays edit box for a backlog. The edit box contains serialized version
	 * of the backlog model (wiki markup). The content corresponds to the
	 * project specified by the path variable.
	 * 
	 * @param id
	 *            path variable of a project which backlog is to be edited
	 */
	@RequestMapping(value = "/project/{id}/backlog/edit", method = RequestMethod.GET)
	public ModelAndView editBacklogHandler(@PathVariable Long id) {
//		ArrayList<Project> projectList = company.getProjects();
//		int index = Integer.parseInt(id);
//		Project project = projectList.get(index);
		
		Project project = projectDao.get(id);
		
		ModelAndView model = new ModelAndView("standalone/backlogEditPage");
		model.addObject("project", project);
		model.addObject("command", new TextAreaFormBacklog(project.getBacklogWikiMarkup()));
		model.addObject("message", Messages.editBacklogMessage);
		return model;
	}

	/**
	 * Corrects backlog and displays backlog page with updated backlog. The
	 * backlog corresponds to the project specified by the path variable.
	 * 
	 * @param id
	 *            path variable of a project which backlog is to be updated
	 */
	@RequestMapping(value = "/project/{id}/backlog/view", params = "_updateBacklog", method = RequestMethod.POST)
	public ModelAndView updateBacklogHandler(@PathVariable Long id,
			@Valid TextAreaFormBacklog form, BindingResult result) {
//		ArrayList<Project> projectList = company.getProjects();
//		int index = Integer.parseInt(id);
//		Project project = projectList.get(index);
		
		Project project = projectDao.get(id);
		
		String wikiMarkupBacklog = form.getText();
		boolean allowingMultilineFeatures = form.getAllowMultilineFeatures();
		
		corrector.updateProjectWithNewBacklog(project, wikiMarkupBacklog,
				allowingMultilineFeatures);

		return new ModelAndView("standalone/backlogPage", "project", project);

	}

	@RequestMapping(value = "/project/{id}/backlog/history/{part}", method = RequestMethod.GET)
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

		ModelAndView model = new ModelAndView("standalone/backlogHistoryPage");
		model.addObject("projectId", project.getId());
		model.addObject("history", history);
		model.addObject("translationNewer", translationNewer);
		model.addObject("translationOlder", translationOlder);

		return model;
	}
	
	@RequestMapping(value = "/project/{id}/backlog/r{r}/view", method = RequestMethod.GET)
	public ModelAndView viewBacklogRevision(@PathVariable Long id,
			@PathVariable String r) {
		
//		ArrayList<Project> projectList = company.getProjects();
//		int index = Integer.parseInt(id);
//		Project project = projectList.get(index);
		
		Project project = projectDao.get(id);
		
		int revision = Integer.parseInt(r);
		ProjectHistoryItem snapshot = project.getProjectSnapshot(revision);
		// WikiToHTMLSerializer serializer = new WikiToHTMLSerializer();
		// String htmlBacklog =
		// serializer.convert(snapshot.getBacklogSnapshot().getWikiMarkupContent());
		ModelAndView model = new ModelAndView("standalone/backlogRevisionPage");
		model.addObject("project", project);
		model.addObject("snapshot", snapshot);
		return model;

	}

	@RequestMapping(value = "/project/{id}/backlog/r{r}/revert", method = RequestMethod.GET)
	public String revertBacklogFromRevision(@PathVariable Long id,
			@PathVariable String r) {
//		ArrayList<Project> projectList = company.getProjects();
//		int index = Integer.parseInt(id);
//		Project project = projectList.get(index);

		Project project = projectDao.get(id);
		
		int revision = Integer.parseInt(r);
		
		corrector.revertBacklog(project, revision);
		
		//return new ModelAndView("standalone/backlogPage", "project", project);
		return "redirect:/project/"+id+"/backlog/view";

	}

}