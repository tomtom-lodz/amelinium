package amelinium1.grails

import amelinium.builder.ModelBuilder
import amelinium.builder.ModelBuilderImpl
import amelinium.converter.NewToLegacyModelConverter
import amelinium.converter.NewToLegacyModelConverterImpl
import amelinium.recalculator.ProjectRecalculator
import amelinium.recalculator.ProjectRecalculatorImpl
import amelinium.serializer.ModelToHtmlSerializer
import amelinium.serializer.ModelToHtmlSerializerImpl
import com.tomtom.amelinium.backlogservice.builders.BacklogModelBuilder;
import com.tomtom.amelinium.backlogservice.corrector.BacklogModelCorrector;
import com.tomtom.amelinium.backlogservice.model.BacklogModel
import com.tomtom.amelinium.backlogservice.serializer.BacklogModelSerializer;
import com.tomtom.amelinium.backlogservice.serializer.WikiToHTMLSerializer;
import com.tomtom.amelinium.common.LineProducer;
import com.tomtom.woj.amelinium.journal.converter.AbsoluteToCumulativeConverterInPlace;
import com.tomtom.woj.amelinium.journal.io.BacklogJournalReader;
import com.tomtom.woj.amelinium.journal.model.BacklogChunk
import com.tomtom.woj.amelinium.journal.operations.BacklogChunksMerger;
import com.tomtom.woj.amelinium.journal.operations.DoneLinesRemover;
import com.tomtom.woj.amelinium.journal.operations.MergedBacklogColumnSorter;
import com.tomtom.woj.amelinium.journal.updating.BacklogAndJournalUpdater;
import com.tomtom.woj.amelinium.plots.burndown.BacklogJournalSubtractorIntoBurndown;
import com.tomtom.woj.amelinium.plots.burndown.BurndownModelFactory;
import com.tomtom.woj.amelinium.plots.burndown.BurndownPlotJavascriptGenerator;
import com.tomtom.woj.amelinium.plots.burnup.BurnupModel
import com.tomtom.woj.amelinium.plots.burnup.BurnupModelFactory;
import com.tomtom.woj.amelinium.plots.burnup.BurnupPlotJavascriptGenerator;
import com.tomtom.woj.amelinium.plots.burnup.BurnupTableGenerator;

import org.joda.time.DateTime

/**
 * CoreService
 * A service class encapsulates the core business logic of a Grails application
 */
class CoreService {

	def BacklogJournalReader reader = new BacklogJournalReader();
	def AbsoluteToCumulativeConverterInPlace cumulativeConverter = new AbsoluteToCumulativeConverterInPlace();
	def BacklogChunksMerger merger = new BacklogChunksMerger();
	def BurnupModelFactory burnupModelFactory = new BurnupModelFactory();
	def BurnupPlotJavascriptGenerator burnupGenerator = new BurnupPlotJavascriptGenerator();
	def MergedBacklogColumnSorter columnSorter = new MergedBacklogColumnSorter();
	def DoneLinesRemover doneLinesRemover = new DoneLinesRemover();
	def BacklogJournalSubtractorIntoBurndown subtractor = new BacklogJournalSubtractorIntoBurndown();
	def BurndownModelFactory burndownModelFactory = new BurndownModelFactory();
	def BurndownPlotJavascriptGenerator burndownGenerator = new BurndownPlotJavascriptGenerator();
	def BurnupTableGenerator burnupTableGenerator = new BurnupTableGenerator();
	def BacklogAndJournalUpdater journalUpdater = new BacklogAndJournalUpdater();
	def WikiToHTMLSerializer htmlSerializer = new WikiToHTMLSerializer();
	def ModelBuilder projectModelBuilder = new ModelBuilderImpl();
	def ProjectRecalculator projectModelRecalculator = new ProjectRecalculatorImpl();
	def NewToLegacyModelConverter legacyConverter = new NewToLegacyModelConverterImpl();
	def ModelToHtmlSerializer projectSerializer = new ModelToHtmlSerializerImpl();

	String recalculateCsv(String backlogAddress,String csvText, boolean isCumulative, boolean addNewFeatureGroups, boolean overwriteExistingDate,boolean allowingMultilineFeatures){
		DateTime dateTime  = new DateTime().toDateMidnight().toDateTime();
		amelinium.model.Project project = projectModelBuilder.generateProjectModel(backlogAddress)
		projectModelRecalculator.recalculateProject(project)
		BacklogModel legacyModel = legacyConverter.convertToLegacyModel(project, isCumulative);
		String updatedJournal = journalUpdater.generateUpdatedString(dateTime, legacyModel, csvText, isCumulative, addNewFeatureGroups, overwriteExistingDate, allowingMultilineFeatures);
	}

	String recalculateBacklog(String backlogAddress) {
		amelinium.model.Project project = projectModelBuilder.generateProjectModel(backlogAddress)
		projectModelRecalculator.recalculateProject(project)
		String newContent = projectSerializer.serializeModelToHtml(project);
	}


	String[] createCsvChartAndTable(String csvText, boolean isCumulative,double dailyVelocity, double dailyScopeIncrease, String chartName){
		ArrayList<BacklogChunk> chunks = reader.readFromStringNullAllowed(csvText);

		if(!isCumulative) {
			cumulativeConverter.convertIntoCumulative(chunks);
		}
		BacklogChunk merged = merger.mergeCumulativeChunks(chunks);

		doneLinesRemover.removeDoneLinesFromCumulativeMerged(merged);

		columnSorter.sortColumns(merged);
		BurnupModel burnupModel = burnupModelFactory.createModel(merged, dailyVelocity, dailyScopeIncrease);
		String chart1 = burnupGenerator.generateBurnup(burnupModel, chartName, "Burnup chart");
		String burnupTable = burnupTableGenerator.generateTable(burnupModel);
		String [] result = [chart1, burnupTable];
	}

	String serializeText(String csvText){
		htmlSerializer.convert(csvText);
	}
}
