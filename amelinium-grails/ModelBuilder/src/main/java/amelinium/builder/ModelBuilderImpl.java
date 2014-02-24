package amelinium.builder;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import amelinium.model.Release;
import amelinium.model.Project;
import amelinium.model.Story;
import amelinium.model.Feature;

public class ModelBuilderImpl implements ModelBuilder {
	private Pattern pattern = Pattern.compile("\\s*-\\s*(\\d|\\?)+sp");
	
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
					} else if(project.getLastRelease()!=null){
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
			story.setStrikeThrough(checkIfElementIsStrikeThrough(st));
			Matcher matcher = pattern.matcher(st.text());
			if (matcher.find())
				story.setContent(extractContent(st));
			else
				story.setContent(extractContent(st) + " - 0sp");
			story.setTagName(st.tagName());
			if (st.children().size() > 0 && st.child(0).tagName() == "em")
				story.setCurrentlyWorkedOn(true);

			extractInfo(st, story);

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

	private Story extractInfo(Element st, Story story) {
		Elements children = st.children();
		if (children.size() > 0) {
			if (children.get(0).tagName() == "del"
					|| children.get(0).tagName() == "a"
					|| children.get(0).tagName() == "em")
				children.remove(0);
			String infoHtml = getHtmlInfo(children);

			story.setInfoHtml(infoHtml);

		}
		return story;
	}

	private String getHtmlInfo(Elements children) {
		String htmlInfo = "";
		for (Element el : children) {
			htmlInfo += "<" + el.tagName() + ">" + el.html() + "</"
					+ el.tagName() + ">";
		}
		return htmlInfo;
	}

	private boolean checkIfElementIsStrikeThrough(Element el) {
		if (el.children().size() > 0 && el.child(0).tagName() == "del")
			return true;
		else
			return false;
	}

	private String extractContent(Element el) {
			return extractContentWithoutDescription(el);
	}

	private String extractContentWithoutDescription(Element el) {
		if (el.children().size() > 0 && el.child(0).children().size() > 0
				&& el.child(0).child(0).tagName() == "a") {
			return el.child(0).html().replaceAll("&nbsp;", " ");
		} else if (el.children().size() > 0 && el.child(0).tagName() == "a") {
			String[] ownContent = el.ownText().replaceAll("&nbsp;", " ").split(" - ");
			Element link = el.select("a[href]").first();
			String linkHtml = link.outerHtml();
			return ownContent[0] + " " + linkHtml + " - " + ownContent[1];
		} else if (el.children().size() > 0 && el.child(0).tagName() == "del") {
			return el.child(0).ownText().replaceAll("&nbsp;", " ");
		} else if (el.children().size() > 0 && el.child(0).tagName() == "ul") {
			return el.ownText().replaceAll("&nbsp;", " ");
		} else {
			return el.text().replaceAll("&nbsp;", " ");
		}
	}
}
