package com.cas.portfolio.core;


import java.util.List;

import org.springframework.stereotype.Service;

import com.cas.portfolio.core.entity.Customer;
import com.cas.portfolio.web.model.Credential;

@Service
public interface CustomerService {
	public Customer createCustomer(Customer customer);
	public Customer updateCustomer(Customer customer);
	public int deleteCustomer(Customer customer);	
	public Customer authenticateCustomer(Credential credential);
	public Customer getCustomerByEmail(String email);
	public List<Customer> getAllCustomers();
}
