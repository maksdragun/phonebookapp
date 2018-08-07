package ua.dragun.phonebookapp.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class MainControllerTest {
	
	@InjectMocks
	private MainController mainController;
	
	private MockMvc mockMvc;
	
	@BeforeEach
	public void initialization(){
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(mainController).build();
	}
	
	@Test
	public void testWithNoSession() throws Exception {
		mockMvc.perform(get("/")).andExpect(view().name("index"));
	}
	
	@Test
	public void testWithSession() throws Exception {
		mockMvc.perform(get("/")
				.sessionAttr("username", "username"))
				.andExpect(view().name("redirect:/phoneBookApp"));
	}
}
