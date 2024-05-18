package dto;

import entity.User;
import enums.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Data
public class RegistrationForm {

    //@NotBlank(message = "Fill captcha.")
    private String captcha;
    private String username;
    private String password;
    private String password2;
    private String firstName;
    private String lastName;
    private String street;
    private String city;
    private String country;
    private String zip;
    private String phone;
    public User toUser(PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(passwordEncoder.encode(password));
        user.setStreet(street);
        user.setCity(city);
        user.setCountry(country);
        user.setZip(zip);
        user.setPhoneNumber(phone);
        user.setRoles(Set.of(Role.USER));
        return user;
    }
}