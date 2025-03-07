package softuni.bg.supplementsonlinestore.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.bg.supplementsonlinestore.web.dto.ProductRequest;

@Controller
@RequestMapping("home")
public class ProductController {


    @GetMapping("/products")
    public ModelAndView showProducts() {
        ModelAndView modelAndView = new ModelAndView("products");
        modelAndView.setViewName("home");
        modelAndView.addObject("productRequest", new ProductRequest());
        return modelAndView;
    }
}
