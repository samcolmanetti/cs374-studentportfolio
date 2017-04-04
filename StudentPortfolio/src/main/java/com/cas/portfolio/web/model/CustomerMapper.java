package com.cas.portfolio.web.model;

import com.cas.portfolio.core.entity.Customer;

/**
 * @author Bi
 *
 */
public class CustomerMapper {
	public static Customer webCustomerToCustomer(WebCustomer webCustomer) {
		Customer result = new Customer();
		result.setEmail(webCustomer.getEmail());
		result.setPassword(webCustomer.getPassword());
		result.setAge(webCustomer.getAge());
		
		return result;		
	}
	public static WebCustomer customerToWebCustomer(Customer customer) {
		WebCustomer result = new WebCustomer();
		result.setEmail(customer.getEmail());
		result.setPassword(customer.getPassword());
		result.setConfPassword(customer.getPassword());
		result.setAge(customer.getAge());
		
		return result;		
	}
}
