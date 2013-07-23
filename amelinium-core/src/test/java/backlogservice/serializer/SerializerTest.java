package backlogservice.serializer;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.tomtom.amelinium.backlogservice.config.MarkupConfig;
import com.tomtom.amelinium.backlogservice.factory.BacklogServiceFactory;

public class SerializerTest {
	private boolean allowingMultilineFeatures = true;

	@Test
	public void testOneFreeStory_OneFreeFeature() throws IOException {
		// given
		BacklogServiceFactory factory = new BacklogServiceFactory();
		String fileName = "src/test/resources/backlogservice/serializer/modelOneFreeStory_OneFreeFeature.txt";

		// when
		String result = factory.readCorrectAndSerializeModelFromFile(fileName,this.allowingMultilineFeatures);
		// then
		assertEquals(MarkupConfig.WARNING_LINE
				+ "* One Story - 1sp\n\n-One Feature- - 0/0sp\n", result);

	}

	@Test
	public void testOneFeatureGroupNoFeatureThreeStories() throws IOException {
		// given
		BacklogServiceFactory factory = new BacklogServiceFactory();
		String fileName = "src/test/resources/backlogservice/serializer/modelOneFeatureGroupNoFeatureThreeStories.txt";

		// when
		String result = factory.readCorrectAndSerializeModelFromFile(fileName,this.allowingMultilineFeatures);

		// then
		assertEquals(
				MarkupConfig.WARNING_LINE
						+ "\n\nh3. -AAA - 5/5sp-\n* -aaa - 2sp-\n* -bbb - 1sp-\n* -ccc - 2sp-\n",
				result);

	}

	@Test
	public void testOneFeatureGroupNoFeatureThreeStories_UncertainPoints()
			throws IOException {
		// given
		BacklogServiceFactory factory = new BacklogServiceFactory();
		String fileName = "src/test/resources/backlogservice/serializer/modelOneFeatureGroupNoFeatureThreeStories_UncertainPoints.txt";

		// when
		String result = factory.readCorrectAndSerializeModelFromFile(fileName,this.allowingMultilineFeatures);
		// then
		assertEquals(
				MarkupConfig.WARNING_LINE
						+ "\n\nh3. -AAA - 5/5sp-\n* -aaa - 2?sp-\n* -bbb - 1?sp-\n* -ccc - 2sp-\n",
				result);

	}
}
