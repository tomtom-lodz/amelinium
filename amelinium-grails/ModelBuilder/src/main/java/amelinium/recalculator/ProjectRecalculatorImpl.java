package amelinium.recalculator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import amelinium.model.Feature;
import amelinium.model.Project;
import amelinium.model.Release;
import amelinium.model.Story;

public class ProjectRecalculatorImpl implements ProjectRecalculator {
	private Pattern pattern = Pattern.compile("\\s*-\\s*(\\d|\\?)+sp");
	
	@Override
	public Project recalculateProject(Project project) {
		Project recalculatedProject = new Project();
		recalculatedProject.setProjectName(project.getProjectName());
		recalculatedProject.setReleases(project.getReleases());

		recalculateReleases(recalculatedProject.getReleases());

		return recalculatedProject;
	}

	private void recalculateReleases(List<Release> releases) {

		for (Release release : releases) {
			release.setContent(recalculateFeatures(release,
					release.getFeatures()));
		}

	}

	private String recalculateFeatures(Release release, List<Feature> features) {

		int calculatedPoints = 0;
		int allPoints = 0;

		for (Feature feature : features) {
			int[] newValues = recalculateStories(feature.getContent(),
					feature.getStories());
			String appEnd = " - " + newValues[0] + "/" + newValues[1] + "sp";
			String featureContent = feature.getContent();
			if (featureContent != "") {
				featureContent = featureContent.replaceFirst(
						"\\s+-{1}\\s+(\\d|\\?)+/(\\d|\\?)+sp", appEnd);
			}
			if (newValues[0] == newValues[1])
				feature.setStrikeThrough(true);
			feature.setTotalPoints(newValues[1]);
			feature.setDonePoints(newValues[0]);
			feature.setContent(featureContent);
			calculatedPoints += newValues[0];
			allPoints += newValues[1];
		}

		String appEnd = " - " + calculatedPoints + "/" + allPoints + "sp";
		release.setContent(release.getContent().replaceFirst(
				"\\s+-{1}\\s+(\\d|\\?)+/(\\d|\\?)+sp", appEnd));
		if (calculatedPoints == allPoints)
			release.setStrikeThrough(true);
		release.setTotalPoints(allPoints);
		release.setDonePoints(calculatedPoints);

		return release.getContent();
	}

	private int[] recalculateStories(String featureContent, List<Story> stories) {

		int calculatedPoints = 0;
		int allPoints = 0;

		for (Story story : stories) {
			int storyPoints = getPointsFromStory(story);
			if (story.isStrikeThrough())
				calculatedPoints += storyPoints;
			story.setPoints(storyPoints);
			allPoints += storyPoints;
		}

		return new int[] { calculatedPoints, allPoints };
	}

	private int getPointsFromStory(Story story) {
		Matcher matcher = pattern.matcher(story.getContent());
		String points;
		if (matcher.find()) {
			points = story.getContent()
					.substring(matcher.start(), matcher.end())
					.replaceAll("sp", "");
			if (points.matches("\\?+")) {
				story.setContent(story.getContent().replaceFirst(
						"(\\d|\\?)+sp", "0sp"));
				return 0;
			}
		} else {
			return 0;
		}
		int storyPoints = Integer.parseInt(points);
		return storyPoints;
	}

}
