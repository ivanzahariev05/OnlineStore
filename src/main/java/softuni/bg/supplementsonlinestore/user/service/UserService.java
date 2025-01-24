package softuni.bg.supplementsonlinestore.user.service;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import softuni.bg.supplementsonlinestore.exception.DomainException;
import softuni.bg.supplementsonlinestore.web.dto.RegisterRequest;
import softuni.bg.supplementsonlinestore.web.dto.LoginRequest;
import softuni.bg.supplementsonlinestore.user.model.Role;
import softuni.bg.supplementsonlinestore.user.model.User;
import softuni.bg.supplementsonlinestore.user.repository.UserRepository;

import java.time.LocalDate;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    public void registerUser(RegisterRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new DomainException("Username is already in use!");
        }
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new DomainException("The email address is already in use!");
        }

         User user = User.builder()
                 .username(registerRequest.getUsername())
                 .password(passwordEncoder.encode(registerRequest.getPassword()))
                 .email(registerRequest.getEmail())
                 .role(Role.USER)
                 .registrationDate(LocalDate.now())
                 .build();

        userRepository.save(user);
    }

    public User loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new DomainException("Wrong username or password!"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new DomainException("Wrong username or password!");
        }
        return user;
    }

  
}
