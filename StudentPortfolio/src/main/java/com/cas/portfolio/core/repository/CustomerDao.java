package com.cas.portfolio.core.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cas.portfolio.core.entity.Customer;

@Repository
public interface CustomerDao {
	public Customer createCustomer(Customer customer);
	public int deleteCustomer(Customer customer);
	public Customer getCustomerByEmailAndPassword(String email, String password);
	public Customer getCustomerByEmail(String email);
	public List<Customer> getAllCustomers();
	public int update(Customer customer);
}
