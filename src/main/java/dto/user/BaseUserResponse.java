package dto.user;

import enums.Role;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class BaseUserResponse {
    private Long id;
    private String username;
    private String firstName;

    private Set<Role> roles;
    private String provider;
}
