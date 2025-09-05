package co.com.crediya.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table("user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEntity {
    @Id
    @Column("id_user")
    private Long idUser;

    @Column("name")
    private String name;

    @Column("lastname")
    private String lastname;

    @Column("birthdate")
    private LocalDate birthdate;

    @Column("address")
    private String address;

    @Column("email")
    private String email;

    @Column("password")
    private String password;

    @Column("document_id")
    private Long documentoIdentidad;

    @Column("phone")
    private Long phone;

    @Column("base_salary")
    private BigDecimal baseSalary;

    @Column("id_role")
    private Long idRol;

    @Column("created_at")
    private LocalDate createdAt;
}
