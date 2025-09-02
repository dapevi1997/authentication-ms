package co.com.crediya.api.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RegisterUserResponseDto implements Serializable {
    private String idUser;
    private String idRole;
    private String message;
    private String email;
    private String nombre;
    private String salarioBase;
    @Builder.Default
    private String timestamp = String.valueOf(System.currentTimeMillis());
}
