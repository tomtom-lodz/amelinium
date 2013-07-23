package backlogservice.difficult_cases;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import com.tomtom.amelinium.backlogservice.factory.BacklogServiceFactory;
import com.tomtom.amelinium.backlogservice.model.BacklogModel;
import com.tomtom.amelinium.backlogservice.model.Feature;

public class FeaturesTest {
	private boolean allowingMultilineFeatures = true;

	@Test
	public void test() {
		// given
		String fileName = "src/test/resources/backlogservice/difficult_cases/SixFreeFeaturesDifficultMarkup.txt";
		BacklogServiceFactory factory = new BacklogServiceFactory();

		// when
		BacklogModel backlogModel = factory.readBacklogModelFromFile(fileName,this.allowingMultilineFeatures);
		ArrayList<Feature> featuresUnderUntitledFG = backlogModel
				.getFirstSubProject().getFeatureGroups().get(0).getLastColumn()
				.getFeatures();

		// then
		assertEquals(6, featuresUnderUntitledFG.size());

		assertEquals("-Some Feature- \\- _", featuresUnderUntitledFG.get(0)
				.getContentLeft());
		assertEquals("2/5sp", featuresUnderUntitledFG.get(0).getContentMiddle());
		assertEquals("_", featuresUnderUntitledFG.get(0).getContentRight());

		assertEquals("-Some- {color:#ff0000}{-}Feature{-}{color} -\\-- ",
				featuresUnderUntitledFG.get(1).getContentLeft());
		assertEquals("5/?sp", featuresUnderUntitledFG.get(1).getContentMiddle());
		assertEquals("", featuresUnderUntitledFG.get(1).getContentRight());

		assertEquals("-Ddsds dsdsd - ", featuresUnderUntitledFG.get(2)
				.getContentLeft());
		assertEquals("2sp/5sp", featuresUnderUntitledFG.get(2)
				.getContentMiddle());
		assertEquals("-", featuresUnderUntitledFG.get(2).getContentRight());

		assertEquals("-asdfasda - ", featuresUnderUntitledFG.get(3)
				.getContentLeft());
		assertEquals("1sp/?sp", featuresUnderUntitledFG.get(3)
				.getContentMiddle());
		assertEquals("-", featuresUnderUntitledFG.get(3).getContentRight());

		assertEquals("{color:#ff0000}{-}asdfasda - ", featuresUnderUntitledFG
				.get(4).getContentLeft());
		assertEquals("1sp", featuresUnderUntitledFG.get(4).getContentMiddle());
		assertEquals("{-}{color}", featuresUnderUntitledFG.get(4)
				.getContentRight());

		assertEquals("{color:#ff0000}{-}asdfasda - ", featuresUnderUntitledFG
				.get(5).getContentLeft());
		assertEquals("?sp", featuresUnderUntitledFG.get(5).getContentMiddle());
		assertEquals("{-}{color}", featuresUnderUntitledFG.get(5)
				.getContentRight());

	}

}
