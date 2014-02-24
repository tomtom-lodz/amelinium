package amelinium.serializer;

import java.util.List;

import amelinium.model.Feature;
import amelinium.model.Project;
import amelinium.model.Release;
import amelinium.model.Story;

public class ModelToHtmlSerializerImpl implements ModelToHtmlSerializer {

	@Override
	public String serializeModelToHtml(Project project) {
		String html = "";
		for (Release release : project.getReleases()) {
			if (release.isStrikeThrough())
				html += "<" + release.getTagName() + "><del>"
						+ release.getContent() + "</del></"
						+ release.getTagName() + ">\n";
			else
				html += "<" + release.getTagName() + ">" + release.getContent()
						+ "</" + release.getTagName() + ">\n";
			html = serializeFeaturesToHtml(html, release.getFeatures());
		}
		html +="\n<p>BACKLOG END</p>\n"+project.getAfterBacklogHtml();

		return html;
	}

	private String serializeFeaturesToHtml(String html, List<Feature> features) {

		for (Feature feature : features) {

			if (feature.isStrikeThrough() && feature.getContent() != "")
				html += "<" + feature.getTagName() + "><del>"
						+ feature.getContent() + "<del></"
						+ feature.getTagName() + ">\n";
			else if (feature.getContent() != "")
				html += "<" + feature.getTagName() + ">" + feature.getContent()
						+ "</" + feature.getTagName() + ">\n";
			html = serializeStoriesToHtml(html, feature.getStories());
		}

		return html;
	}

	private String serializeStoriesToHtml(String html, List<Story> stories) {
		html += "<ul>\n";
		for (Story story : stories) {
			html = serializeStoryToHtml(html, story);
		}
		html += "</ul>\n";

		return html;
	}

	private String serializeStoryToHtml(String html, Story story) {
		if (story.isCurrentlyWorkedOn())
			html += "<" + story.getTagName() + "><em>" + story.getContent()
					+ "</em>\n";
		else if (story.isStrikeThrough())
			html += "<" + story.getTagName() + "><del>" + story.getContent()
					+ "</del>\n";
		else
			html += "<" + story.getTagName() + ">" + story.getContent();
		if (story.getInfoHtml() != "")
			html += story.getInfoHtml() + "\n</" + story.getTagName() + ">\n";
		else
			html += "</" + story.getTagName() + ">\n";

		return html;
	}

}
