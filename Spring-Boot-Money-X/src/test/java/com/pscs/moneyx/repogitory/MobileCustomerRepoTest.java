/**
 * 
 */
package com.pscs.moneyx.repogitory;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.pscs.moneyx.entity.MobileCustomer;
import com.pscs.moneyx.repo.CustomerRepo;

/**
 * 
 */
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class MobileCustomerRepoTest {

	@Autowired
	private CustomerRepo customerRepo;
	
	@Test
	public void saveCustomerTest() {
		//arrange 
		MobileCustomer customer=new MobileCustomer();
		customer.setDob("15011992");
//		customer.setId(23L);
		customer.setFirstName("Dipak Kumar");
		customer.setLastName("Kumar");
		customer.setGender("MALE");
		customer.setMail("kumardip@gmail.com");
		customer.setPhone("32435466576");
		
		
		//act
		MobileCustomer save = customerRepo.save(customer);
		
		
		
		
		//assert
		
		Assertions.assertNotNull(save.getId());
		
		Assertions.assertEquals("Dipak Kumar", save.getFirstName());
		
		
	}
	@Test
	public void saveCustomerAll() {
		//arrange 
		MobileCustomer customer=new MobileCustomer();
		customer.setDob("15011992");
//		customer.setId(23L);
		customer.setFirstName("Dipak Kumar");
		customer.setLastName("Kumar");
		customer.setGender("MALE");
		customer.setMail("kumardip@gmail.com");
		customer.setPhone("32435466576");
		
		
		
		//arrange 
		MobileCustomer customer1=new MobileCustomer();
		customer1.setDob("15011992");
//		customer1.setId(23L);
		customer1.setFirstName("Dipak Kumar");
		customer1.setLastName("Kumar");
		customer1.setGender("MALE");
		customer1.setMail("kumardip@gmail.com");
		customer1.setPhone("32435466576");
		
		
		//act
		 customerRepo.save(customer);
		 customerRepo.save(customer1);
		 
		 List<MobileCustomer> all = customerRepo.findAll();
			//assert
		 Assertions.assertNotNull(all);
		 Assertions.assertEquals(2, all.size());
		 
		
		
		
	}
	@Test
	public void getCustomerById() {
		
		//arrange
		MobileCustomer customer=new MobileCustomer();
		customer.setDob("15011992");
//		customer.setId(23L);
		customer.setFirstName("Dipak Kumar");
		customer.setLastName("Kumar");
		customer.setGender("MALE");
		customer.setMail("kumardip@gmail.com");
		customer.setPhone("32435466576");
		
		//act
		 MobileCustomer save = customerRepo.save(customer);
		 MobileCustomer customerdata = customerRepo.findById(save.getId()).orElseThrow(()->new RuntimeException("Data not found by this id "));
		 
		
		//
		//assert
		Assertions.assertNotNull(customer);
		Assertions.assertEquals(save.getId(), customerdata.getId());
		
	}
	@Test
	public void testFindByPhone() {
		
		//arrange
		MobileCustomer customer=new MobileCustomer();
		customer.setDob("15011992");
//		customer.setId(23L);
		customer.setFirstName("Dipak Kumar");
		customer.setLastName("Kumar");
		customer.setGender("MALE");
		customer.setMail("kumardip@gmail.com");
		customer.setPhone("32435466576");
		
		//act
		 MobileCustomer save = customerRepo.save(customer);
		 
		 MobileCustomer byPhone = customerRepo.findByPhone(customer.getPhone()).get();
		 //assert
		 Assertions.assertNotNull(byPhone);
		 Assertions.assertEquals(customer.getPhone(), byPhone.getPhone());
		 
		
	}
	
	
	@Test
	public void updatebyPhone() {

		//arrange
		MobileCustomer customer=new MobileCustomer();
		customer.setDob("15011992");
//		customer.setId(23L);
		customer.setFirstName("Dipak Kumar");
		customer.setLastName("Kumar");
		customer.setGender("MALE");
		customer.setMail("kumardip@gmail.com");
		customer.setPhone("32435466576");
		
		//act
		 MobileCustomer save = customerRepo.save(customer);
		 
		 MobileCustomer mobileCustomer = customerRepo.findById(customer.getId()).get();
		 
		 mobileCustomer.setPhone("6301655736");
		 MobileCustomer save2 = customerRepo.save(mobileCustomer);
		 
		 //assert
		 
		 
		 Assertions.assertNotNull(save2);
		 Assertions.assertEquals(mobileCustomer.getPhone(), save2.getPhone());
		 
		
	}
	
	@Test
	public void deleteById() {
		//arrange
		MobileCustomer customer=new MobileCustomer();
		customer.setDob("15011992");
//		customer.setId(23L);
		customer.setFirstName("Dipak Kumar");
		customer.setLastName("Kumar");
		customer.setGender("MALE");
		customer.setMail("kumardip@gmail.com");
		customer.setPhone("32435466576");
		
		//act
		 MobileCustomer save = customerRepo.save(customer);
		 
		 customerRepo.deleteById(customer.getId());
		 
		 Optional<MobileCustomer> byId = customerRepo.findById(save.getId());
		 //assert
		 
		 
		 
		 Assertions.assertNotNull(byId);
		 Assertions.assertEquals(byId.isEmpty(), true);
		 
		 
	}
	
	
}
