package com.pscs.moneyx.services;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pscs.moneyx.entity.MobileCustomer;
import com.pscs.moneyx.repo.CustomerRepo;
import com.pscs.moneyx.services.MobileCustomerService;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

	@Mock
	private CustomerRepo customerRepo;
	@InjectMocks
	private MobileCustomerService customerService;
	
	@Test
	public void saveUser() {
		
		//arrange 
		MobileCustomer customer=MobileCustomer.builder().firstName("Dipak")
				.lastName("Kumar").mail("Dipak@gmail.com").phone("123456789")
				.dob("15011992").id(99L).build();
		
//		act
		when(customerRepo.save(Mockito.any(MobileCustomer.class))).thenReturn(customer);
		MobileCustomer save = customerService.save(customer);
		
		//assert 
		Assertions.assertNotNull(save);
		Assertions.assertEquals(customer.getId(),save.getId());
		Assertions.assertEquals(customer.getFirstName(), save.getFirstName());
		
		
		
	}
	@Test
	public void getbyUserid() {
		//arrange 
		MobileCustomer customer=MobileCustomer.builder().firstName("Dipak")
				.lastName("Kumar").mail("Dipak@gmail.com").phone("123456789")
				.dob("15011992").id(99L).build();
		
		//act 
		when(customerRepo.save(Mockito.any(MobileCustomer.class))).thenReturn(customer);
		when(customerRepo.findById(customer.getId())).thenReturn(Optional.of(customer));
		 customerService.save(customer);
		MobileCustomer byId = customerService.getById(customer.getId());
		//Assert
		
		Assertions.assertNotNull(byId);
		Assertions.assertEquals(customer.getId(), byId.getId());
		Assertions.assertEquals(customer.getFirstName(), byId.getFirstName());
		
		
	}
	
	@Test
	public void getall() {
		
		//arrange 
		MobileCustomer customer=MobileCustomer.builder().firstName("Dipak")
				.lastName("Kumar").mail("Dipak@gmail.com").phone("123456789")
				.dob("15011992").id(99L).build();
		MobileCustomer customer2=MobileCustomer.builder().firstName("Dipak")
				.lastName("Kumar").mail("Dipak@gmail.com").phone("123456789")
				.dob("15011992").id(109L).build();
		List<MobileCustomer> list=List.of(customer,customer2);
		
		//act
		when(customerRepo.saveAll(Mockito.anyList())).thenReturn(list);
		when(customerRepo.findAll()).thenReturn(list);
		
		customerService.savelist(list);
		List<MobileCustomer> all = customerService.getAll();
		
		Assertions.assertNotNull(all);
		Assertions.assertEquals(all.size(), list.size());
		
		
	}
	
	
	@Test
	public void MobileCustomer_getAllCustomer_returnCustomer() {
		//arrange 
		Page<MobileCustomer> page =Mockito.mock(Page.class);
	//act
		when(customerRepo.findAll(Mockito.any(Pageable.class))).thenReturn(page);
		
		List<MobileCustomer> pageByPage = customerService.getPageByPage(0, 4);
		//assert
		
		Assertions.assertNotNull(pageByPage);
		
		
	}
	}
