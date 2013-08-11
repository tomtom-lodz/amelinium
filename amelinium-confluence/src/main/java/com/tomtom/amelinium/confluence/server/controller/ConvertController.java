package com.tomtom.amelinium.confluence.server.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tomtom.amelinium.confluence.client.ConfluenceOperations;
import com.tomtom.amelinium.confluence.config.ConfluenceConfig;
import com.tomtom.woj.amelinium.journal.converter.AbsoluteToCumulativeConverterInPlace;
import com.tomtom.woj.amelinium.journal.converter.CumulativeToAbsoluteConverter;
import com.tomtom.woj.amelinium.journal.io.BacklogJournalReader;
import com.tomtom.woj.amelinium.journal.io.BacklogJournalSerializer;
import com.tomtom.woj.amelinium.journal.model.BacklogChunk;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
/**
 * Controller for all the actions concerning backlogs and charts on Confluence.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
@Controller
@RequestMapping(value = "/convert")
@Api(value = "Converting CSV form one format to another", listingClass = "ConvertController",
description = "Converting CSV form one format to another")
public class ConvertController {
	
	@Autowired
	private ConfluenceConfig confluenceConfig;
	@Autowired
	private CumulativeToAbsoluteConverter cumulativeToAbsoluteConverter;
	@Autowired
	private AbsoluteToCumulativeConverterInPlace absoluteToCumulativeConverterInPlace;
	@Autowired
	private BacklogJournalSerializer backlogJournalSerializer;
	
    @ApiOperation(value = "Convert CSV page from cumulative to absolute",
    		notes = "Convert CSV page from cumulative to absolute",
    		responseClass = "String")
	@RequestMapping(value = "/convertCsvCumulativeToAbsolute", method = RequestMethod.GET)
	public void convertCsvCumulativeToAbsolute(
			@ApiParam("Confluence space of CSV")
			@RequestParam String csvSpace,
			@ApiParam("Confluence page with CSV")
			@RequestParam String csvTitle,
			HttpServletResponse response) throws IOException {

		String journalContent = ConfluenceOperations.getPageSource(confluenceConfig.SERVER,
				confluenceConfig.USER, confluenceConfig.PASS, csvSpace, csvTitle);
		
		BacklogJournalReader reader = new BacklogJournalReader();
		ArrayList<BacklogChunk> chunks = reader.readFromStringNullAllowed(journalContent);
		ArrayList<BacklogChunk> newChunks = cumulativeToAbsoluteConverter.convert(chunks);
		String newContent = backlogJournalSerializer.serialize(newChunks);

		
		response.setContentType("text/plain; charset=windows-1250");
		PrintWriter out = response.getWriter();
		out.println(newContent);
	}

    @ApiOperation(value = "Convert CSV page from absolute to cumulative",
    		notes = "Convert CSV page from absolute to cumulative",
    		responseClass = "String")
	@RequestMapping(value = "/convertCsvAbsoluteToCumulative", method = RequestMethod.GET)
	public void convertCsvAbsoluteToCumulative(
			@ApiParam("Confluence space of CSV")
			@RequestParam String csvSpace,
			@ApiParam("Confluence page with CSV")
			@RequestParam String csvTitle,
			HttpServletResponse response) throws IOException {

		String journalContent = ConfluenceOperations.getPageSource(confluenceConfig.SERVER,
				confluenceConfig.USER, confluenceConfig.PASS, csvSpace, csvTitle);
		
		BacklogJournalReader reader = new BacklogJournalReader();
		ArrayList<BacklogChunk> chunks = reader.readFromStringNullAllowed(journalContent);
		
		absoluteToCumulativeConverterInPlace.convertIntoCumulative(chunks);
		String newContent = backlogJournalSerializer.serialize(chunks);
		
		response.setContentType("text/plain; charset=windows-1250");
		PrintWriter out = response.getWriter();
		out.println(newContent);
	}

}
