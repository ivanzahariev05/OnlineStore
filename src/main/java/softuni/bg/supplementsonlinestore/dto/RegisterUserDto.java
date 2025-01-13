package softuni.bg.supplementsonlinestore.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RegisterUserDto {

    @NotBlank(message = "Потребителското име е задължително!")
    private String username;

    @NotBlank(message = "Имейлът е задължителен")
    @Email(message = "Невалиден имейл!")
    private String email;

    @NotBlank(message = "Паролата трябва да е с дължина минимум 6 символа")
    private String password;

    public @NotBlank(message = "Потребителското име е задължително!") String getUsername() {
        return username;
    }

    public @NotBlank(message = "Имейлът е задължителен") @Email(message = "Невалиден имейл!") String getEmail() {
        return email;
    }

    public @NotBlank(message = "Паролата трябва да е с дължина минимум 6 символа") String getPassword() {
        return password;
    }
}
