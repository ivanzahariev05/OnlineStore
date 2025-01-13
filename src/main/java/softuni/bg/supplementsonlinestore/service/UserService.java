package softuni.bg.supplementsonlinestore.service;

import softuni.bg.supplementsonlinestore.dto.RegisterUserDto;
import softuni.bg.supplementsonlinestore.model.User;

public interface UserService {

    void registerUser(RegisterUserDto registerUserDto);
    User loginUser(String username, String password);
}
