package amelinium.model;

public class ModelElement {
	
	private String content;
	private String tagName;
	private boolean strikeThrough;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public boolean isStrikeThrough() {
		return strikeThrough;
	}
	public void setStrikeThrough(boolean strokeThrough) {
		this.strikeThrough = strokeThrough;
	}
	
	
}
