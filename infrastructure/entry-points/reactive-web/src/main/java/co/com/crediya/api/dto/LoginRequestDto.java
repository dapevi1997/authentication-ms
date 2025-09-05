package co.com.crediya.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;


@Schema(description = "Json para el login")
public class LoginRequestDto implements Serializable {
    @NotBlank(message = "El campo email no puede estar vacío")
    @NotNull(message = "El campo email no puede ser nulo")
    @Schema(description = "Correo del usuario que quiere hacer login", examples = "admin@mail.com")
    private String email;
    @NotBlank(message = "El clave password no puede estar vacío")
    @NotNull(message = "El campo password no puede ser nulo")
    @Schema(description = "Contraseña del usuario", examples = "admin123")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
