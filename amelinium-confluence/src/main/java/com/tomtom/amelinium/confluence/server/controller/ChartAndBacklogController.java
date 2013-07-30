package com.tomtom.amelinium.confluence.server.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tomtom.amelinium.confluence.config.ConfluenceConfig;
import com.tomtom.amelinium.confluence.logic.BacklogPageCorrector;
import com.tomtom.amelinium.confluence.logic.ChartPageCorrector;
import com.tomtom.amelinium.confluence.server.model.ChartForm;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
/**
 * Controller for all the actions concerning backlogs and charts on Confluence.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
@Controller
@RequestMapping(value = "/chart-and-backlog")
@Api(value = "Confluence chart and backlog operations", listingClass = "ChartAndBacklogController", description = "All operations for Confluence chart and backlog")
public class ChartAndBacklogController {
	
	@Autowired
	private ConfluenceConfig confluenceConfig;
	@Autowired
	private BacklogPageCorrector backlogPageCorrector;
	@Autowired
	private ChartPageCorrector chartPageCorrector;

    //@ApiExclude
    @ApiOperation(value = "Display update backlog and chart form",
    		notes = "Display update backlog and chart form",
    		responseClass = "VOID")
	@RequestMapping("/")
	public ModelAndView chartFormWelcomeHandler() {
		return new ModelAndView("confluence/chartFormSpring", "command", new ChartForm());
	}

    //@ApiExclude
    @ApiOperation(value = "Recalculate and update Confluence backlog and chart page",
    		notes = "Recalculate and update Confluence backlog and chart page",
    		responseClass = "VOID")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView chartFormHandlerPost(@Valid ChartForm form, BindingResult result) {
		if (!result.hasErrors()) {
			chartPageCorrector.correctChart(form.getChartTitle(), form.getChartSpace(), form.getBacklogTitle(), form.getBacklogSpace(), form.getAllowMultilineFeatures());
			return new ModelAndView("confluence/chartFormSpring", "command", new ChartForm());
		} else {
			return new ModelAndView("confluence/chartFormSpring", "command", form);
		}
	}

    @ApiOperation(value = "Recalculate and update Confluence backlog and chart page",
    		notes = "Recalculate and update Confluence backlog and chart page",
    		responseClass = "VOID")
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String chartFormHandlerGet(
			@ApiParam("Confluence space of backlog")
			@RequestParam String backlogSpace,
			@ApiParam("Confluence page title of backlog")
			@RequestParam String backlogTitle,
			@ApiParam("Confluence space of chart")
			@RequestParam String chartSpace,
			@ApiParam("Confluence page title of chart")
			@RequestParam String chartTitle,
			@ApiParam("If features can be spanned through multiple lines")
			@RequestParam(defaultValue = "false", required=false) boolean allowMultilineFeatures) {
		chartPageCorrector.correctChart(chartTitle, chartSpace, backlogTitle, backlogSpace, allowMultilineFeatures);
		return "redirect:" + confluenceConfig.SERVER + "/display/" + chartSpace + "/" + chartTitle;
	}
}
