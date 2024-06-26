package dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse extends BaseUserResponse {
    private String lastName;
    private String street;
    private String city;
    private String country;
    private String zip;
    private String phoneNumber;
}
