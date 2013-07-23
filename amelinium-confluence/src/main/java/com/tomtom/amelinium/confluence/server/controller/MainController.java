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
import com.tomtom.amelinium.confluence.server.model.BacklogForm;
import com.tomtom.amelinium.confluence.server.model.ChartForm;
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

	@RequestMapping("/backlog/")
	public ModelAndView updateFormWelcomeHandler() {
		return new ModelAndView("confluence/updateFormSpring", "command", new BacklogForm());
	}

	@RequestMapping("/chart-and-backlog/")
	public ModelAndView chartFormWelcomeHandler() {
		return new ModelAndView("confluence/chartFormSpring", "command", new ChartForm());
	}

	@RequestMapping(value = "/backlog/update", method = RequestMethod.GET)
	public String formHandler(@RequestParam String space, @RequestParam String title, @RequestParam(value = "allowMultilineFeatures", defaultValue = "false") boolean allowMultilineFeatures) {
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
			@RequestParam(value = "allowMultilineFeatures", defaultValue = "false") boolean allowMultilineFeatures) {
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

}
