package amelinium.builder;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class ModelBuilderImplTests {

	ModelBuilderImpl modelBuilder = new ModelBuilderImpl();
	
	@Test
	public void testRegex() {
		String str = "This is story 1 - 10sp";
		Pattern pattern = Pattern.compile("\\s*-\\s*(\\d|\\?)+sp");
		Matcher matcher = pattern.matcher(str);
		assertTrue(matcher.find());
	}

}
