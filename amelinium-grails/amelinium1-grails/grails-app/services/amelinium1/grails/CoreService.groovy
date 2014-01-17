package amelinium1.grails

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
    def LineProducer producer = new LineProducer();
    def BacklogModelBuilder builder = new BacklogModelBuilder();
    def BacklogModelCorrector backlogModelCorrector = new BacklogModelCorrector();
    def BacklogModelSerializer generator = new BacklogModelSerializer();
    def BacklogAndJournalUpdater journalUpdater = new BacklogAndJournalUpdater();
    def WikiToHTMLSerializer htmlSerializer = new WikiToHTMLSerializer();
    

    def recalculateCsv(String backlogText,String csvText, boolean isCumulative, boolean addNewFeatureGroups, boolean overwriteExistingDate,boolean allowingMultilineFeatures){
        DateTime dateTime  = new DateTime().toDateMidnight().toDateTime();
        String updatedJournal = journalUpdater.generateUpdatedString(dateTime, backlogText, csvText, isCumulative, addNewFeatureGroups, overwriteExistingDate, allowingMultilineFeatures);
    }
    
    def recalculateBacklog(String oldContent){
        ArrayList<String> lines = producer.readLinesFromString(oldContent);
        BacklogModel backlogModel = builder.buildBacklogModel(lines,true);
        backlogModel = backlogModelCorrector.correctModelPoints(backlogModel);
        String newContent = generator.serializeModel(backlogModel);
    }
    
    
    def createCsvChartAndTable(String csvText, boolean isCumulative,double dailyVelocity, double dailyScopeIncrease, String chartName){
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
    
    def serializeText(String csvText){
        htmlSerializer.convert(csvText);  
    }
}
