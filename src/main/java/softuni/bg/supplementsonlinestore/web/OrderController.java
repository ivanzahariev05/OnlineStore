package softuni.bg.supplementsonlinestore.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.bg.supplementsonlinestore.order.model.Order;
import softuni.bg.supplementsonlinestore.order.service.OrderService;
import softuni.bg.supplementsonlinestore.security.MetaDataAuthentication;
import softuni.bg.supplementsonlinestore.user.model.User;
import softuni.bg.supplementsonlinestore.user.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView getOrders(@AuthenticationPrincipal MetaDataAuthentication metaDataAuthentication){
        ModelAndView modelAndView = new ModelAndView("orders");

        User user = userService.findById(metaDataAuthentication.getId());
        List<Order> orders =  orderService.getAllOrders();

        modelAndView.addObject("user", user);
        modelAndView.addObject("orders", orders);
        return modelAndView;
    }

}
