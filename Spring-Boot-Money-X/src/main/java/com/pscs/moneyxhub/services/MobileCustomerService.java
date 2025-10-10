/**
 * 
 */
package com.pscs.moneyxhub.services;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pscs.moneyxhub.entity.MobileCustomer;
import com.pscs.moneyxhub.repo.CustomerRepo;

/**
 * 
 */
@Service
public class MobileCustomerService {

	private final CustomerRepo customerRepo;

	public MobileCustomerService(CustomerRepo customerRepo) {
		this.customerRepo = customerRepo;
	}

	public MobileCustomer save(MobileCustomer mobilecustomer) {
		return customerRepo.save(mobilecustomer);
	}

	public MobileCustomer getById(Long Id) {
		return customerRepo.findById(Id).orElseThrow(() -> new RuntimeException("No Data Found By this Id" + Id));
	}

	public List<MobileCustomer> getAll() {
		return customerRepo.findAll();
	}

	public List<MobileCustomer> getSortByField(String field) {
		return customerRepo.findAll(Sort.by(Sort.Direction.DESC, field));
	}

	public List<MobileCustomer> getPageByPage(int offset, int page) {
		return customerRepo.findAll(PageRequest.of(offset, page)).getContent();
	}

	public List<MobileCustomer> getPageByPageAndSorted(int offset, int pageSize, String fileds) {
		return customerRepo.findAll(PageRequest.of(offset, pageSize).withSort(Sort.Direction.ASC, fileds)).getContent();
	}

	public List<MobileCustomer> savelist(List<MobileCustomer> list) {
		return customerRepo.saveAll(list);
	}
}
