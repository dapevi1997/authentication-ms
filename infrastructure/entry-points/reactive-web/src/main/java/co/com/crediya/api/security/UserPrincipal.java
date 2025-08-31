package co.com.crediya.api.security;

import co.com.crediya.model.user.gateways.UserRepository;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class UserPrincipal implements UserDetails {
    private final UserRepository userRepository;
    private String email;
    private String password;
    private Long idRole;
    private String nameRole;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Stream.of("ROLE_" + nameRole)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

}
