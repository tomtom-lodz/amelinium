package com.tomtom.woj.amelinium.utils;

import java.util.HashMap;
import java.util.Map.Entry;

public class TemplateRenderer {

	public static String render(String templateFileName, HashMap<String, String> model) {
		String out = StringUtils.readResource(templateFileName);
		
		for(Entry<String, String> e : model.entrySet()) {
			out = out.replace(e.getKey(), e.getValue());
		}
		
		return out;
	}
	
}
