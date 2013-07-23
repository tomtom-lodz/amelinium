package com.tomtom.amelinium.web.standalone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PlanningController {
	@RequestMapping("/planning/")
	public ModelAndView mainLayoutHandler() {
		return new ModelAndView("tabs/mainLayout", "command", "");
	}
	
	@RequestMapping("/planning/backlog")
	public ModelAndView backlogHandler() {
		return new ModelAndView("tabs/backlogTab", "command", "");
	}
	
	@RequestMapping("/planning/chart")
	public ModelAndView chartHandler() {
		return new ModelAndView("tabs/chartTab", "command", "");
	}
	
	@RequestMapping("/planning/backlog/edit")
	public ModelAndView backlogEditHandler() {
		return new ModelAndView("tabs/backlogEditTab", "command", "");
	}
	
	@RequestMapping("/planning/chart/edit")
	public ModelAndView chartEditHandler() {
		return new ModelAndView("tabs/chartEditTab", "chartedit", "");
	}
}
