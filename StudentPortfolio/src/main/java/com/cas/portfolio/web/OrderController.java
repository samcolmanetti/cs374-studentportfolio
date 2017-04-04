package com.cas.portfolio.web;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cas.portfolio.web.model.ShoppingCart;

@Scope("session")
@Controller
public class OrderController {
	public static final String PACKAGE_PATH = "OrderManagement/";
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ShoppingCart shoppingCart;

	@RequestMapping(value = "/displayMenu", method = RequestMethod.GET)
	public String viewRegistrationPage(Principal principal, Model model) {
		// ArrayList<MenuItem> menu = orderService.getMenu();
		// model.addAttribute("menu", menu);
		logger.info("display the menu");
		return PACKAGE_PATH + "menu";
	}
	
	@RequestMapping(value = "/shoppingcart", method = RequestMethod.GET)
	public String shoppingcart(Model model) {
			logger.info("Admin Successully logged in");
			model.addAttribute("shoppingCart", shoppingCart);
			return PACKAGE_PATH + "shoppingCart";
	}
}