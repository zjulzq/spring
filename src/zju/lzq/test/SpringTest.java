package zju.lzq.test;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import zju.lzq.service.PersonService;

public class SpringTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@Test
	public void instanceSpring() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		PersonService personService = (PersonService) ctx
				.getBean("personService");

		personService.save();

	}

	// @Test
	public void instanceSpring2() {
		ItcastClassPathXMLApplicationContext ctx = new ItcastClassPathXMLApplicationContext(
				"beans.xml");
		PersonService personService = (PersonService) ctx
				.getBean("personService");

		personService.save();
	}

}
