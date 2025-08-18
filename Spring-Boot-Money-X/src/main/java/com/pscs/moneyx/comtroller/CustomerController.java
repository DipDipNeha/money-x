/**
 * 
 */
package com.pscs.moneyx.comtroller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pscs.moneyx.entity.MobileCustomer;
import com.pscs.moneyx.entity.UserEntity;
import com.pscs.moneyx.model.RequestData;
import com.pscs.moneyx.services.MobileCustomerService;

/**
 * 
 */
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

	private final MobileCustomerService mobileCustomerService;

	public CustomerController(MobileCustomerService mobileCustomerService) {
		this.mobileCustomerService = mobileCustomerService;
	}

	// echo
	@GetMapping("/echo")
	public ResponseEntity<String> echo() {
		return new ResponseEntity<>("Welcome to PSCS", HttpStatus.OK);
	}
	@PostMapping("/save")
	public ResponseEntity<MobileCustomer> save(@RequestBody RequestData requestBody) {
		ObjectMapper mapper = new ObjectMapper();
		MobileCustomer customer = mapper.convertValue(requestBody.getJbody(), MobileCustomer.class);
		
		MobileCustomer save = mobileCustomerService.save(customer);
		return new ResponseEntity<MobileCustomer>(save, HttpStatus.OK);
	}

	

	@GetMapping("/getbyid/{id}")
	public ResponseEntity<MobileCustomer> getByUserId(@PathVariable Long id) {
		MobileCustomer mobsus = mobileCustomerService.getById(id);

		return new ResponseEntity<MobileCustomer>(mobsus, HttpStatus.OK);

	}

	@GetMapping("/getall")
	public ResponseEntity<List<MobileCustomer>> getAll() {
		List<MobileCustomer> all = mobileCustomerService.getAll();
		return new ResponseEntity<List<MobileCustomer>>(all, HttpStatus.OK);
	}

	@GetMapping("/getall/{field}")
	public ResponseEntity<List<MobileCustomer>> getSortbyFiled(@PathVariable String field) {

		List<MobileCustomer> sortByField = mobileCustomerService.getSortByField(field);
		return new ResponseEntity<List<MobileCustomer>>(sortByField, HttpStatus.OK);
	}

	@GetMapping("/getbypage/{offset}/{pagesize}")
	public ResponseEntity<List<MobileCustomer>> getPageByPage(@PathVariable int offset, @PathVariable int pagesize) {
		List<MobileCustomer> pageByPage = mobileCustomerService.getPageByPage(offset, pagesize);
		return new ResponseEntity<List<MobileCustomer>>(pageByPage, HttpStatus.OK);
	}

	@GetMapping("/getbypagesorted/{offset}/{pagesize}/{field}")
	public ResponseEntity<List<MobileCustomer>> getPagingAndSorting(@PathVariable int offset,
			@PathVariable int pagesize, @PathVariable String field) {
		List<MobileCustomer> pageByPageAndSorted = mobileCustomerService.getPageByPageAndSorted(offset, pagesize,
				field);
		return new ResponseEntity<List<MobileCustomer>>(pageByPageAndSorted, HttpStatus.OK);
	}
}
