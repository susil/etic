package com.singpro.myapp;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 * http://socialcompare.com/en/comparison/javascript-graphs-and-charts-libraries
 */
//@Controller
//public class HomeController {
//	
//	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
//	
//	/**
//	 * Simply selects the home view to render by returning its name.
//	 */
//	@RequestMapping(value = "/", method = RequestMethod.GET)
//	public String home(Locale locale, Model model) {
//		logger.info("Welcome home! The client locale is {}.", locale);
//		
//		Date date = new Date();
//		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
//		
//		String formattedDate = dateFormat.format(date);
//		
//		model.addAttribute("serverTime", formattedDate );
//		
//		//return "home";
//		
//		//return "redirect:/test";
//		return "systems";
//		
//	}
@Controller
//@RequestMapping("/")
public class HomeController {

	@RequestMapping(value="/", method=RequestMethod.GET )
	public String getHomePage() {
		return "redirect:/systems";
	}
	
	@RequestMapping(value="/graph", method=RequestMethod.GET )
	public String getGraphPage() {
		return "graph";
	}
	
}
//}
