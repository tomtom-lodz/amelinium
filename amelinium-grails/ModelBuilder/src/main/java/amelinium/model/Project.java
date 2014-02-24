package amelinium.model;

import java.util.ArrayList;
import java.util.List;

public class Project {
	private List<Release> releases;
	String projectName = "";
	String afterBacklogHtml = "";
	
	public Project()
	{
		releases = new ArrayList<Release>();
	}
	
	public List<Release> getReleases() {
		return releases;
	}
	
	public void setReleases(List<Release> releases) {
		this.releases = releases;
	}
	
	public void addRelease(Release r)
	{
		releases.add(r);
	}
	public void removeRelease(Release r)
	{
		if(releases.contains(r))
		releases.remove(r);
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getAfterBacklogHtml() {
		return afterBacklogHtml;
	}

	public void setAfterBacklogHtml(String afterBacklogHtml) {
		this.afterBacklogHtml = afterBacklogHtml;
	}

	public Release getLastRelease(){
		if(releases.size()>0) return releases.get(releases.size()-1);
		return null;
	}

	@Override
	public String toString() {
		return "Project: "+projectName+" \n\n releases: "+releases+"\n\n";
	}
	
	
}
