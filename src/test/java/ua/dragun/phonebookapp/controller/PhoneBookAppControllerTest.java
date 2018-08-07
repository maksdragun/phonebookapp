package ua.dragun.phonebookapp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.dragun.phonebookapp.entity.Contact;
import ua.dragun.phonebookapp.entity.User;
import ua.dragun.phonebookapp.service.loginservice.LoginService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class PhoneBookAppControllerTest {
	
	@Mock
	private LoginService loginService;

	@InjectMocks
	private PhoneBookAppController phoneBookAppController;
	
	private MockMvc mockMvc;
	
	@BeforeEach
	public void initialization() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(phoneBookAppController).build();
	}
	
	@Test
	public void testWithNoSession() throws Exception {
		mockMvc.perform(get("/phoneBookApp")).andExpect(view().name("redirect:/"));
	}
	
	@Test
	public void testWithSession() throws Exception {
		User user = new User("testUsername", "testPassword", "testFullName");
		user.getContacts().add(new Contact("surname", "firstName", "patronymic", "mobilePhone", "homePhone", "address", "email", user));
		
		when(loginService.findByUsername("testUsername")).thenReturn(user);
		
		mockMvc.perform(get("/phoneBookApp").sessionAttr("username", "testUsername"))
				.andExpect(view().name("phonebook"))
				.andExpect(model().attribute("username", "testUsername"))
				.andExpect(model().attribute("contacts", user.getContacts()));
	}
}
