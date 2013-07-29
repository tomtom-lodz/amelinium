package com.tomtom.amelinium.confluence.server.controller;

import javax.validation.Valid;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tomtom.amelinium.confluence.client.ConfluenceOperations;
import com.tomtom.amelinium.confluence.config.ConfluenceConfig;
import com.tomtom.amelinium.confluence.logic.BacklogPageCorrector;
import com.tomtom.amelinium.confluence.logic.ChartPageCorrector;
import com.tomtom.amelinium.confluence.logic.PlotPageGenerator;
import com.tomtom.amelinium.confluence.logic.Plots;
import com.tomtom.amelinium.confluence.server.model.BacklogForm;
import com.tomtom.amelinium.confluence.server.model.ChartForm;
import com.tomtom.woj.amelinium.journal.operations.BacklogAndJournalUpdater;
/**
 * Controller for all the actions concerning backlogs and charts on Confluence.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
@Controller
public class MainController {
	
	private ConfluenceConfig confluenceConfig = new ConfluenceConfig();

	@Autowired
	private BacklogPageCorrector backlogPageCorrector;
	@Autowired
	private ChartPageCorrector chartPageCorrector;
	@Autowired
	private PlotPageGenerator plotPageGenerator;

	@RequestMapping("/backlog/")
	public ModelAndView updateFormWelcomeHandler() {
		return new ModelAndView("confluence/updateFormSpring", "command", new BacklogForm());
	}

	@RequestMapping("/chart-and-backlog/")
	public ModelAndView chartFormWelcomeHandler() {
		return new ModelAndView("confluence/chartFormSpring", "command", new ChartForm());
	}

	@RequestMapping(value = "/backlog/update", method = RequestMethod.GET)
	public String formHandler(@RequestParam String space, @RequestParam String title, @RequestParam(value = "allowMultilineFeatures", defaultValue = "false", required=false) boolean allowMultilineFeatures) {
		backlogPageCorrector.correctBacklog(title, space, allowMultilineFeatures);
		return "redirect:" + confluenceConfig.SERVER + "/display/" + space + "/" + title;
	}

	@RequestMapping(value = "/backlog/update", method = RequestMethod.POST)
	public ModelAndView add(@Valid BacklogForm form, BindingResult result) {
		if (!result.hasErrors()) {
			backlogPageCorrector.correctBacklog(form.getTitle(), form.getSpace(), form.getAllowMultilineFeatures());
			return new ModelAndView("confluence/updateFormSpring", "command", new BacklogForm());
		} else {
			return new ModelAndView("confluence/updateFormSpring", "command", form);
		}

	}

	@RequestMapping(value = "/chart-and-backlog/update", method = RequestMethod.GET)
	public String chartFormHandlerGet(@RequestParam String chartTitle, @RequestParam String chartSpace, @RequestParam String backlogTitle, @RequestParam String backlogSpace,
			@RequestParam(value = "allowMultilineFeatures", defaultValue = "false", required=false) boolean allowMultilineFeatures) {
		chartPageCorrector.correctChart(chartTitle, chartSpace, backlogTitle, backlogSpace, allowMultilineFeatures);
		return "redirect:" + confluenceConfig.SERVER + "/display/" + chartSpace + "/" + chartTitle;
	}

	@RequestMapping(value = "/chart-and-backlog/update", method = RequestMethod.POST)
	public ModelAndView chartFormHandlerPost(@Valid ChartForm form, BindingResult result) {
		if (!result.hasErrors()) {
			chartPageCorrector.correctChart(form.getChartTitle(), form.getChartSpace(), form.getBacklogTitle(), form.getBacklogSpace(), form.getAllowMultilineFeatures());
			return new ModelAndView("confluence/chartFormSpring", "command", new ChartForm());
		} else {
			return new ModelAndView("confluence/chartFormSpring", "command", form);
		}

	}

	@RequestMapping(value = "/plot/draw", method = RequestMethod.GET)
	public ModelAndView drawPlot(@RequestParam String space, @RequestParam String title,
			@RequestParam(value = "isCumulative", defaultValue = "false", required=false) boolean isCumulative,
			@RequestParam int sprintLength,
			@RequestParam double velocity,
			@RequestParam(required=false) Double scopeIncrease,
			@RequestParam(required=false) Double effectiveVelocity) {

		double dailyVelocity = velocity/sprintLength;
		double dailyBlackMatter;
		if(scopeIncrease==null) {
			dailyBlackMatter = 0;
		} else {
			dailyBlackMatter = scopeIncrease/sprintLength;
		}
		double dailyEffectiveVelocity;
		if(effectiveVelocity==null) {
			dailyEffectiveVelocity = dailyVelocity - dailyBlackMatter;
		} else {
			dailyEffectiveVelocity = effectiveVelocity/sprintLength;
		}
		
		Plots plots = plotPageGenerator.generatePlotsFromConfluencePage(space, title, isCumulative,
				dailyVelocity, dailyBlackMatter, dailyEffectiveVelocity);
		
		ModelAndView model = new ModelAndView("plots/plotBurnupBurndown");
		model.addObject("chartName1", plots.chartName1);
		model.addObject("chartBody1", plots.chartBody1);
		model.addObject("chartName2", plots.chartName2);
		model.addObject("chartBody2", plots.chartBody2);
		model.addObject("burnupTable", plots.burnupTable);
		
		return model;
	}
	
	private ConfluenceConfig config = new ConfluenceConfig();
	private BacklogAndJournalUpdater updater = new BacklogAndJournalUpdater();
	
	@RequestMapping(value = "/backlog/updateJournal", method = RequestMethod.GET)
	public String updateJournal(@RequestParam String backlogSpace, @RequestParam String backlogTitle,
			@RequestParam String journalSpace, @RequestParam String journalTitle,
			@RequestParam(value = "isCumulative", defaultValue = "false", required=false) boolean isCumulative) {
//		backlogPageCorrector.correctBacklog(backlogTitle, backlogSpace, false);
		
		String backlogContent = ConfluenceOperations.getPageSource(config.SERVER,
				config.USER, config.PASS, backlogSpace, backlogTitle);
		
		String journalContent = ConfluenceOperations.getPageSource(config.SERVER,
				config.USER, config.PASS, journalSpace, journalTitle);
		
		DateTime dateTime = new DateTime();
		
		String updatedJournal = updater.generateUpdatedString(dateTime, backlogContent, journalContent, isCumulative);
		
		ConfluenceOperations.updatePageSource(config.SERVER,
				config.USER, config.PASS, journalSpace, journalTitle, updatedJournal);
		
//		return "redirect:" + confluenceConfig.SERVER + "/display/" + backlogSpace + "/" + backlogTitle;
		return "redirect:" + confluenceConfig.SERVER + "/display/" + journalSpace + "/" + journalTitle;
	}

	public void addNewReleasesToJournal() {
		
	}
	
//	public void drawPlot(HttpServletResponse response) throws IOException {
//		response.setContentType("text/html");
//		PrintWriter out = response.getWriter();
//		
//		String csv = "Date, Burned,group 1, group 2\n2013-07-20, 0, 13, 13\n";
//		
//		String html = PlotHtmlPageGenerator.createHtmlPageWithPlots(csv,false,1,0,1);
//		
//		out.println(html);
//	}
	
}
