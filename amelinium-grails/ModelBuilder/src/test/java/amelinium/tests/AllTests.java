package amelinium.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import amelinium.builder.ModelBuilderImplTest;
import amelinium.converter.NewToLegacyModelConverterImplTest;
import amelinium.recalculator.ProjectRecalculatorImplTest;
import amelinium.serializer.ModelToHtmlSerializerImplTest;

@RunWith(Suite.class)
@SuiteClasses({ ModelBuilderImplTest.class,
		NewToLegacyModelConverterImplTest.class,
		ProjectRecalculatorImplTest.class,
		ModelToHtmlSerializerImplTest.class })
public class AllTests {

}
