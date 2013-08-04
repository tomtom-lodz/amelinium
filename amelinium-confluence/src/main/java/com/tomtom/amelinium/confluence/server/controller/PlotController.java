package com.tomtom.amelinium.confluence.server.controller;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tomtom.amelinium.confluence.client.ConfluenceOperations;
import com.tomtom.amelinium.confluence.config.ConfluenceConfig;
import com.tomtom.amelinium.confluence.logic.PlotPageGenerator;
import com.tomtom.amelinium.confluence.logic.Plots;
import com.tomtom.woj.amelinium.journal.operations.BacklogAndJournalUpdater;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
/**
 * Controller for all the actions concerning backlogs and charts on Confluence.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
@Controller
@RequestMapping(value = "/plot")
@Api(value = "Plot operations", listingClass = "PlotController", description = "All operations for plots")
public class PlotController {
	
	@Autowired
	private ConfluenceConfig confluenceConfig;
	@Autowired
	private PlotPageGenerator plotPageGenerator;
	@Autowired
	private BacklogAndJournalUpdater backlogAndJournalUpdater;
	
    @ApiOperation(value = "Draw plots from CSV file",
    		notes = "Draw plots from CSV file",
    		responseClass = "VOID")
	@RequestMapping(value = "/draw", method = RequestMethod.GET)
	public ModelAndView drawPlot(
			@ApiParam("Confluence space")
			@RequestParam String space,
			@ApiParam("Confluence page with CSV")
			@RequestParam String title,
			@ApiParam("Length of sprint in days")
			@RequestParam int sprintLength,
			@ApiParam("Team velocity")
			@RequestParam double velocity,
			@ApiParam("Number of story points added to scope per sprint")
			@RequestParam(required=false) Double scopeIncrease,
			@ApiParam("Effective velocity for burndown plot (velocity - scopeIncrease)")
			@RequestParam(required=false) Double effectiveVelocity,
			@ApiParam("Is CSV in cumulative form")
			@RequestParam(defaultValue = "false", required=false) boolean isCumulative) {

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
	
    @ApiOperation(value = "Update CSV page according to backlog",
    		notes = "Update CSV page according to backlog",
    		responseClass = "VOID")
	@RequestMapping(value = "/updateCsv", method = RequestMethod.GET)
	public String updateJournal(
			@ApiParam("Confluence space of backlog")
			@RequestParam String backlogSpace,
			@ApiParam("Confluence page with backlog")
			@RequestParam String backlogTitle,
			@ApiParam("Confluence space of CSV")
			@RequestParam String csvSpace,
			@ApiParam("Confluence page with CSV")
			@RequestParam String csvTitle,
			@ApiParam("Is CSV in cumulative form")
			@RequestParam(value = "isCumulative", defaultValue = "false", required=false) boolean isCumulative,
			@ApiParam("Wheather new groups should be added to CSV")
			@RequestParam(defaultValue = "true", required=false) boolean addNewFeatureGroups,
			@ApiParam("Wheather last low in CSV should be overwritten when it has the same date")
			@RequestParam(defaultValue = "true", required=false) boolean overWriteExistingDate) {
		
		String backlogContent = ConfluenceOperations.getPageSource(confluenceConfig.SERVER,
				confluenceConfig.USER, confluenceConfig.PASS, backlogSpace, backlogTitle);
		
		String journalContent = ConfluenceOperations.getPageSource(confluenceConfig.SERVER,
				confluenceConfig.USER, confluenceConfig.PASS, csvSpace, csvTitle);
		
		DateTime dateTime = new DateTime().toDateMidnight().toDateTime();
		
		String updatedJournal = backlogAndJournalUpdater.generateUpdatedString(dateTime,
				backlogContent, journalContent,
				isCumulative, addNewFeatureGroups, overWriteExistingDate);
		
		ConfluenceOperations.updatePageSource(confluenceConfig.SERVER,
				confluenceConfig.USER, confluenceConfig.PASS, csvSpace, csvTitle, updatedJournal);
		
//		return "redirect:" + confluenceConfig.SERVER + "/display/" + backlogSpace + "/" + backlogTitle;
		return "redirect:" + confluenceConfig.SERVER + "/display/" + csvSpace + "/" + csvTitle;
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
