package softuni.bg.supplementsonlinestore.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import softuni.bg.supplementsonlinestore.dto.RegisterUserDto;
import softuni.bg.supplementsonlinestore.model.User;
import softuni.bg.supplementsonlinestore.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    public void registerUser(RegisterUserDto registerUserDto) {
        if (userRepository.findByUsername(registerUserDto.getUsername()).isPresent()) {
            throw new RuntimeException("Потребителското име вече съществува!");
        }
        if (userRepository.findByEmail(registerUserDto.getEmail()).isPresent()) {
            throw new RuntimeException("Този имейл вече е зает!");
        }

        User user = new User();
        user.setUsername(registerUserDto.getUsername());
        user.setEmail(registerUserDto.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(registerUserDto.getPassword()));
        user.setRole("User");

        userRepository.saveAndFlush(user);
    }

    @Override
    public User loginUser(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Потребител с такова име не е намерен"));

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Грешна парола");
        }
        return user;
    }
}
