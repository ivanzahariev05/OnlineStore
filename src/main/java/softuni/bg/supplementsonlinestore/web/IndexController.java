package softuni.bg.supplementsonlinestore.web;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.bg.supplementsonlinestore.user.model.User;
import softuni.bg.supplementsonlinestore.user.service.UserService;
import softuni.bg.supplementsonlinestore.web.dto.LoginRequest;
import softuni.bg.supplementsonlinestore.web.dto.RegisterRequest;

@Controller
@RequestMapping("/")
public class IndexController {

    private final UserService userService;

    public IndexController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView index() {

        return new ModelAndView("index");
    }


    @GetMapping("/login")
    public ModelAndView login() {
        User user = new User();
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.setViewName("login");
        modelAndView.addObject("loginRequest", new LoginRequest());
        return modelAndView;
    }



    @GetMapping("/register")
    public ModelAndView register() {
        User user = new User();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register");
        modelAndView.addObject("registerRequest", new RegisterRequest());
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registerUser(@Valid RegisterRequest registerRequest, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            return  new ModelAndView("register");
        }

        userService.registerUser(registerRequest);
        modelAndView.setViewName("redirect:/login");
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView loginUser(@Valid LoginRequest loginRequest, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            return  new ModelAndView("login");
        }
       userService.loginUser(loginRequest);
       modelAndView.setViewName("redirect:/home");
       return modelAndView;
    }
}
