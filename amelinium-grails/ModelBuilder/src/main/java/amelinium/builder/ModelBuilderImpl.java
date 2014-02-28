package amelinium.builder;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import amelinium.model.Release;
import amelinium.model.Project;
import amelinium.model.Story;
import amelinium.model.Feature;
import amelinium.recalculator.ProjectRecalculatorImpl;
import amelinium.serializer.ModelToHtmlSerializer;
import amelinium.serializer.ModelToHtmlSerializerImpl;

public class ModelBuilderImpl implements ModelBuilder {
	
	public Project generateProjectModel(String address) {
		Project project = new Project();
		try {
			Document doc = Jsoup.connect(address).get();
			Element content = doc.getElementsByClass("value").get(0);
			Elements projectH1 = doc.select(".content h1");
			String projectName = projectH1.get(0).text().split(" - ")[0];

			Elements contents = content.children();
			project.setProjectName(projectName);

			buildProject(project, contents);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return project;
	}

	private Project buildProject(Project project, Elements contents) {
		boolean inRelease = false;
		boolean inFeature = false;
		boolean backlogEnd = false;
		for (Element el : contents) {
			if (!backlogEnd) {

				switch (el.tagName()) {
				case "h1":
				case "h2":
				case "h3":
				case "h4":
				case "h5":
					extractRelease(project, el);
					inRelease = true;
					break;
				case "p":
					if (el.text().matches("BACKLOG END")) {
						backlogEnd = true;
					} else if (project.getLastRelease() != null) {
						extractFeature(project, el);
						inFeature = true;
					}
					break;
				case "ul":
					extractStories(project, inRelease, inFeature, el);
					inFeature = false;
					break;
				}
			} else {
				project.setAfterBacklogHtml(project.getAfterBacklogHtml()
						+ el.outerHtml());
			}
		}

		return project;
	}

	private Project extractRelease(Project project, Element el) {
		Release release = new Release();
		if (el.text().matches(".*(\\d|\\?)/(\\d|\\?).*"))
			release.setContent(extractContent(el));
		else
			release.setContent(extractContent(el) + " - 0/0sp");
		release.setStrikeThrough(checkIfElementIsStrikeThrough(el));
		release.setTagName(el.tagName());
		project.addRelease(release);

		return project;
	}

	private Project extractFeature(Project project, Element el) {
		Feature feature = new Feature();
		if (el.text().matches(".*(\\d|\\?)/(\\d|\\?).*"))
			feature.setContent(extractContent(el));
		else
			feature.setContent(extractContent(el) + " - 0/0sp");
		feature.setStrikeThrough(checkIfElementIsStrikeThrough(el));
		feature.setTagName(el.tagName());
		project.getLastRelease().addFeature(feature);
		return project;
	}

	private Project extractStories(Project project, boolean inRelease,
			boolean inFeature, Element el) {
		for (Element st : el.children()) {
			Story story = new Story();
			story.setTagName(st.tagName());

			extractContentFromStory(st, story);
			story.setStrikeThrough(checkIfElementIsStrikeThrough(st));

			if (inFeature)
				project.getLastRelease().getLastFeature().addStory(story);
			else if (inRelease) {
				Feature tempFeature = new Feature();
				tempFeature.setTagName("p");
				project.getLastRelease().addFeature(tempFeature);
				inFeature = true;
				project.getLastRelease().getLastFeature().addStory(story);
			}
		}
		return project;
	}

	private String extractContentFromStory(Element st, Story story) {
		boolean brExists = st.html().contains("<br />");
		boolean ulExists = st.html().contains("<ul>");
		int brIndex = st.html().indexOf("<br />");
		int ulIndex = st.html().indexOf("<ul>");
		if (brExists && brIndex < ulIndex) {
			String content = st.html().replaceAll("\n", "").split("<br />")[0];
			story.setContent(content.replaceAll("&nbsp;", " "));
			String descriptionAndInfo = st.html().replaceFirst(content, "");
			story.setInfoHtml(descriptionAndInfo.replaceAll("&nbsp;", " "));
		} else if (ulExists && ulIndex < brIndex) {
			String content = st.html().replaceAll("\n", "").split("<ul>")[0];
			story.setContent(content.replaceAll("&nbsp;", " "));
			String descriptionAndInfo = st.html().replaceFirst(content, "")
					.replaceFirst("\n", "");
			story.setInfoHtml(descriptionAndInfo.replaceAll("&nbsp;", " "));
		} else {
			story.setContent(st.html().replaceAll("\n", "").replaceAll("&nbsp;", " "));

		}

		return story.getContent();
	}

	private String extractContent(Element el) {
		String content;
		if (el.html().contains("<br />")) {
			content = el.html().replaceAll("\n", "").split("<br />")[0];
			return content.replaceAll("&nbsp;", " ");
		} else {
			return el.html();
		}
	}

	private boolean checkIfElementIsStrikeThrough(Element el) {
		if (el.children().size() > 0
				&& (el.child(0).tagName() == "del" || (el.child(0).children()
						.size() > 0 && el.child(0).child(0).tagName() == "del")))
			return true;
		else
			return false;
	}

}