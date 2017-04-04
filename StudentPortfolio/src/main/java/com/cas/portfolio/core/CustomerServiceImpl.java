package com.cas.portfolio.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cas.portfolio.core.entity.Customer;
import com.cas.portfolio.core.repository.CustomerDao;
import com.cas.portfolio.web.model.Credential;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDao customerDao;

	@Override
	public Customer createCustomer(Customer customer) {
		return customerDao.createCustomer(customer);
	}

	@Override
	public Customer updateCustomer(Customer customer) {
		customerDao.deleteCustomer(customer);
		Customer newCustomer = customerDao.createCustomer(customer);
		return newCustomer;
	}
	
	@Override
	public int deleteCustomer(Customer customer) {
		customerDao.deleteCustomer(customer);
		return 1;
	}

	@Override
	public Customer authenticateCustomer(Credential credential) {
		String email = credential.getUserName();
		String password = credential.getPassword();
		Customer customer = customerDao.getCustomerByEmailAndPassword(email, password);
		
		return customer;		
	}
	
	@Override
	public Customer getCustomerByEmail(String email) {
		Customer result = customerDao.getCustomerByEmail(email);
		return result;
	}

	@Override
	public List<Customer> getAllCustomers() {
		List<Customer> result = customerDao.getAllCustomers();
		return result;
	}	
}
