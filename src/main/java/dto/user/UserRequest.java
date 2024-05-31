package dto.user;

import enums.Role;

import java.util.Set;

public class UserRequest {
    private Long id;
    private String username;
    private String firstName;

    private Set<Role> roles;
    private String provider;
    private String lastName;
    private String street;
    private String city;
    private String country;
    private String zip;
    private String phoneNumber;
}
