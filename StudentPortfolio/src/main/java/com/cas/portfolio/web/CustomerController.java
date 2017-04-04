package com.cas.portfolio.web;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cas.portfolio.core.CustomerService;
import com.cas.portfolio.core.entity.Customer;
import com.cas.portfolio.web.model.CustomerMapper;
import com.cas.portfolio.web.model.ShoppingCart;
import com.cas.portfolio.web.model.WebCustomer;

@Scope("session")
@Controller
public class CustomerController {
	public static final String PACKAGE_PATH = "CustomerManagement/";
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ShoppingCart shoppingCart;
	
	@Autowired
	private CustomerService customerService;

	@RequestMapping(value = "/registerCustomer", method = RequestMethod.GET)
	public String viewRegistrationPage(WebCustomer customer) {
		return PACKAGE_PATH + "customerForm";
	}

	@RequestMapping(value = "/registerCustomer", method = RequestMethod.POST)
	public String registerCustomer(@Valid WebCustomer webCustomer, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return PACKAGE_PATH + "customerForm";
		}

		Customer customer = CustomerMapper.webCustomerToCustomer(webCustomer);
		shoppingCart.addCustomer(customer);
		
		customer = customerService.createCustomer(customer);

		logger.info("Customer creaed: " + customer.getEmail());
		
		// retrieve the customer from DB
		customer = customerService.getCustomerByEmail(customer.getEmail());
		logger.info("Customer retrieved: " + customer.getEmail());
		
		model.addAttribute("customer", customer);
		return PACKAGE_PATH + "customerHome";
	}

	@RequestMapping(value = "/updateCustomer", method = RequestMethod.GET)
	public String viewUpdatePage(Customer customer, Model model) {
		
		String email = shoppingCart.getCustomers().get(0).getEmail();
		Customer currentCustomer = customerService.getCustomerByEmail(email);
		WebCustomer currentWebCustomer = CustomerMapper.customerToWebCustomer(currentCustomer);
		
		model.addAttribute("customer", currentWebCustomer);
		return PACKAGE_PATH + "updateCustomerForm";
	}

	@RequestMapping(value = "/updateCustomer", method = RequestMethod.POST)
	public String updateCustomer(@Valid WebCustomer webCustomer, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return PACKAGE_PATH + "updateCustomerForm";
		}

		Customer customer = CustomerMapper.webCustomerToCustomer(webCustomer);
		shoppingCart.reset();
		shoppingCart.addCustomer(customer);

		customer = customerService.updateCustomer(customer);
		logger.info("Customer updateted: " + customer.getEmail());
		
		model.addAttribute("customer", customer);
		return PACKAGE_PATH + "customerHome";
	}
	
	@RequestMapping(value = "/viewCustomer", method = RequestMethod.GET)
	public String viewCustomer(Model model) {
		String email = shoppingCart.getCustomers().get(0).getEmail();
		Customer customer = customerService.getCustomerByEmail(email);
		
		WebCustomer webCustomer = CustomerMapper.customerToWebCustomer(customer);
		logger.info("View Customer: "+ webCustomer.getEmail());
		
		model.addAttribute("customer", webCustomer);
		return PACKAGE_PATH + "viewCustomer";
	}
}