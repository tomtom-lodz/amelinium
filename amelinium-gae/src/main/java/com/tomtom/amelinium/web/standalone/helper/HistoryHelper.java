package com.tomtom.amelinium.web.standalone.helper;

import com.tomtom.amelinium.projectservice.model.Project;

public class HistoryHelper {
	public int calculateTranslationOlder(int translation, Project project) {
		int translationOlder = translation + 1;
		if (((project.getHistorySize() - 1) - (translationOlder * 100)) < 0) {
			translationOlder = translation;
		}
		return translationOlder;
	}

	public int calculateTranslationNewer(int translation) {
		int translationNewer = translation - 1;

		if (translationNewer < 0) {
			translationNewer = 0;
		}
		return translationNewer;
	}

	public int calculateFinalIndex(int translation, Project project) {
		int finalIndex = (project.getHistorySize() - 1) - (translation * 100);

		if (finalIndex < 99) {
			finalIndex = 99;
		}
		return finalIndex;
	}
	

	public int calculateInitialIndex(int finalIndex) {
		int initialIndex = finalIndex - 99;
		return initialIndex;
	}
}
