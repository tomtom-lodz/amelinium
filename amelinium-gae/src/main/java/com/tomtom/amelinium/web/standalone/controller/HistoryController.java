package com.tomtom.amelinium.web.standalone.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tomtom.amelinium.projectservice.model.Company;
import com.tomtom.amelinium.projectservice.model.Project;
import com.tomtom.amelinium.projectservice.model.ProjectDao;
import com.tomtom.amelinium.projectservice.snapshot.ProjectHistoryItem;

public class HistoryController {
	@Autowired
	private Company company;
	
	@Autowired
	private ProjectDao projectDao;
	
	public void setCompany(Company company) {
		this.company = company;
	}
	
	public void setProjectsDao(ProjectDao projects) {
		this.projectDao = projects;
	}

	@RequestMapping(value = "/project/{id}/backlog/r{r}/view", method = RequestMethod.GET)
	public ModelAndView viewBacklogSnapshot(@PathVariable Long id, @PathVariable String r) {
//		ArrayList<Project> projectList = company.getProjects();
//		int index = Integer.parseInt(id);
//		Project project = projectList.get(index);
		
		Project project = projectDao.get(id);
		
		int revision = Integer.parseInt(r);
		ProjectHistoryItem snapshot = project.getProjectSnapshot(revision);
		ModelAndView model = new ModelAndView("standalone/backlogHistoryPage");
		model.addObject("project", project);
		model.addObject("snapshot", snapshot);
		return model;

	}
}
