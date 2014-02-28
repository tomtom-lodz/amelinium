package amelinium.builder;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;

import amelinium.model.Feature;
import amelinium.model.Project;
import amelinium.model.Release;
import amelinium.model.Story;

public class ModelBuilderImplTest {	
	
	private String projectBacklog;
	private Method method;
	private Element content;
	
	@Before
	public void setUp() throws NoSuchMethodException, SecurityException{
		projectBacklog = "<html>"+
						 "<head></head><body><div class='value'>"+
						 "<h3>Release</h3>"+
						 "<p>Feature</p>"+
						 "<ul>"+
						 "<li>Story1 - 10sp</li>"+
						 "<li>Story1 - 5sp</li>"+
						 "</ul>"+
						 "</div>"+
						 "</body>"+
						 "</html>";
		Class params[] = new Class[2];
		params[0] = Project.class;
		params[1] = Elements.class;
		method = ModelBuilderImpl.class.getDeclaredMethod("buildProject", params);
		method.setAccessible(true);
		Document doc = Jsoup.parse(projectBacklog);
		content = doc.getElementsByClass("value").get(0);
	}
	
	@Test
	public void testBuildProject() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		//given
		ModelBuilderImpl builder = new ModelBuilderImpl();
		Object args[] = new Object[2];
		args[0] = new Project();
		args[1] = content.children();
		
		//when
		Project project = (Project) method.invoke(builder, args);
		Release release = project.getLastRelease();
		Feature feature = release.getLastFeature();
		Story story = feature.getLastStory();
		
		//then
		assertEquals("Release - 0/0sp", release.getContent());
		assertEquals("Feature - 0/0sp", feature.getContent());
		assertEquals("Story1 - 5sp", story.getContent());
	}

}
