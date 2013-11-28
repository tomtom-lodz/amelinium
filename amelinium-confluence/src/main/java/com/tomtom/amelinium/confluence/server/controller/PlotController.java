package com.tomtom.amelinium.confluence.server.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tomtom.amelinium.confluence.client.ConfluenceOperations;
import com.tomtom.amelinium.confluence.config.ConfluenceConfig;
import com.tomtom.amelinium.confluence.logic.BacklogPageCorrector;
import com.tomtom.amelinium.confluence.logic.PlotPageGenerator;
import com.tomtom.amelinium.confluence.logic.Plots;
import com.tomtom.woj.amelinium.journal.converter.CumulativeToAbsoluteConverter;
import com.tomtom.woj.amelinium.journal.io.BacklogJournalReader;
import com.tomtom.woj.amelinium.journal.io.BacklogJournalSerializer;
import com.tomtom.woj.amelinium.journal.model.BacklogChunk;
import com.tomtom.woj.amelinium.journal.updating.BacklogAndJournalUpdater;
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
	@Autowired
	private BacklogPageCorrector backlogPageCorrector;
	
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
			@RequestParam(defaultValue = "0", required=false) double scopeIncrease,
			@ApiParam("Effective velocity for burndown plot (velocity - scopeIncrease)")
			@RequestParam(required=false) Double effectiveVelocity,
			@ApiParam("Is CSV in cumulative form")
			@RequestParam(defaultValue = "false", required=false) boolean isCumulative,
			@ApiParam("Should burnup chart be rendered")
			@RequestParam(defaultValue = "true", required=false) boolean renderBurnup,
			@ApiParam("Should table with releases be rendered")
			@RequestParam(defaultValue = "true", required=false) boolean renderTable,
			@ApiParam("Should burndown chart be rendered")
			@RequestParam(defaultValue = "false", required=false) boolean renderBurndown) {

		double dailyVelocity = velocity/sprintLength;
		double dailyBlackMatter = scopeIncrease/sprintLength;

		double dailyEffectiveVelocity;
		if(effectiveVelocity==null) {
			dailyEffectiveVelocity = dailyVelocity - dailyBlackMatter;
		} else {
			dailyEffectiveVelocity = effectiveVelocity/sprintLength;
		}
		
		Plots plots = plotPageGenerator.generatePlotsFromConfluencePage(space, title, isCumulative,
				dailyVelocity, dailyBlackMatter, dailyEffectiveVelocity);
		
		ModelAndView model = new ModelAndView("plots/plotBurnupBurndown");
		model.addObject("sprintLength", sprintLength);
		model.addObject("velocity", velocity);
		model.addObject("scopeIncrease", scopeIncrease);
		model.addObject("chartName1", plots.chartName1);
		model.addObject("chartBody1", plots.chartBody1);
		model.addObject("chartName2", plots.chartName2);
		model.addObject("chartBody2", plots.chartBody2);
		model.addObject("burnupTable", plots.burnupTable);
		model.addObject("renderBurnup", renderBurnup);
		model.addObject("renderTable", renderTable);
		model.addObject("renderBurndown", renderBurndown);
		
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
			@ApiParam("Wheather last row in CSV should be overwritten when it has the same date")
			@RequestParam(defaultValue = "true", required=false) boolean overwriteExistingDate,
			@ApiParam("Force specific date of update")
			@RequestParam(required=false) String forceDate) {
		
		String backlogContent = ConfluenceOperations.getPageSource(confluenceConfig.SERVER,
				confluenceConfig.USER, confluenceConfig.PASS, backlogSpace, backlogTitle);
		
		String journalContent = ConfluenceOperations.getPageSource(confluenceConfig.SERVER,
				confluenceConfig.USER, confluenceConfig.PASS, csvSpace, csvTitle);
		
		DateTime dateTime;
		if(forceDate == null) {
			dateTime = new DateTime().toDateMidnight().toDateTime();
		} else {
			dateTime = new DateTime(forceDate);
		}
		
		boolean allowingMultilineFeatures = true;
		
		String updatedJournal = backlogAndJournalUpdater.generateUpdatedString(dateTime,
				backlogContent, journalContent,
				isCumulative, addNewFeatureGroups, overwriteExistingDate, allowingMultilineFeatures);
		
		ConfluenceOperations.updatePageSource(confluenceConfig.SERVER,
				confluenceConfig.USER, confluenceConfig.PASS, csvSpace, csvTitle, updatedJournal);
		
//		return "redirect:" + confluenceConfig.SERVER + "/display/" + backlogSpace + "/" + backlogTitle;
		return "redirect:" + confluenceConfig.SERVER + "/display/" + csvSpace + "/" + csvTitle;
	}

    @ApiOperation(value = "Create new CSV page according to backlog",
    		notes = "Create new CSV page according to backlog",
    		responseClass = "VOID")
	@RequestMapping(value = "/createCsv", method = RequestMethod.GET)
	public String createCsv(
			@ApiParam("Confluence space of backlog")
			@RequestParam String backlogSpace,
			@ApiParam("Confluence page with backlog")
			@RequestParam String backlogTitle,
			@ApiParam("Confluence space of new CSV")
			@RequestParam String csvSpace,
			@ApiParam("Name of Parent Confluence page with new CSV")
			@RequestParam String csvParentTitle,
			@ApiParam("Name of Confluence page with CSV")
			@RequestParam String csvTitle,
			@ApiParam("Is CSV in cumulative form")
			@RequestParam(value = "isCumulative", defaultValue = "false", required=false) boolean isCumulative,
			@ApiParam("Force specific date of update")
			@RequestParam(required=false) String forceDate) {
		
		String backlogContent = ConfluenceOperations.getPageSource(confluenceConfig.SERVER,
				confluenceConfig.USER, confluenceConfig.PASS, backlogSpace, backlogTitle);
		
		DateTime dateTime;
		if(forceDate==null) {
			dateTime = new DateTime().toDateMidnight().toDateTime();
		} else {
			dateTime = new DateTime(forceDate);
		}
		
		String newJournal = backlogAndJournalUpdater.create(dateTime,
				backlogContent, isCumulative);
		
		ConfluenceOperations.addPage(confluenceConfig.SERVER,
				confluenceConfig.USER, confluenceConfig.PASS, csvSpace, csvParentTitle, csvTitle, newJournal);
		
		return "redirect:" + confluenceConfig.SERVER + "/display/" + csvSpace + "/" + csvTitle;
	}


    @ApiOperation(value = "Update backlog and CSV page",
    		notes = "Update backlog and CSV page",
    		responseClass = "VOID")
	@RequestMapping(value = "/updateBacklogAndCsv", method = RequestMethod.GET)
	public String updateBacklogAndCsv(
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
			@ApiParam("Wheather last row in CSV should be overwritten when it has the same date")
			@RequestParam(defaultValue = "true", required=false) boolean overwriteExistingDate,
			@ApiParam("Force specific date of update")
			@RequestParam(required=false) String forceDate,
			@ApiParam("If features can be spanned through multiple lines")
			@RequestParam(defaultValue = "false", required=false) boolean allowMultilineFeatures) {
		
		String backlogContent = ConfluenceOperations.getPageSource(confluenceConfig.SERVER,
				confluenceConfig.USER, confluenceConfig.PASS, backlogSpace, backlogTitle);
		
		String journalContent = ConfluenceOperations.getPageSource(confluenceConfig.SERVER,
				confluenceConfig.USER, confluenceConfig.PASS, csvSpace, csvTitle);
		
		String updatedBacklogContent = backlogPageCorrector.generateNewBacklogContent(backlogContent, allowMultilineFeatures);		
		
		DateTime dateTime;
		if(forceDate==null) {
			dateTime = new DateTime().toDateMidnight().toDateTime();
		} else {
			dateTime = new DateTime(forceDate);
		}
		
		String updatedJournal = backlogAndJournalUpdater.generateUpdatedString(dateTime,
				updatedBacklogContent, journalContent,
				isCumulative, addNewFeatureGroups, overwriteExistingDate, allowMultilineFeatures);

		ConfluenceOperations.updatePageSource(confluenceConfig.SERVER,
				confluenceConfig.USER, confluenceConfig.PASS, backlogSpace, backlogTitle, updatedBacklogContent);
		
		ConfluenceOperations.updatePageSource(confluenceConfig.SERVER,
				confluenceConfig.USER, confluenceConfig.PASS, csvSpace, csvTitle, updatedJournal);
		
		return "redirect:" + confluenceConfig.SERVER + "/display/" + backlogSpace + "/" + backlogTitle;
	}

}
