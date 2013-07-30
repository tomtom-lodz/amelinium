package com.tomtom.amelinium.confluence.other.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tomtom.amelinium.confluence.config.ConfluenceConfig;
import com.tomtom.amelinium.confluence.logic.BacklogPageCorrector;
import com.tomtom.amelinium.confluence.logic.ChartPageCorrector;
import com.tomtom.amelinium.confluence.server.model.BacklogForm;
import com.tomtom.amelinium.confluence.server.model.ChartForm;

@Controller
public class FormsController {

	@Autowired
	private ConfluenceConfig confluenceConfig;
	@Autowired
	private BacklogPageCorrector backlogPageCorrector;
	@Autowired
	private ChartPageCorrector chartPageCorrector;

	
	@RequestMapping(value = "/backlog/", method = RequestMethod.GET)
	public ModelAndView updateFormWelcomeHandler(ModelAndView model) {
		return new ModelAndView("confluence/updateFormSpring", "command", new BacklogForm());
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
    
	@RequestMapping(value = "/chart-and-backlog/", method = RequestMethod.GET)
	public ModelAndView chartFormWelcomeHandler() {
		return new ModelAndView("confluence/chartFormSpring", "command", new ChartForm());
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
