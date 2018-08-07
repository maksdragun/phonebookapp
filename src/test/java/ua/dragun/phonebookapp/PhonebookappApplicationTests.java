package ua.dragun.phonebookapp;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PhonebookApplication.class)
@WebAppConfiguration
public class PhonebookappApplicationTests {

	@Test
	public void contextLoads() {
	}
}
