package amelinium.model;

public class Story extends ModelElement{
	private String infoHtml;
	private int points;

	public Story(){
		super();
		infoHtml="";
	}
	
	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}


	public String getInfoHtml() {
		return infoHtml;
	}
	public void setInfoHtml(String infoHtml) {
		this.infoHtml = infoHtml;
	}

	@Override
	public String toString() {
		return "Story:\n"+super.getContent()+"\nInformations: \n"+infoHtml+"\n\n";
	}

}
