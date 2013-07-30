package com.tomtom.amelinium.confluence.server.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
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
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiError;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
/**
 * Controller for all the actions concerning backlogs and charts on Confluence.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
@Controller
@RequestMapping(value = "/plot")
@Api(value = "Plot operations", listingClass = "PlotController", basePath = "/plot", description = "All operations for plots")
public class PlotController {
	
	@Autowired
	private ConfluenceConfig confluenceConfig;
	@Autowired
	private PlotPageGenerator plotPageGenerator;
	@Autowired
	private BacklogAndJournalUpdater backlogAndJournalUpdater;
	
    @ApiOperation(value = "Draw plots based on journal",
    		notes = "Draw plots based on journal")
	@RequestMapping(value = "/draw", method = RequestMethod.GET)
	public void drawPlot(
			@ApiParam("Confluence space")
			@RequestParam String space,
			@ApiParam("Confluence page title")
			@RequestParam String title,
			@ApiParam("Length of sprint in days")
			@RequestParam int sprintLength,
			@ApiParam("Team velocity")
			@RequestParam double velocity,
			@ApiParam("Number of story points added to scope per sprint")
			@RequestParam(required=false) Double scopeIncrease,
			@ApiParam("Effective velocity for burndown plot (velocity - scopeIncrease)")
			@RequestParam(required=false) Double effectiveVelocity,
			@ApiParam("Is journal expressed in cumulative form")
			@RequestParam(defaultValue = "false", required=false) boolean isCumulative,
			ModelAndView model) {

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
		
//		ModelAndView model = new ModelAndView("plots/plotBurnupBurndown");
		model.setViewName("plots/plotBurnupBurndown");
		model.addObject("chartName1", plots.chartName1);
		model.addObject("chartBody1", plots.chartBody1);
		model.addObject("chartName2", plots.chartName2);
		model.addObject("chartBody2", plots.chartBody2);
		model.addObject("burnupTable", plots.burnupTable);
		
//		return model;
	}
	
    @ApiOperation(value = "Update the journal based on current Confluence backlog",
    		notes = "Update the journal based on current Confluence backlog")
	@RequestMapping(value = "/updateJournal", method = RequestMethod.GET)
	public void updateJournal(
			@ApiParam("Confluence space of backlog")
			@RequestParam String backlogSpace,
			@ApiParam("Confluence page title of backlog")
			@RequestParam String backlogTitle,
			@ApiParam("Confluence space of journal")
			@RequestParam String journalSpace,
			@ApiParam("Confluence page title of journal")
			@RequestParam String journalTitle,
			@ApiParam("Is journal expressed in cumulative form")
			@RequestParam(value = "isCumulative", defaultValue = "false", required=false) boolean isCumulative,
			@ApiParam("Wheather new groups should be added to journal")
			@RequestParam(defaultValue = "true", required=false) boolean addNewFeatureGroups,
			@ApiParam("Wheather last low in journal should be overwritten has the same date")
			@RequestParam(defaultValue = "true", required=false) boolean overWriteExistingDate,
			HttpServletResponse response) throws IOException {
		
		String backlogContent = ConfluenceOperations.getPageSource(confluenceConfig.SERVER,
				confluenceConfig.USER, confluenceConfig.PASS, backlogSpace, backlogTitle);
		
		String journalContent = ConfluenceOperations.getPageSource(confluenceConfig.SERVER,
				confluenceConfig.USER, confluenceConfig.PASS, journalSpace, journalTitle);
		
		DateTime dateTime = new DateTime().toDateMidnight().toDateTime();
		
		String updatedJournal = backlogAndJournalUpdater.generateUpdatedString(dateTime,
				backlogContent, journalContent,
				isCumulative, addNewFeatureGroups, overWriteExistingDate);
		
		ConfluenceOperations.updatePageSource(confluenceConfig.SERVER,
				confluenceConfig.USER, confluenceConfig.PASS, journalSpace, journalTitle, updatedJournal);
		
//		return "redirect:" + confluenceConfig.SERVER + "/display/" + backlogSpace + "/" + backlogTitle;
//		return "redirect:" + confluenceConfig.SERVER + "/display/" + journalSpace + "/" + journalTitle;

		response.sendRedirect(confluenceConfig.SERVER + "/display/" + journalSpace + "/" + journalTitle);
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
