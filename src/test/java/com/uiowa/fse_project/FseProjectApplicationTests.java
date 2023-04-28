package com.uiowa.fse_project;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FseProjectApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	// register patient from home page test
	@Test
	public void testPatientRegistration() throws Exception {
		mockMvc.perform(post("/createUser")
				.param("firstName", "John")
				.param("lastName", "Doe")
				.param("email", "john.doe@example.com")
				.param("password", "password")
				.param("confirmPassword", "password"))
				.andExpect(status().is3xxRedirection());
		}

	// log in as existing patient test
	@Test
	public void testExistingPatientLogin() throws Exception {
		// create new patient account
		mockMvc.perform(post("/createUser")
				.param("firstName", "Jane")
				.param("lastName", "Doe")
				.param("email", "jane.doe@example.com")
				.param("role", "ROLE_PATIENT")
				.param("password", "pat")
				.param("confirmPassword", "pat"))
				.andExpect(status().is3xxRedirection());

		// log out the newly registered patient
		mockMvc.perform(post("/logout"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/"));

		// log in as existing patient
		mockMvc.perform(post("/signin")
				.param("email", "jane.doe@example.com")
				.param("password", "pat"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("patient/"));
	}

}
