package softuni.bg.supplementsonlinestore.user.web;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import softuni.bg.supplementsonlinestore.user.dto.RegisterRequest;

@Controller
public class HomeController {

    @GetMapping("/index")
    public String index(Model model) {

        return "index";
    }

    @GetMapping("/users/register")
    public String register(Model model) {
        return "register";
    }



    @GetMapping("/users/login")
    public String login(Model model) {
        return "login";
    }
}
