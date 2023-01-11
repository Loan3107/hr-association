package com.openclassrooms.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.api.model.Employee;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployerControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testGetAll() throws Exception {
		mockMvc.perform(get("/employees")).andExpect(status().isOk());
	}
	
	@Test
	public void testGetById() throws Exception {
		// We're ensuring we have an employee
		mockMvc.perform(
			post("/employees")
			.content(asJsonString(new Employee("Loan", "PIROTAIS", "loan.pirotais@cgi.com", "toto")))
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
		);
		
		mockMvc.perform(
			get("/employees/{id}", 1)
			.accept(MediaType.APPLICATION_JSON)
		)
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
		
		mockMvc.perform(
			get("/employees/{id}", 2)
			.accept(MediaType.APPLICATION_JSON)
		)
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void testCreate() throws Exception {
		mockMvc.perform(
			post("/employees")
			.content(asJsonString(new Employee("Loan", "PIROTAIS", "loan.pirotais@cgi.com", "toto")))
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
		)
		.andExpect(status().isCreated())
		.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
		.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2));
	}
	
	@Test
	public void testDelete() throws Exception {
		// We're ensuring we have an employee
		mockMvc.perform(
			post("/employees")
			.content(asJsonString(new Employee("Loan", "PIROTAIS", "loan.pirotais@cgi.com", "toto")))
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
		);
		
		mockMvc.perform(delete("/employees/{id}", 3)).andExpect(status().isNoContent());
		
		mockMvc.perform(delete("/employees/{id}", 4)).andExpect(status().isNotFound());
	}
	
	private static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
