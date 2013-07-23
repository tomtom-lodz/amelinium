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
import com.tomtom.amelinium.web.standalone.model.TextAreaFormChart;

/**
 * Controller for all the actions concerning charts.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
@Controller
public class ChartController {

	@Autowired
	private Company company;
	
	@Autowired
	private ProjectDao projectDao;
	
	private ProjectCorrector corrector = new ProjectCorrector();

	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * Displays chart page. Displayed chart corresponds to the project specified
	 * by the path variable.
	 * 
	 * @param id
	 *            path variable of a project which chart is to be displayed
	 */
	@RequestMapping("/project/{id}/chart/view")
	public ModelAndView viewChartHandler(@PathVariable Long id) {
		
		Project project = projectDao.get(id);
		
//		ArrayList<Project> projectList = company.getProjects();
//		int index = Integer.parseInt(id);
//		Project project = projectList.get(index);
		
		ModelAndView model = new ModelAndView("standalone/chartPage", "project", project);
		if (project.isUpToDate() == false) {
			model.addObject("message", Messages.chartMessage);
		} else {
			model.addObject("message", "");
		}
		return model;

	}

	/**
	 * Displays edit box for a chart. The edit box contains serialized version
	 * of the chart model (wiki markup). The content corresponds to the project
	 * specified by the path variable.
	 * 
	 * @param id
	 *            path variable of a project which chart is to be edited
	 */
	@RequestMapping("/project/{id}/chart/edit")
	public ModelAndView editChartHandler(@PathVariable Long id) {
//		ArrayList<Project> projectList = company.getProjects();
//		int index = Integer.parseInt(id);
//		Project project = projectList.get(index);
		
		Project project = projectDao.get(id);
		
		ModelAndView model = new ModelAndView("standalone/chartEditPage");
		model.addObject("project", project);
		model.addObject("command", new TextAreaFormBacklog(project.getChartWikiMarkup()));
		if (project.isUpToDate() == false) {
			model.addObject("message", Messages.editChartMessage);
		} else {
			model.addObject("message", "");
		}
		return model;

	}

	/**
	 * Corrects chart and displays chart page with updated chart. The chart
	 * corresponds to the project specified by the path variable.
	 * 
	 * @param id
	 *            path variable of a project which chart is to be updated
	 */
	@RequestMapping(value = "/project/{id}/chart/view", params = "_updateChart", method = RequestMethod.POST)
	public ModelAndView updateChartHandler(@PathVariable Long id,
			@Valid TextAreaFormChart form, BindingResult result) {
//		ArrayList<Project> projectList = company.getProjects();
//		int index = Integer.parseInt(id);
//		Project project = projectList.get(index);
		
		Project project = projectDao.get(id);
		
		String wikiMarkupChart = form.getText();
		corrector.updateProjectWithNewChart(project, wikiMarkupChart);
		return new ModelAndView("standalone/chartPage", "project", project);

	}
	
	@RequestMapping(value = "/project/{id}/chart/history/{part}", method = RequestMethod.GET)
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

		ModelAndView model = new ModelAndView("standalone/chartHistoryPage");
		model.addObject("projectId", project.getId());
		model.addObject("history", history);
		model.addObject("translationNewer", translationNewer);
		model.addObject("translationOlder", translationOlder);

		return model;
	}
	
	@RequestMapping(value = "/project/{id}/chart/r{r}/view", method = RequestMethod.GET)
	public ModelAndView viewChartRevision(@PathVariable Long id, @PathVariable String r) {
		
//		ArrayList<Project> projectList = company.getProjects();
//		int index = Integer.parseInt(id);
//		Project project = projectList.get(index);

		Project project = projectDao.get(id);
		
		int revision = Integer.parseInt(r);
		ProjectHistoryItem snapshot = project.getProjectSnapshot(revision);
		//WikiToHTMLSerializer serializer = new WikiToHTMLSerializer();
		//String htmlChart = serializer.convert(snapshot.getChartSnapshot().getWikiMarkupContent());
		ModelAndView model = new ModelAndView("standalone/chartRevisionPage");
		model.addObject("project", project);
		model.addObject("snapshot", snapshot);
		return model;

	}
	
	@RequestMapping(value = "/project/{id}/chart/r{r}/revert", method = RequestMethod.GET)
	public String revertChartFromRevision(@PathVariable Long id,
			@PathVariable String r) {
//		ArrayList<Project> projectList = company.getProjects();
//		int index = Integer.parseInt(id);
//		Project project = projectList.get(index);
		int revision = Integer.parseInt(r);
		
		Project project = projectDao.get(id);
		
		
		corrector.revertChart(project, revision);
		
		//return new ModelAndView("standalone/backlogPage", "project", project);
		return "redirect:/project/"+id+"/chart/view";

	}
}
