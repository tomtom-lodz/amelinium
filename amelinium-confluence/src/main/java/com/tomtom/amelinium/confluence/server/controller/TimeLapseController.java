package com.tomtom.amelinium.confluence.server.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tomtom.amelinium.confluence.client.ConfluenceOperations;
import com.tomtom.amelinium.confluence.config.ConfluenceConfig;
import com.tomtom.amelinium.confluence.logic.Plots;
import com.tomtom.woj.amelinium.journal.timelapse.TimeLapseChartGenerator;
import com.tomtom.woj.amelinium.journal.timelapse.TimeLapseChunk;
import com.tomtom.woj.amelinium.journal.timelapse.TimeLapseStringBuilder;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * Controller for all the actions concerning backlogs and charts on Confluence.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
@Controller
@RequestMapping(value = "/timeLapse")
@Api(value = "Drawing time lapse view of predicted release dates", listingClass = "TimeLapseController",
description = "Drawing time lapse view of predicted release dates")
public class TimeLapseController {
	
	@Autowired
	private ConfluenceConfig confluenceConfig;
	
	private TimeLapseStringBuilder timeLapseStringBuilder = new TimeLapseStringBuilder();
	TimeLapseChartGenerator timeLapseChartGenerator = new TimeLapseChartGenerator();
	
    @ApiOperation(value = "Display CSV data of time lapse of predicted dates of releases",
    		notes = "Display CSV data of time lapse of predicted dates of releases",
    		responseClass = "VOID")
	@RequestMapping(value = "/timeLapseString", method = RequestMethod.GET)
	public void timeLapseString(
			@ApiParam("Confluence space of CSV")
			@RequestParam String csvSpace,
			@ApiParam("Confluence page with CSV")
			@RequestParam String csvTitle,
			@ApiParam("Length of sprint in days")
			@RequestParam int sprintLength,
			@ApiParam("Team velocity")
			@RequestParam double velocity,
			@ApiParam("Number of story points added to scope per sprint")
			@RequestParam(required=false) Double scopeIncrease,
			@ApiParam("Is CSV in cumulative form")
			@RequestParam(defaultValue = "false", required=false) boolean isCumulative,
			HttpServletResponse response) throws IOException {

		double dailyVelocity = velocity/sprintLength;
		double dailyBlackMatter;
		if(scopeIncrease==null) {
			dailyBlackMatter = 0;
		} else {
			dailyBlackMatter = scopeIncrease/sprintLength;
		}
    	
		String journalContent = ConfluenceOperations.getPageSource(confluenceConfig.SERVER,
				confluenceConfig.USER, confluenceConfig.PASS, csvSpace, csvTitle);
		
		TimeLapseChunk chunk = timeLapseStringBuilder.createTimeLapseString(journalContent,
				dailyVelocity, dailyBlackMatter, isCumulative);
		
		String output = chunk.toString();

		
		response.setContentType("text/plain; charset=windows-1250");
		PrintWriter out = response.getWriter();
		out.println(output);
	}
    
    
    
    @ApiOperation(value = "Draw plots from CSV file",
    		notes = "Draw plots from CSV file",
    		responseClass = "VOID")
	@RequestMapping(value = "/timeLapsePlot", method = RequestMethod.GET)
	public ModelAndView timeLapsePlot(
			@ApiParam("Confluence space")
			@RequestParam String csvSpace,
			@ApiParam("Confluence page with CSV")
			@RequestParam String csvTitle,
			@ApiParam("Length of sprint in days")
			@RequestParam int sprintLength,
			@ApiParam("Team velocity")
			@RequestParam double velocity,
			@ApiParam("Number of story points added to scope per sprint")
			@RequestParam(required=false) Double scopeIncrease,
			@ApiParam("Is CSV in cumulative form")
			@RequestParam(defaultValue = "false", required=false) boolean isCumulative) {

		double dailyVelocity = velocity/sprintLength;
		double dailyBlackMatter;
		if(scopeIncrease==null) {
			dailyBlackMatter = 0;
		} else {
			dailyBlackMatter = scopeIncrease/sprintLength;
		}
    	
		String journalContent = ConfluenceOperations.getPageSource(confluenceConfig.SERVER,
				confluenceConfig.USER, confluenceConfig.PASS, csvSpace, csvTitle);
		
		TimeLapseChunk chunk = timeLapseStringBuilder.createTimeLapseString(journalContent,
				dailyVelocity, dailyBlackMatter, isCumulative);
		
		String chart1Body = timeLapseChartGenerator.generateTimeLaps(chunk, "chart1", "Time Lapse Release Dates");
		
		ModelAndView model = new ModelAndView("timeLapse/plotTimeLapse");
		model.addObject("chartName1", "chart1");
		model.addObject("chartBody1", chart1Body);
		
		return model;
	}
	
}
