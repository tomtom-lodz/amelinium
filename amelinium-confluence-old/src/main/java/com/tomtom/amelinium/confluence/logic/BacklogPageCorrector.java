package com.tomtom.amelinium.confluence.logic;

import java.util.ArrayList;

import com.tomtom.amelinium.backlogservice.builders.BacklogModelBuilder;
import com.tomtom.amelinium.backlogservice.corrector.BacklogModelCorrector;
import com.tomtom.amelinium.backlogservice.model.BacklogModel;
import com.tomtom.amelinium.backlogservice.serializer.BacklogModelSerializer;
import com.tomtom.amelinium.common.LineProducer;
import com.tomtom.amelinium.confluence.client.ConfluenceOperations;
import com.tomtom.amelinium.confluence.config.ConfluenceConfig;

public class BacklogPageCorrector {
	
	private ConfluenceConfig config = new ConfluenceConfig();
	
	/**
	 * Retrieves, corrects and updates a specified backlog on the Confluence.
	 * <p>
	 * First, the method retrieves the content of the backlog to be corrected.
	 * If the content is not empty, the model based on it is built and
	 * corrected. Then the new backlog content is generated. Finally, the old
	 * backlog content is replaced on the server with the newly generated one.
	 * <p>
	 * The method uses the server and credentials set in
	 * service.config.confluence.
	 * 
	 * @param title
	 *            Title of the backlog Page to be corrected
	 * @param space
	 *            Name of the Confluence space where the backlog to be corrected
	 *            is located.
	 * 
	 * @return message describing if operation is successful or not. If the
	 *         backlog with the specified parameters is found on the server, the
	 *         response "Backlog <code>title</code> corrected
	 *         successfully!" is returned. If not, the response is "Page
	 *         <code>title</code> not found in <code>space</code> or the test
	 *         user is not authorized to it."
	 */

	public String correctBacklog(String title, String space, boolean allowingMultilineFeatures) {

		String response;

		String oldContent = ConfluenceOperations.getPageSource(config.SERVER, config.USER, config.PASS, space, title);

		if (oldContent.isEmpty()) {
			response = "Page '" + title + "' not found in space '" + space + "' or the test user is not authorized to it.";
		} else {
			String newContent = generateNewBacklogContent(oldContent, allowingMultilineFeatures);

			if (!newContent.equals(oldContent))

			{
				ConfluenceOperations.updatePageSource(config.SERVER, config.USER, config.PASS, space, title, newContent);
				response = "Backlog '" + title + "' corrected successfully!";
			} else {
				response = "Backlog '" + title + "' didn't require correction.";
			}

		}
		return response;
	}

	/**
	 * Generates new backlog content given the one to update.
	 * 
	 * @param oldContent
	 *            The content to be update.
	 * @return Updated content.
	 */
	private String generateNewBacklogContent(String oldContent, boolean allowingMultilineFeatures) {
		LineProducer producer = new LineProducer();
		BacklogModelBuilder builder = new BacklogModelBuilder();
		BacklogModelCorrector backlogModelCorrector = new BacklogModelCorrector();
		BacklogModelSerializer generator = new BacklogModelSerializer();

		ArrayList<String> lines = producer.readLinesFromString(oldContent);
		BacklogModel backlogModel = builder.buildBacklogModel(lines,allowingMultilineFeatures);
		backlogModel = backlogModelCorrector.correctModelPoints(backlogModel);
		String newContent = generator.serializeModel(backlogModel);
		return newContent;
	}
}
