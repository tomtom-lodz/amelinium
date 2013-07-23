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

import com.tomtom.amelinium.backlogservice.serializer.WikiToHTMLSerializer;
import com.tomtom.amelinium.db.beans.Backlog;
import com.tomtom.amelinium.db.config.SyncStatus;
import com.tomtom.amelinium.db.logic.BacklogDbLogic;
import com.tomtom.amelinium.db.logic.ProjectDbLogic;
import com.tomtom.amelinium.db.results.HistoryElement;
import com.tomtom.amelinium.db.results.ResultProject;
import com.tomtom.amelinium.projectservice.config.Messages;
import com.tomtom.amelinium.projectservice.corrector.ProjectCorrector;
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
	private BacklogDbLogic backlogDbLogic;
	@Autowired
	private ProjectDbLogic projectDbLogic;
	@Autowired
	private ProjectCorrector corrector;
	@Autowired
	private HistoryHelper historyHelper;
	
	private WikiToHTMLSerializer wikiToHTMLSerializer = new WikiToHTMLSerializer();

	/**
	 * Displays backlog page. Displayed backlog corresponds to current version
	 * of the project specified by the path variable.
	 * 
	 * @param id
	 *            path variable of a project which backlog is to be displayed
	 */
	@RequestMapping(value = "/project/{id}/backlog/view", method = RequestMethod.GET)
	public ModelAndView mainLayoutHandler(@PathVariable int id) {
		ResultProject project = projectDbLogic.getCurrentRevision(id);
		String htmlBacklog = wikiToHTMLSerializer.convert(project
				.getWikiMarkupBacklog());
		ModelAndView model = new ModelAndView("standalone/backlogPage");
		if (project.getSync().equals(SyncStatus.CHART_REVERTED)) {
			model.addObject("message", Messages.CHART_REVERTED_MSG);
		} else {
			model.addObject("message", "");
		}
		model.addObject("htmlBacklog", htmlBacklog);
		model.addObject("projectId", id);
		model.addObject("projectName", project.getName());
		return model;

	}

	/**
	 * Displays edit box for a backlog. The edit box contains serialized version
	 * of the backlog model (wiki markup). The content corresponds to the
	 * current version of the project specified by the path variable.
	 * 
	 * @param id
	 *            path variable of a project which backlog is to be edited
	 */
	@RequestMapping(value = "/project/{id}/backlog/edit", method = RequestMethod.GET)
	public ModelAndView editBacklogHandler(@PathVariable int id) {
		ResultProject project = projectDbLogic.getCurrentRevision(id);
		ModelAndView model = new ModelAndView("standalone/backlogEditPage");
		model.addObject("projectName", project.getName());
		model.addObject("projectId", id);
		model.addObject("command",
				new TextAreaFormBacklog(project.getWikiMarkupBacklog()));
		model.addObject("message", Messages.EDIT_BACKLOG_WARNING);
		return model;
	}

	/**
	 * Adds new revision of a project to the database. Redirects to the backlog
	 * page. Work item points in a backlog are updated. New revision consists of
	 * a corrected backlog provided in a text form and current chart from the
	 * database. Note that this action doesn't update the chart! In order for
	 * the backlog and chart to stay synchronized one should update sprint
	 * number and invoke recalculation/redraw of a chart.
	 * 
	 * @param id
	 *            path variable of a project which backlog is to be updated
	 */
	@RequestMapping(value = "/project/{id}/backlog/view", params = "_updateBacklog", method = RequestMethod.POST)
	public ModelAndView updateBacklogHandler(@PathVariable int id,
			@Valid TextAreaFormBacklog form, BindingResult result) {
		String wikiMarkupBacklog = form.getText();
		boolean allowingMultilineFeatures = form.getAllowMultilineFeatures();
		corrector.updateProjectWithNewBacklog(id, wikiMarkupBacklog,
				allowingMultilineFeatures);
		ResultProject project = projectDbLogic.getCurrentRevision(id);
		String htmlBacklog = wikiToHTMLSerializer.convert(project
				.getWikiMarkupBacklog());
		ModelAndView model = new ModelAndView("standalone/backlogPage");
		model.addObject("htmlBacklog", htmlBacklog);
		model.addObject("projectId", id);
		model.addObject("projectName", project.getName());
		return model;

	}

	/**
	 * Displays part of the backlog history revisions specified as the path
	 * variables.
	 * 
	 * @param id
	 *            path variable of a project which backlog history is to be
	 *            displayed
	 * @param part
	 *            part of the backlog history which is to be displayed
	 */
	@RequestMapping(value = "/project/{id}/backlog/history/{part}", method = RequestMethod.GET)
	public ModelAndView showHistory(@PathVariable int id, @PathVariable int part) {
		ResultProject project = projectDbLogic.getCurrentRevision(id);

		List<HistoryElement> history = historyHelper.getPartOfBacklogHistory(id, part);

		int translationNewer = historyHelper.calculateTranslationNewer(part);
		int translationOlder = historyHelper.calculateTranslationOlder(part, id);

		ModelAndView model = new ModelAndView("standalone/backlogHistoryPage");
		model.addObject("projectId", id);
		model.addObject("projectName", project.getName());
		model.addObject("history", history);
		model.addObject("translationNewer", translationNewer);
		model.addObject("translationOlder", translationOlder);

		return model;
	}

	/**
	 * Displays revision of the backlog specified as a path parameter.
	 * 
	 * @param id
	 *            path variable of a project which backlog revision is to be
	 *            displayed
	 * @param part
	 *            revision of the backlog which is to be displayed
	 */
	@RequestMapping(value = "/project/{id}/backlog/r{revision}/view", method = RequestMethod.GET)
	public ModelAndView viewBacklogRev(@PathVariable int id,
			@PathVariable int revision) {
		ResultProject project = projectDbLogic.getCurrentRevision(id);
		Backlog backlog = backlogDbLogic.getBacklog(id, revision);
		ModelAndView model = new ModelAndView("standalone/backlogRevisionPage");
		model.addObject("projectName", project.getName());
		model.addObject("projectId", id);
		model.addObject("backlog", backlog);
		return model;

	}

	/**
	 * Reverts backlog to a specified revision. Redirects to the page displaying
	 * current (reverted) version of the backlog.
	 * 
	 * @param id
	 *            path variable of a project which backlog revision is to be
	 *            displayed
	 * @param part
	 *            revision of the backlog which is to be displayed
	 */
	@RequestMapping(value = "/project/{id}/backlog/r{revision}/revert", method = RequestMethod.GET)
	public String revertBacklogFromRevision(@PathVariable int id,
			@PathVariable int revision) {
		corrector.revertBacklog(id, revision);
		return "redirect:/web/project/" + id + "/backlog/view";

	}
}