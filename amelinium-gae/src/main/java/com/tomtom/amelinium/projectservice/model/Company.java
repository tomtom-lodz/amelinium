package com.tomtom.amelinium.projectservice.model;

import java.util.ArrayList;

import com.googlecode.objectify.ObjectifyService;
import com.tomtom.amelinium.projectservice.snapshot.BacklogSnapshot;
import com.tomtom.amelinium.projectservice.snapshot.ChartSnapshot;
import com.tomtom.amelinium.projectservice.snapshot.ProjectHistoryItem;

/**
 * Object model of the company.
 * 
 *  @author Natasza.Nowak@tomtom.com
 */
public class Company {
	
	static {
        OfyService.ofy();
    }

	private ArrayList<Project> projects = new ArrayList<Project>();

	public ArrayList<Project> getProjects() {
		return projects;
	}

	public void setProjects(ArrayList<Project> projects) {
		this.projects = projects;
	}
	
	public void addProject(Project project){
		projects.add(project);
	}
	
	public void removeProject(Project project){
		projects.remove(project);
	}
	
	public void removeProject(String id){
		int index = Integer.parseInt(id);
		for (Project p : projects){
			if (p.getId()==index){
				projects.remove(p);
			}
		}
	}

}
