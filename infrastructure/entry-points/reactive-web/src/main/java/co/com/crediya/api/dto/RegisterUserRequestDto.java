package co.com.crediya.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;

@Schema(description = "Entidad Usuario del sistema")
public class RegisterUserRequestDto implements Serializable {
    @NotBlank(message = "El campo nombre no puede estar vacío")
    @NotNull(message = "El campo nombre no puede ser nulo")
    @JsonProperty("nombre")
    @Schema(description = "Nombre del usuario", examples = "Nombre")
    private String name;
    @JsonProperty("apellido")
    @NotBlank(message = "El campo apellido no puede estar vacío")
    @NotNull(message = "El campo apellido no puede ser nulo")
    @Schema(description = "Apellido del usuario", examples = "Apellido")
    private String lastName;
    @JsonProperty("email")
    @NotNull(message = "El campo email no puede ser nulo")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "El email debe tener formato correcto")
    @Schema(description = "Email del usuario", examples = "user@mail.com")
    private String email;
    @JsonProperty("numero_documento")
    @NotBlank(message = "El campo numero_documento no puede estar vacío")
    @NotNull(message = "El campo numero_documento no puede ser nulo")
    @Schema(description = "Número de documento", examples = "110398545")
    private String documentId;
    @JsonProperty("telefono")
    @NotBlank(message = "El campo telefono no puede estar vacío")
    @NotNull(message = "El campo telefono no puede ser nulo")
    @Schema(description = "Número de celular", examples = "3215784652")
    private String phone;
    @JsonProperty("salario_base")
    @NotBlank(message = "El campo salario_base no puede estar vacío")
    @NotNull(message = "El campo salario_base no puede ser nulo")
    @Schema(description = "Salario base", examples = "1000000")
    private String baseSalary;
    @NotBlank(message = "El campo numero_documento no puede estar vacío")
    @NotNull(message = "El campo numero_documento no puede ser nulo")
    @Schema(description = "Id del rol", examples = "1")
    @JsonProperty("id_rol")
    private String idRole;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(String baseSalary) {
        this.baseSalary = baseSalary;
    }

    public String getIdRole() {
        return idRole;
    }

    public void setIdRole(String idRole) {
        this.idRole = idRole;
    }
}
