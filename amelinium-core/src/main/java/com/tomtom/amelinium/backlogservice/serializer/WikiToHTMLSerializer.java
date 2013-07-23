package com.tomtom.amelinium.backlogservice.serializer;

import java.io.StringWriter;

import net.java.textilej.parser.MarkupParser;
import net.java.textilej.parser.builder.HtmlDocumentBuilder;
import net.java.textilej.parser.markup.confluence.ConfluenceDialect;

public class WikiToHTMLSerializer {
	
	public String convert(String wikiMarkup){
	//	String result = "";
	       StringWriter writer = new StringWriter();

	        HtmlDocumentBuilder builder = new HtmlDocumentBuilder(writer);
	        builder.setEmitAsDocument(false);

	        MarkupParser parser = new MarkupParser(new ConfluenceDialect());
	        parser.setBuilder(builder);
	        parser.parse(wikiMarkup);

	        final String html = writer.toString();

	        //System.out.println(html);
        
		return html;
	}
	
//	public static void main(String[] args) {
//		String markup = "h3. xxxxx xxx - xxxxxxxx xxx xxxxx 25 / 90 sp\nh5. xxxxx xxx - xxxx hxxxx - xxx xxxxxxxx - 16 / 64 sp\n* _xxx-xxx (xxxxxx xxxh xxxxxxxx xxx xx xxxxxxxxxxx)_ - 5 sp\n{xxxxx:#808080}xx xxxx xxx xxxxxx xxxxxxxxxxxx xxx xxxxxxxxxx xx xxx xxxxxx xxxxxxxxxx xx xxxxxx xxxhxxxxxxxxxx. xhx xxxx xxxx xx xx xxxxxxxxx xhxx xx xxx xxxxxxxxxxxx xx xhx xxxx xxxhxxx.{xxxxx}";
//		convert(markup);
//	}
	
}
	