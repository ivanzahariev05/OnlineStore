package softuni.bg.supplementsonlinestore.user.web;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import softuni.bg.supplementsonlinestore.exception.DomainException;
import softuni.bg.supplementsonlinestore.user.dto.LoginRequest;
import softuni.bg.supplementsonlinestore.user.dto.RegisterRequest;
import softuni.bg.supplementsonlinestore.user.service.UserService;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("RegisterRequest", new RegisterRequest());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("RegisterRequest") RegisterRequest registerUserDto,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        try {
            userService.registerUser(registerUserDto);
        } catch (DomainException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }

        return "redirect:/users/login";
    }


    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@Valid @ModelAttribute("loginUserDto") LoginRequest loginUserDto,
    BindingResult bindingResult,
    Model model) {
        if (bindingResult.hasErrors()) {
            return "login";
        }
        try {
            userService.loginUser(loginUserDto);
        }catch (DomainException e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
        return "redirect:/index";
    }
}

