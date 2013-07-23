package com.tomtom.amelinium.web.standalone.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tomtom.amelinium.db.beans.Chart;
import com.tomtom.amelinium.db.config.SyncStatus;
import com.tomtom.amelinium.db.logic.ChartDbLogic;
import com.tomtom.amelinium.db.logic.ProjectDbLogic;
import com.tomtom.amelinium.db.results.HistoryElement;
import com.tomtom.amelinium.db.results.ResultProject;
import com.tomtom.amelinium.projectservice.config.Messages;
import com.tomtom.amelinium.projectservice.corrector.ProjectCorrector;
import com.tomtom.amelinium.projectservice.visualisation.JSPChart;
import com.tomtom.amelinium.projectservice.visualisation.JSPChartSerializer;
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
	ChartDbLogic chartDbLogic;
	@Autowired
	private ProjectDbLogic projectDbLogic;
	@Autowired
	private ProjectCorrector corrector;
	@Autowired
	private HistoryHelper historyHelper;

	/**
	 * Displays chart page. Displayed chart corresponds to the current version
	 * of the project specified by the path variable.
	 * 
	 * @param id
	 *            path variable of a project which chart is to be displayed
	 */
	@RequestMapping("/project/{id}/chart/view")
	public ModelAndView viewChartHandler(@PathVariable int id) {
		JSPChartSerializer jspChartSerializer = new JSPChartSerializer();
		ResultProject project = projectDbLogic.getCurrentRevision(id);
		ModelAndView model = new ModelAndView("standalone/chartPage");
		JSPChart jspChart = jspChartSerializer.serializeWikiMarkupContents(
				project.getWikiMarkupBacklog(), project.getWikiMarkupChart());
		if (project.getSync().equals(SyncStatus.BACKLOG_EDITED)) {
			model.addObject("message", Messages.BACKLOG_UPDATED_MSG);
		} else if (project.getSync().equals(SyncStatus.BACKLOG_REVERTED)) {
			model.addObject("message", Messages.BACKLOG_REVERTED_MSG);
		} else {
			model.addObject("message", "");
		}
		model.addObject("jspChart", jspChart);
		model.addObject("projectId", id);
		model.addObject("projectName", project.getName());
		return model;

	}

	/**
	 * Displays edit box for a chart. The edit box contains serialized version
	 * of the chart model (wiki markup). The content corresponds to the current
	 * version of the the project specified by the path variable.
	 * 
	 * @param id
	 *            path variable of a project which chart is to be edited
	 */
	@RequestMapping("/project/{id}/chart/edit")
	public ModelAndView editChartHandler(@PathVariable int id) {
		ResultProject project = projectDbLogic.getCurrentRevision(id);
		ModelAndView model = new ModelAndView("standalone/chartEditPage");
		model.addObject("projectName", project.getName());
		model.addObject("projectId", id);
		model.addObject("command",
				new TextAreaFormBacklog(project.getWikiMarkupChart()));
		if (project.getSync().equals(SyncStatus.BACKLOG_EDITED)||project.getSync().equals(SyncStatus.BACKLOG_REVERTED)) {
			model.addObject("message", Messages.EDIT_CHART_WARNING);
		} else {
			model.addObject("message", "");
		}
		return model;

	}

	/**
	 * Adds new revision of a project to the database. Corrects chart and
	 * displays chart page with updated chart. New revision consists of a
	 * corrected chart provided in a text form and current backlog from the
	 * database.
	 * 
	 * @param id
	 *            path variable of a project which chart is to be updated
	 */
	@RequestMapping(value = "/project/{id}/chart/view", params = "_updateChart", method = RequestMethod.POST)
	public ModelAndView updateChartHandler(@PathVariable int id,
			@Valid TextAreaFormChart form, BindingResult result) {
		JSPChartSerializer jspChartSerializer = new JSPChartSerializer();
		ResultProject project = projectDbLogic.getCurrentRevision(id);
		String wikiMarkupBacklog = project.getWikiMarkupBacklog();
		String newWikiMarkupChart = form.getText();
		corrector.updateProjectWithNewChart(id, wikiMarkupBacklog,
				newWikiMarkupChart);
		ModelAndView model = new ModelAndView("standalone/chartPage");
		JSPChart jspChart = jspChartSerializer.serializeWikiMarkupContents(
				wikiMarkupBacklog, newWikiMarkupChart);

		model.addObject("message", "");//no warning message, because project is now up-to-date
		model.addObject("jspChart", jspChart);
		model.addObject("projectId", id);
		model.addObject("projectName", project.getName());

		return model;

	}

	/**
	 * Displays part of the chart history revisions specified as the path
	 * variables.
	 * 
	 * @param id
	 *            path variable of a project which chart history is to be
	 *            displayed
	 * @param part
	 *            part of the chart history which is to be displayed
	 */
	@RequestMapping(value = "/project/{id}/chart/history/{part}", method = RequestMethod.GET)
	public ModelAndView showHistory(@PathVariable int id, @PathVariable int part) {
		ResultProject project = projectDbLogic.getCurrentRevision(id);

		List<HistoryElement> history = historyHelper.getPartOfChartHistory(id, part);

		int translationNewer = historyHelper.calculateTranslationNewer(part);
		int translationOlder = historyHelper.calculateTranslationOlder(part, id);

		ModelAndView model = new ModelAndView("standalone/chartHistoryPage");
		model.addObject("projectId", id);
		model.addObject("projectName", project.getName());
		model.addObject("history", history);
		model.addObject("translationNewer", translationNewer);
		model.addObject("translationOlder", translationOlder);

		return model;
	}

	/**
	 * Displays revision of the chart specified as a path parameter.
	 * 
	 * @param id
	 *            path variable of a project which chart revision is to be
	 *            displayed
	 * @param part
	 *            revision of the chart which is to be displayed
	 */
	@RequestMapping(value = "/project/{id}/chart/r{revision}/view", method = RequestMethod.GET)
	public ModelAndView viewChartRev(@PathVariable int id,
			@PathVariable int revision) {
		ResultProject project = projectDbLogic.getCurrentRevision(id);
		Chart chart = chartDbLogic.getChart(id, revision);
		ModelAndView model = new ModelAndView("standalone/chartRevisionPage");
		model.addObject("projectName", project.getName());
		model.addObject("projectId", id);
		model.addObject("chart", chart);
		return model;

	}

	/**
	 * Reverts chart to a specified revision. Redirects to the page displaying
	 * current (reverted) version of the chart.
	 * 
	 * @param id
	 *            path variable of a project which chart revision is to be
	 *            displayed
	 * @param part
	 *            revision of the chart which is to be displayed
	 */
	@RequestMapping(value = "/project/{id}/chart/r{r}/revert", method = RequestMethod.GET)
	public String revertChartFromRevision(@PathVariable int id,
			@PathVariable int r) {
		corrector.revertChart(id, r);
		return "redirect:/web/project/" + id + "/chart/view";

	}
}
