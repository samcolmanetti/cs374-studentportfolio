package com.cas.portfolio.web;

import javax.servlet.http.HttpSession;
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
import com.cas.portfolio.web.model.Credential;
import com.cas.portfolio.web.model.ShoppingCart;

@Scope("session")
@Controller
public class AuthenticationController {
	public static final String PACKAGE_PATH = "AuthenticationManagement/";
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ShoppingCart shoppingCart;
	
	@Autowired
	private CustomerService customerService;
	@RequestMapping(value={"", "/", "login"}, method = RequestMethod.GET)
	public String login(Credential  credential) {
		return PACKAGE_PATH + "loginForm";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String authenticate(@Valid Credential  credential, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return PACKAGE_PATH +  "loginForm";
		}

		Customer customer = customerService.authenticateCustomer(credential);
		if (customer == null) {
			return PACKAGE_PATH + "loginForm";
		}
		
		logger.info("Customer creaed: " + customer.getEmail());
		
		shoppingCart.addCustomer(customer);
		
		model.addAttribute("customer", customer);
		return CustomerController.PACKAGE_PATH + "customerHome";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logout(HttpSession session, Model model) {
		shoppingCart.reset();
		session.invalidate();
		
		return PACKAGE_PATH + "logout";
	}
}
