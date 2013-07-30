package com.tomtom.amelinium.confluence.server.controller;

import java.io.IOException;

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
import com.tomtom.amelinium.confluence.server.model.BacklogForm;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
/**
 * Controller for all the actions concerning backlogs and charts on Confluence.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
@Controller
@RequestMapping(value = "/backlog")
@Api(value = "Backlog operations", listingClass = "BacklogController", description = "All operations for backlog")
public class BacklogController {
	
	@Autowired
	private ConfluenceConfig confluenceConfig;
	@Autowired
	private BacklogPageCorrector backlogPageCorrector;

    @ApiOperation(value = "Recalculate and update Confluence backlog page",
    		notes = "Recalculate and update Confluence backlog page",
    		responseClass = "VOID")
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String formHandler(
			@ApiParam("Confluence space of backlog")
			@RequestParam String space,
			@ApiParam("Confluence page title of backlog")
			@RequestParam String title,
			@ApiParam("If features can be spanned through multiple lines")
			@RequestParam(defaultValue = "false", required=false) boolean allowMultilineFeatures) throws IOException {
		backlogPageCorrector.correctBacklog(title, space, allowMultilineFeatures);
		return "redirect:" + confluenceConfig.SERVER + "/display/" + space + "/" + title;
	}
	
}
