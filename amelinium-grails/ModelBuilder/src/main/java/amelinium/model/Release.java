package amelinium.model;

import java.util.ArrayList;
import java.util.List;

public class Release extends ModelElement{
	private List<Feature> features;
	private int totalPoints;
	private int donePoints;
	
	public Release()
	{
		super();
		features = new ArrayList<Feature>();
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

	public List<Feature> getFeatures() {
		return features;
	}

	public void addFeature(Feature t){
		features.add(t);
	}
	
	public void removeFeature(Feature t){
		if(features.contains(t))
		features.remove(t);
	}
	
	public Feature getLastFeature(){
		if(features.size()>0) return features.get(features.size()-1);
		return null;
	}

	@Override
	public String toString() {
		return "Release:\n"+super.getContent()+"\n Features:\n"+features+"\n";
	}
	
}
