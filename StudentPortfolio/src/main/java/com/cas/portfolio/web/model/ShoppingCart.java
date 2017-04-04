package com.cas.portfolio.web.model;

import java.util.ArrayList;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.cas.portfolio.core.entity.Customer;

@Component
@Scope(value="session")
public class ShoppingCart {
	private ArrayList<Customer> customers;
	
	public ShoppingCart() {
		customers = new ArrayList<Customer>();
	}
	public void addCustomer(Customer customer) {
		customers.add(customer);
	}

	public ArrayList<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(ArrayList<Customer> customers) {
		this.customers = customers;
	}
	public void reset() {
		customers.clear();
	}
}
