package ua.dragun.phonebookapp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.dragun.phonebookapp.entity.User;
import ua.dragun.phonebookapp.service.enums.Status;
import ua.dragun.phonebookapp.service.loginservice.LoginService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class LoginControllerTest {

	@Mock
	private LoginService loginService;
	
	@InjectMocks
	private LoginController loginController;
	
	private MockMvc mockMvc;
	
	@BeforeEach
	public void initialization() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
	}
	
	@Test
	public void signIn() throws Exception {
		User user = new User("testUsername", "testPassword", "testFullName");
		
		when(loginService.findByUsername("testUsername")).thenReturn(user);
		
		mockMvc.perform(post("/signIn")
						.sessionAttr("username", "testUsername")
						.param("username", "testUsername")
						.param("password", "testPassword"))
						.andExpect(view().name("redirect:/phoneBookApp"));
	}
	
	@Test
	public void registration() throws Exception {
		User user = new User("testUsername", "testPassword", "testFullName");
		
		when(loginService.create(user.getUsername(),
				                 user.getPassword(),
								 user.getFullName())).thenReturn(Status.SUCCESS);
		
		mockMvc.perform(post("/registration")
				.sessionAttr("username", "testUsername")
				.param("username", "testUsername")
				.param("password", "testPassword")
				.param("fullName", "testFullName"))
				.andExpect(view().name("redirect:/phoneBookApp"));
	}
}
