package amelinium.converter;

import java.util.ArrayList;

import amelinium.model.Project;
import amelinium.model.Release;

import com.tomtom.amelinium.backlogservice.model.BacklogModel;
import com.tomtom.amelinium.backlogservice.model.Column;
import com.tomtom.amelinium.backlogservice.model.FeatureGroup;
import com.tomtom.amelinium.backlogservice.model.SubProject;

public class NewToLegacyModelConverterImpl implements NewToLegacyModelConverter {

	@Override
	public BacklogModel convertToLegacyModel(Project project,
			boolean allowingMultilineFeatures) {

		BacklogModel legacyModel = new BacklogModel();

		ArrayList<FeatureGroup> featureGroups = new ArrayList<FeatureGroup>();
		int cumulativePoints = 0;
		int totalPoints = 0;
		// Set all fields for featureGroups, not providing one of them may cause
		// failure in graph painting.
		for (Release release : project.getReleases()) {
			cumulativePoints += release.getDonePoints();
			totalPoints += release.getTotalPoints();
			FeatureGroup releaseGroup = new FeatureGroup();
			releaseGroup.setColumns(new ArrayList<Column>());

			if (release.getContent().contains("<del>")
					|| release.getContent().contains("<em>")) {
				releaseGroup.setContentLeft(removeHtmlFromContent(release
						.getContent().replaceFirst(
								"\\s\\-\\s(\\d|\\?)+/(\\d|\\?)+sp", "")));
			} else {
				releaseGroup.setContentLeft(release.getContent().replaceFirst(
						"\\s\\-\\s(\\d|\\?)+/(\\d|\\?)+sp", ""));
			}
			releaseGroup.setContentMiddle(release.getDonePoints() + "sp/"
					+ release.getTotalPoints() + "sp");
			releaseGroup.setContentRight("");
			releaseGroup.setCummulativePoints(cumulativePoints);
			releaseGroup.setDone(release.isStrikeThrough());
			releaseGroup.setDonePoints(release.getDonePoints());
			releaseGroup.setPoints(release.getTotalPoints());

			featureGroups.add(releaseGroup);
		}
		SubProject subProject = new SubProject();
		subProject.setFeatureGroups(featureGroups);
		if (cumulativePoints == totalPoints)
			subProject.setDone(true);
		else
			subProject.setDone(false);
		subProject.setDonePoints(cumulativePoints);
		subProject.setPoints(totalPoints);

		ArrayList<SubProject> subProjects = new ArrayList<SubProject>();
		subProjects.add(subProject);

		legacyModel.setProjects(subProjects);
		legacyModel.setAllowingMulitilineFeatures(allowingMultilineFeatures);
		legacyModel.setOverallBurnedStoryPoints(cumulativePoints);

		return legacyModel;
	}

	// <em><del>dsadsadsadsa</del></em>
	private String removeHtmlFromContent(String content) {
		if (content.contains("<em>") && content.contains("<del>")) {
			return subContent(8, content);
		} else if (content.contains("<del>")) {
			return subContent(4, content);
		} else if (content.contains("<em>")) {
			return subContent(3, content);

		} else {
			return content;
		}

	}

	private String subContent(int startIndex, String content) {
		int endIndex = content.indexOf("</");
		return content.substring(8, endIndex);
	}

}
