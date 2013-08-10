package com.tomtom.woj.amelinium;

import java.io.IOException;

import com.tomtom.woj.amelinium.utils.StringUtils;

public class Main {

	public static void main(String[] args) throws IOException {
		
//		String csv = StringUtils.readFile("src/test/resources/backlog_journals/example2_cumulative.txt");
//		String out = PlotHtmlPageGenerator.createHtmlPageWithPlots(csv,true,8,2,6);
		
		String csv = StringUtils.readFile("src/test/resources/absolute_removing_tests/san_product_backlog.txt");
		String out = PlotHtmlPageGenerator.createHtmlPageWithPlots(csv,false,8,2,6);
		
		
		StringUtils.writeFile(out, "src/main/resources/jqplot/examples/woj4.html");
	}

}
