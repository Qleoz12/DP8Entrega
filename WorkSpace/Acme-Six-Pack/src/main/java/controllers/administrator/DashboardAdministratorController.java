package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.GymService;
import controllers.AbstractController;
import domain.Gym;

@Controller
@RequestMapping("/dashboard/administrator")
public class DashboardAdministratorController extends AbstractController {
	
	// Services ----------------------------------------------------------
	
//	@Autowired
//	private CustomerService consumerService;
	
//	@Autowired
//	private CommentService commentService;
	
	@Autowired
	private GymService gymService;
	
	// Constructors --------------------------------------------------------
	
	public DashboardAdministratorController() {
		super();
	}
	
	
	// Listing ------------------------------------------------------------
	
		@RequestMapping(value = "/list", method = RequestMethod.GET)
		public ModelAndView list() {
			ModelAndView result;
			Collection<Gym> mostPopularGyms;
//			Collection<Customer> consumerMoreOrders;
//			Collection<Customer> consumerSpentMoreMoney;
//			Collection<Item> bestSellingItem;
//			Collection<Item> worstSellingItem;
//			Collection<Clerk> clerkMoreOrders;
//			Collection<Clerk> clerkLessOrders;
//			Collection<Customer> consumerCancelledMoreOrders;
//			Collection<Customer> consumerCancelledLessOrders;
//			double ratioCancelledCurrentMonth;
//			Collection<Item> itemMoreComment;
			
			mostPopularGyms = gymService.findMostPopularGyms();
//			consumerMoreOrders = consumerService.findConsumerMoreOrders();
//			consumerSpentMoreMoney = consumerService.findConsumerSpentMoreMoney();
//			bestSellingItem = itemService.findItemBestSelling();
//			worstSellingItem = itemService.findItemWorstSelling();
//			clerkMoreOrders = clerkService.findClerkServedMoreOrders();
//			clerkLessOrders = clerkService.findClerkServedLessOrders();
//			consumerCancelledMoreOrders = consumerService.findConsumerMoreOrdersCancelled();
//			consumerCancelledLessOrders = consumerService.findConsumerLessOrdersCancelled();
//			ratioCancelledCurrentMonth = orderService.rateOrderCancelled();
//			itemMoreComment = itemService.findItemMoreComments();
			
			result = new ModelAndView("administrator/list");
			result.addObject("mostPopularGyms", mostPopularGyms);
//			result.addObject("consumerMoreOrders", consumerMoreOrders);
//			result.addObject("consumerSpentMoreMoney", consumerSpentMoreMoney);
//			result.addObject("bestSellingItem", bestSellingItem);
//			result.addObject("worstSellingItem", worstSellingItem);
//			result.addObject("clerkMoreOrders", clerkMoreOrders);
//			result.addObject("clerkLessOrders", clerkLessOrders);
//			result.addObject("consumerCancelledMoreOrders", consumerCancelledMoreOrders);
//			result.addObject("consumerCancelledLessOrders", consumerCancelledLessOrders);
//			result.addObject("ratioCancelledCurrentMonth", ratioCancelledCurrentMonth);
//			result.addObject("itemMoreComment", itemMoreComment);
			result.addObject("requestURI", "administrator/list.do");
			
			return result;
		}
	
}
