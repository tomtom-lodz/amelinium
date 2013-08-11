package com.tomtom.woj.amelinium.journal.timelapse;

import java.io.IOException;

import com.tomtom.woj.amelinium.utils.StringUtils;

public class Main {
	
	public static void main(String[] args) throws IOException {
		
		boolean isCumulative = false;
		double dailyVelocity = 9;
		double dailyBlackMatter = 0;
		
		String input = StringUtils.readFile("src/test/resources/absolute_removing_tests/san_product_backlog.txt");
		
		TimeLapseStringBuilder aaa = new TimeLapseStringBuilder();
		String output = aaa.createTimeLapseString(input, dailyVelocity, dailyBlackMatter,
				isCumulative);
		
		System.out.println(output);
		
	}

}
