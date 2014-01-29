package amelinium1.grails;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CoreServiceTests {

	private CoreService coreService = new CoreService();
	
	private static String oldContent;
	private static String csvText;
	private static String backlogText;
	private static String serializedCsv;
	
	
	@BeforeClass
	public static void setUp() throws Exception {
		oldContent = "BACKLOG - do not remove this line (needed for automatic recalculation)\n"+
					  "h3. Test backlog - 0/0sp\n"+
					  "* Write tests for backlog - 1sp\n"+
					  "* Write more tests - 1sp";
		backlogText = "BACKLOG - do not remove this line (needed for automatic recalculation)\n\n\n"+
					  "h3. Test backlog - 0/2sp\n"
					  +"* Write tests for backlog - 1sp\n"+
					  "* Write more tests - 1sp\n";
		
		csvText = "Date,Burned,Test backlog\n"+"2014-01-28,0.0,2.0\n";
		serializedCsv = "<p>Date,Burned,Test backlog<br/>2014-01-28,0.0,2.0</p>";
	}

	@Test
	public void testRecalculateCsv() {
		assertEquals(csvText.substring(0, 25), coreService.recalculateCsv(backlogText, csvText, true, true, true, true).substring(0, 25));
	}

	@Test
	public void testRecalculateBacklog() {
		assertEquals(backlogText, coreService.recalculateBacklog(oldContent));
		
	}
	@Test
	public void testCreateCsvChartAndTable() {
		String[] html = coreService.createCsvChartAndTable(csvText, false, 0.71, 0.071, "char1");
		
		//html[0] - chart, html[1] - burnuptable
		assertEquals(true, html[0].length()>0);
		assertEquals(true, html[1].length()>0);
	}
	@Test
	public void testSerializeText() {
		assertEquals(serializedCsv.substring(0, 32), coreService.serializeText(csvText).substring(0, 32));
	}
}
