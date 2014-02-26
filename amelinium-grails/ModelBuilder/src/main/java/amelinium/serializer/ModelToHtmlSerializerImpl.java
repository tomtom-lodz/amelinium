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
			if (release.isStrikeThrough() && !release.getContent().contains("<del>")) {
				release.setContent("<del>"+release.getContent()+"</del>");
			} else if(!release.isStrikeThrough() && release.getContent().contains("<del>")){
				String newContent = release.getContent().replaceFirst("<del>", "").replaceFirst("</del>", "");
				release.setContent(newContent);
			}
			html += "<" + release.getTagName() + ">" + release.getContent()
						+ "</" + release.getTagName() + ">\n";
			html = serializeFeaturesToHtml(html, release.getFeatures());
		}
		html += "\n<p>BACKLOG END</p>\n" + project.getAfterBacklogHtml();

		return html;
	}

	private String serializeFeaturesToHtml(String html, List<Feature> features) {

		for (Feature feature : features) {
			if (feature.isStrikeThrough() && !feature.getContent().contains("<del>")&&feature.getContent() != "") {
				feature.setContent("<del>"+feature.getContent()+"</del>");
			} else if(!feature.isStrikeThrough() && feature.getContent().contains("<del>")){
				String newContent = feature.getContent().replaceFirst("<del>", "").replaceFirst("</del>", "");
				feature.setContent(newContent);
			}
			if (feature.getContent() != "")
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
		html += "<" + story.getTagName() + ">" + story.getContent()
				+ story.getInfoHtml() + "</" + story.getTagName() + ">\n";

		return html;
	}

}
