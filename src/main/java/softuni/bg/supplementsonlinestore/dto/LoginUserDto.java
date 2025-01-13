package softuni.bg.supplementsonlinestore.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginUserDto {

    @NotBlank(message = "Потребителското име е задължително!")
    private String username;

    @NotBlank(message = "Паролата е задължителна!")
    private String password;

    public @NotBlank(message = "Потребителското име е задължително!") String getUsername() {
        return username;
    }

    public @NotBlank(message = "Паролата е задължителна!") String getPassword() {
        return password;
    }
}
