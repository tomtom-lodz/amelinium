package amelinium.model;

import java.util.ArrayList;
import java.util.List;

public class Feature extends ModelElement{
	private List<Story> stories;
	private int totalPoints;
	private int donePoints;
	
	public Feature(){
		super();
		stories = new ArrayList<Story>();
		setContent("");
	}
	
	public int getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}

	public int getDonePoints() {
		return donePoints;
	}

	public void setDonePoints(int donePoints) {
		this.donePoints = donePoints;
	}

	public void addStory(Story s){
		stories.add(s);
	}
	public void removeStory(Story s){
		if(stories.contains(s))
		stories.remove(s);
	}
	public List<Story> getStories() {
		return stories;
	}
	public void setStories(List<Story> stories) {
		this.stories = stories;
	}
	
	public Story getLastStory(){
		if(stories.size()>0) return stories.get(stories.size()-1);
		return null;
	}
	
	@Override
	public String toString() {
		return "Feature:\n"+super.getContent()+"\nStories:\n"+stories+"\n";
	}
	
}
