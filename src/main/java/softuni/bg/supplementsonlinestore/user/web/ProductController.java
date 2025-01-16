package softuni.bg.supplementsonlinestore.user.web;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import softuni.bg.supplementsonlinestore.products.model.Product;
import softuni.bg.supplementsonlinestore.products.service.ProductService;

import java.util.List;



public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String getIndexPage(Model model) {
        List<Product> products = productService.getRandomProducts();
        model.addAttribute("products", products);
        return "index";
    }
}
