package com.tomtom.amelinium.web.standalone.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.tomtom.amelinium.projectservice.factory.ProjectServiceFactory;
import com.tomtom.amelinium.web.standalone.model.NewProjectForm;

/**
 * Controller for adding a new project. Handles the three-step (three-page)
 * spring form.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
@Controller
@RequestMapping("/projects/new")
@SessionAttributes("command")
public class NewProjectController {

	@Autowired
	private ProjectServiceFactory factory;

	/**
	 * The default handler (target0) in which the name of a new project can be
	 * specified.
	 */
	@RequestMapping("/projects/new")
	//@RequestMapping(value = "/projects/new", params = "form", method = RequestMethod.POST)
	public ModelAndView getInitialPage(final ModelMap modelMap) {
		ModelAndView model = new ModelAndView("standalone/newProject0");
		model.addObject("command", new NewProjectForm());
		return model;
	}

	/**
	 * First step handler in which the backlog in wiki markup can be provided.
	 */
	@RequestMapping(value = "/projects/new", params = "_target1", method = RequestMethod.POST)
	public ModelAndView processZeroStep(
			final @ModelAttribute("command") NewProjectForm form,
			final Errors errors) {
		ModelAndView model = new ModelAndView("standalone/newProject1");
		model.addObject("command", form);
		return model;
	}

	/**
	 * Second step handler in which the chart in wiki markup can be provided.
	 */
	@RequestMapping(value = "/projects/new", params = "_target2", method = RequestMethod.POST)
	public ModelAndView processFirstStep(
			final @ModelAttribute("command") NewProjectForm form,
			final Errors errors) {
		ModelAndView model = new ModelAndView("standalone/newProject2");
		model.addObject("command", form);
		return model;
	}

	/**
	 * The successful finish step.
	 */
	@RequestMapping(value = "/projects/new", params = "_finish", method = RequestMethod.POST)
	public String processFinish(
			final @ModelAttribute("command") NewProjectForm form,
			final Errors errors, final ModelMap modelMap,
			final SessionStatus status) {
		factory.readAndCorrectAndPersistModelsFromStrings(form.getName(), form.getBacklogContent(),
				form.getChartContent(), form.getAllowMultilineFeatures());
		status.setComplete();
		return "redirect:/web/projects/";
	}

	/**
	 * Cancel step. Can be called from any of the three pages of the form.
	 */
	@RequestMapping(value = "/projects/new", params = "_cancel", method = RequestMethod.POST)
	public String processCancel(final HttpServletRequest request,
			final HttpServletResponse response, final SessionStatus status) {
		status.setComplete();
		return "redirect:/web/projects/";
	}

}
