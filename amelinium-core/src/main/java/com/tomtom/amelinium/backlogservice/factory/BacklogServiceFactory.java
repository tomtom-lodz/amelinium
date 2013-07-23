package com.tomtom.amelinium.backlogservice.factory;

import java.io.InputStream;
import java.util.ArrayList;

import com.tomtom.amelinium.backlogservice.builders.BacklogModelBuilder;
import com.tomtom.amelinium.backlogservice.corrector.BacklogModelCorrector;
import com.tomtom.amelinium.backlogservice.model.BacklogModel;
import com.tomtom.amelinium.backlogservice.serializer.BacklogModelSerializer;
import com.tomtom.amelinium.common.LineProducer;
/**
 * Factory that builds a backlog model out of the given sources.
 * 
 *  @author Natasza.Nowak@tomtom.com
 */
public class BacklogServiceFactory {

	LineProducer producer = new LineProducer();
	BacklogModelBuilder builder = new BacklogModelBuilder();
	BacklogModelCorrector backlogModelCorrector = new BacklogModelCorrector();
	BacklogModelSerializer backlogModelSerializer = new BacklogModelSerializer();

	/**
	 * Reads backlog from stream and builds the model.
	 */
	public BacklogModel readBacklogModelFromStream(InputStream stream, boolean allowingMultilineFeatures) {
		ArrayList<String> lines = producer.readLinesFromStream(stream);
		BacklogModel backlogModel = builder.buildBacklogModel(lines, allowingMultilineFeatures);

		return backlogModel;
	}
	
	/**
	 * Reads backlog from stream, builds the model and recalculates the points.
	 */
	public BacklogModel readAndCorrectBacklogModelFromStream(
			InputStream stream, boolean allowingMultilineFeatures) {
		BacklogModel backlogModel = readBacklogModelFromStream(stream, allowingMultilineFeatures);
		BacklogModel newBacklogModel = backlogModelCorrector
				.correctModelPoints(backlogModel);
		return newBacklogModel;
	}
	
	/**
	 * Reads backlog from file and builds the model.
	 */
	public BacklogModel readBacklogModelFromFile(String fileName, boolean allowingMultilineFeatures) {
		ArrayList<String> lines = producer.readLinesFromFile(fileName);
		BacklogModel backlogModel = builder.buildBacklogModel(lines, allowingMultilineFeatures);

		return backlogModel;
	}

	/**
	 * Reads backlog from file, builds the model and recalculates the points.
	 */
	public BacklogModel readAndCorrectBacklogModelFromFile(String fileName, boolean allowingMultilineFeatures) {
		BacklogModel backlogModel = readBacklogModelFromFile(fileName, allowingMultilineFeatures);
		return backlogModelCorrector.correctModelPoints(backlogModel);
	}

	/**
	 * Reads backlog from file, builds the model and recalculates the points.
	 */
	public String readCorrectAndSerializeModelFromFile(String fileName, boolean allowingMultilineFeatures) {
		BacklogModel backlogModel = readAndCorrectBacklogModelFromFile(fileName, allowingMultilineFeatures);
		return backlogModelSerializer.serializeModel(backlogModel);
	}

	/**
	 * Reads backlog from string and builds the model.
	 */
	public BacklogModel readBacklogModelFromString(String backlogContent, boolean allowingMultilineFeatures) {
		ArrayList<String> lines = producer.readLinesFromString(backlogContent);
		BacklogModel backlogModel = builder.buildBacklogModel(lines, allowingMultilineFeatures);

		return backlogModel;
	}

	/**
	 * Reads backlog from string, builds the model and recalculates the points.
	 */
	public BacklogModel readAndCorrectBacklogModelFromString(
			String backlogContent, boolean allowingMultilineFeatures) {
		BacklogModel backlogModel = readBacklogModelFromString(backlogContent, allowingMultilineFeatures);
		BacklogModel newBacklogModel = backlogModelCorrector
				.correctModelPoints(backlogModel);
		return newBacklogModel;
	}


}
