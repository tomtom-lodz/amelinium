package common;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import com.tomtom.amelinium.common.LineProducer;


public class LineProducerTest {
	@Test
	public void test() throws IOException {
		// given
		LineProducer producer = new LineProducer();
		
		// when
		ArrayList<String> lines = producer.readLinesFromFile("src/test/resources/common/modelSomeLines.txt");
		
		// then
		assertEquals(2,lines.size());
		assertEquals("aaa",lines.get(0));
		assertEquals("xxx",lines.get(1));
	}
}
