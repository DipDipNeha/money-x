package com.pscs.moneyx.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.assertj.MvcTestResultAssert;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pscs.moneyx.controller.CustomerBusinessController;
import com.pscs.moneyx.entity.MobileCustomer;
import com.pscs.moneyx.services.MobileCustomerService;

@WebMvcTest(controllers = CustomerBusinessController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	
	@MockBean
	private MobileCustomerService customerService;
	@Autowired
	private ObjectMapper mapper;
	@Test
	public void CustomerController_createCustomer() throws JsonProcessingException, Exception {
		
		//arrange 
		MobileCustomer customer=MobileCustomer.builder().firstName("Dipak")
				.lastName("Kumar").mail("Dipak@gmail.com").phone("123456789")
				.dob("15011992").id(99L).build();
		

        given(customerService.save(ArgumentMatchers.any(MobileCustomer.class))).willReturn(customer);

		ResultActions actions=mockMvc.perform(post("/api/customer/save")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(customer))
				);
		
		actions.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	
}
