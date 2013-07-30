package com.tomtom.amelinium.confluence.server.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
/**
 * Controller for all the actions concerning backlogs and charts on Confluence.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
@Controller
@RequestMapping(value = "/backlog")
@Api(value = "Backlog operations", listingClass = "BacklogController", basePath = "/backlog", description = "All operations for backlog")
public class BacklogController {
	
	@Autowired
	private ConfluenceConfig confluenceConfig;
	@Autowired
	private BacklogPageCorrector backlogPageCorrector;

    @ApiOperation(value = "Display update backlog form",
    		notes = "Display update backlog form")
	@RequestMapping("/")
	public void updateFormWelcomeHandler(ModelAndView model) {
		model.setViewName("confluence/updateFormSpring");
		model.addObject("command", new BacklogForm());
//		return new ModelAndView("confluence/updateFormSpring", "command", new BacklogForm());
	}

    @ApiOperation(value = "Recalculate and update Confluence backlog page",
    		notes = "Recalculate and update Confluence backlog page")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void add(@Valid BacklogForm form, BindingResult result, ModelAndView model) {
		if (!result.hasErrors()) {
			backlogPageCorrector.correctBacklog(form.getTitle(), form.getSpace(), form.getAllowMultilineFeatures());
			model.addObject("command", new BacklogForm());
//			return new ModelAndView("confluence/updateFormSpring", "command", new BacklogForm());
		} else {
			model.addObject("command", form);
//			return new ModelAndView("confluence/updateFormSpring", "command", form);
		}
		model.setViewName("confluence/updateFormSpring");
	}

    @ApiOperation(value = "Recalculate and update Confluence backlog page",
    		notes = "Recalculate and update Confluence backlog page")
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public void formHandler(@RequestParam String space, @RequestParam String title, @RequestParam(value = "allowMultilineFeatures", defaultValue = "false", required=false) boolean allowMultilineFeatures,
			HttpServletResponse response) throws IOException {
		backlogPageCorrector.correctBacklog(title, space, allowMultilineFeatures);
//		return "redirect:" + confluenceConfig.SERVER + "/display/" + space + "/" + title;
		response.sendRedirect(confluenceConfig.SERVER + "/display/" + space + "/" + title);
	}
	
}
