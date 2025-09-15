package co.com.crediya.r2dbc.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MySqlSecretDto {
    private String username;
    private String password;
}
