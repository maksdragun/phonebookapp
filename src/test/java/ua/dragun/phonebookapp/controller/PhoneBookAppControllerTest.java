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
import ua.dragun.phonebookapp.service.enums.Status;
import ua.dragun.phonebookapp.service.loginservice.LoginService;
import ua.dragun.phonebookapp.service.phonebookappservice.PhoneBookAppService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class PhoneBookAppControllerTest {
	
	@Mock
	private LoginService loginService;

	@Mock
	private PhoneBookAppService phoneBookAppService;

	@InjectMocks
	private PhoneBookAppController phoneBookAppController;
	
	private MockMvc mockMvc;
	
	@BeforeEach
	public void initialization() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(phoneBookAppController).build();
	}
	
	@Test
	public void testPage() throws Exception {
		mockMvc.perform(get("/phoneBookApp")).andExpect(view().name("redirect:/"));
	}
	
	@Test
	public void testPageWithAttributes() throws Exception {
		User user = new User("testUsername", "testPassword", "testFullName");
		user.getContacts().add(new Contact("surname", "firstName", "patronymic", "mobilePhone", "homePhone", "address", "email", user));
		
		when(loginService.findByUsername("testUsername")).thenReturn(user);
		
		mockMvc.perform(get("/phoneBookApp").sessionAttr("username", "testUsername"))
				.andExpect(view().name("phonebook"))
				.andExpect(model().attribute("username", "testUsername"))
				.andExpect(model().attribute("contacts", user.getContacts()));
	}

	@Test
	public void removePage() throws Exception {
		User user = new User("testUsername", "testPassword", "testFullName");
		Integer id = 100;

		when(phoneBookAppService.remove(id, user.getUsername())).thenReturn(id);

		mockMvc.perform(post("/phoneBookApp/delete")
				.sessionAttr("username", "testUsername")
				.param("contactId", "100"))
				.andExpect(content().string("100"));
	}
	@Test
	public void signOut() throws Exception {
		mockMvc.perform(get("/logOut").sessionAttr("username", "testUsername"))
				.andExpect(view().name("redirect:/"));
	}

	@Test
	public void pageView() throws Exception {
		mockMvc.perform(get("/phoneBookApp/add"))
				.andExpect(view().name("redirect:/"));
	}

	@Test
	public void pageViewWithAttributes() throws Exception {
		User user = new User("testUsername", "testPassword", "testFullName");

		when(loginService.findByUsername("testUsername")).thenReturn(user);

		mockMvc.perform(get("/phoneBookApp/add").sessionAttr("username", "testUsername"))
				.andExpect(view().name("adds"))
				.andExpect(model().attribute("username", "testUsername"));
	}

	@Test
	public void testSurnameNewContact() throws Exception {
		User user = new User("testUsername", "testPassword", "testFullName");

		when(loginService.findByUsername("testUsername")).thenReturn(user);
		when(phoneBookAppService.add(any(Contact.class), eq(user.getUsername()))).thenReturn(Status.INCORRECT_SURNAME);

		mockMvc.perform(post("/phoneBookApp/addNew")
				.sessionAttr("username", "testUsername")
				.param("surname", "aa")
				.param("firstName", "firstName")
				.param("patronymic", "patronymic")
				.param("mobilePhone", "+380(97)1234567")
				.param("homePhone", "+380(97)1234567")
				.param("address", "address")
				.param("email", ""))
				.andExpect(view().name("redirect:/phoneBookApp/add"))
				.andExpect(flash().attribute("message", "Surname must contain at least 4 symbols"));
	}

	@Test
	public void testFirstNameNewContact() throws Exception {
		User user = new User("testUsername", "testPassword", "testFullName");

		when(loginService.findByUsername("testUsername")).thenReturn(user);
		when(phoneBookAppService.add(any(Contact.class), eq(user.getUsername()))).thenReturn(Status.INCORRECT_FIRSTNAME);

		mockMvc.perform(post("/phoneBookApp/addNew")
				.sessionAttr("username", "testUsername")
				.param("surname", "aa")
				.param("firstName", "firstName")
				.param("patronymic", "patronymic")
				.param("mobilePhone", "+380(97)1234567")
				.param("homePhone", "+380(97)1234567")
				.param("address", "address")
				.param("email", ""))
				.andExpect(view().name("redirect:/phoneBookApp/add"))
				.andExpect(flash().attribute("message", "First Name must contain at least 4 symbols"));
	}

	@Test
	public void testMobileNameNewContact() throws Exception {
		User user = new User("testUsername", "testPassword", "testFullName");

		when(loginService.findByUsername("testUsername")).thenReturn(user);
		when(phoneBookAppService.add(any(Contact.class), eq(user.getUsername()))).thenReturn(Status.INCORRECT_MOBILE);

		mockMvc.perform(post("/phoneBookApp/addNew")
				.sessionAttr("username", "testUsername")
				.param("surname", "surname")
				.param("firstName", "firstName")
				.param("patronymic", "patronymic")
				.param("mobilePhone", "+380(97)123-45-67")
				.param("homePhone", "+380(97)123-45-67")
				.param("address", "address")
				.param("email", ""))
				.andExpect(view().name("redirect:/phoneBookApp/add"))
				.andExpect(flash().attribute("message", "Mobile phone must have format: +XXX(XX)XXX-XX-XX"));
	}

	@Test
	public void testEditPage() throws Exception {
		mockMvc.perform(get("/phoneBookApp/edit/5"))
				.andExpect(view().name("redirect:/"));
	}

	@Test
	public void testEditPageWithAttributes() throws Exception {
		User user = new User("testUsername", "testPassword", "testFullName");

		when(loginService.findByUsername("testUsername")).thenReturn(user);

		mockMvc.perform(get("/phoneBookApp/edit/5")
				.sessionAttr("username", "testUsername"))
				.andExpect(view().name("edits"))
				.andExpect(model().attribute("username", "testUsername"));
	}

}
