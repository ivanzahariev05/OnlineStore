package softuni.bg.supplementsonlinestore.web.dto;


import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {

    @Size(min = 3, max = 20, message = "Username is required!")
    private String username;

    @Size(min = 6, max = 20, message = "Password is required!")
    private String password;


}
