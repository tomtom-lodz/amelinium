package amelinium.converter;

import com.tomtom.amelinium.backlogservice.model.BacklogModel;

import amelinium.model.Project;

public interface NewToLegacyModelConverter {
	BacklogModel convertToLegacyModel(Project project, boolean allowingMultilineFeatures);
}
