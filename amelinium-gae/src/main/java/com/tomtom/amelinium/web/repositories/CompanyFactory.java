package com.tomtom.amelinium.web.repositories;

import com.tomtom.amelinium.projectservice.factory.ProjectServiceFactory;
import com.tomtom.amelinium.projectservice.model.Company;
import com.tomtom.amelinium.projectservice.model.Project;
import com.tomtom.amelinium.projectservice.snapshot.ProjectHistoryItem;

/**
 * Creates an exemplary Company object. Fills it with data read from exemplary
 * backlog/chart pairs of text resources.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
public class CompanyFactory {

	public Company loadDataFromRepository() {

		Company company = new Company();

//		String backlogFileName0 = "company/0/backlog.txt";
//		String chartFileName0 = "company/0/chart.txt";
//		String backlogFileName1 = "company/1/backlog.txt";
//		String chartFileName1 = "company/1/chart.txt";
//		String backlogFileName2 = "company/2/backlog.txt";
//		String chartFileName2 = "company/2/chart.txt";
//
//		ProjectServiceFactory factory = new ProjectServiceFactory();
//		Project p0 = factory.readAndCorrectModelsFromResources("Project A",
//				backlogFileName0, chartFileName0, true);
//		Project p1 = factory.readAndCorrectModelsFromResources("Project B",
//				backlogFileName1, chartFileName1, true);
//		Project p2 = factory.readAndCorrectModelsFromResources("Project C",
//				backlogFileName2, chartFileName2, true);
//		generateMockHistory(p0);
//		company.addProject(p0);
//		company.addProject(p1);
//		company.addProject(p2);

		return company;
	}
	
	private void generateMockHistory(Project project){
		String wikiMarkupBacklog = project.getBacklogWikiMarkup();
		String wikiMarkupChart = project.getChartWikiMarkup();
		for (int i = 1; i <201; i++){
			ProjectHistoryItem projectHistoryItem = new ProjectHistoryItem(i, wikiMarkupBacklog, wikiMarkupChart);
//			project.addProjectSnapshotToHistory(projectSnapshot);
		}
	}

}
