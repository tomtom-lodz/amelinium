package backlogservice.serializer;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.tomtom.amelinium.backlogservice.factory.BacklogServiceFactory;

public class ColumnsTest {
	private boolean allowingMultilineFeatures = true;

	@Test
	public void serializationTest() {
		// given
		String fileName = "src/test/resources/backlogservice/backlogs/columns.txt";
		BacklogServiceFactory factory = new BacklogServiceFactory();

		// when
		String content = factory.readCorrectAndSerializeModelFromFile(fileName,this.allowingMultilineFeatures);

		//then
		assertTrue(content.endsWith("{section}\n"));

	}

}
