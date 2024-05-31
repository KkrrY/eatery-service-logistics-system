package client.controller;

import dto.RegistrationForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import static constants.PathConstants.API_V1_REGISTRATION;
import static client.constants.UtilConstants.RESOURCE_SERVER_URL;
import static client.constants.PathConstants.*;

@Controller
@RequestMapping(CLIENT_REGISTRATION)
@RequiredArgsConstructor
public class RegisterController {

    private final RestTemplate clientRestTemplate;

    @GetMapping
    public String showRegisterForm () {
        return "register";
    }

    @PostMapping
    public String login (@Valid RegistrationForm registrationForm, Errors errors) {
        if (errors.hasErrors()) {
            return "register";
        }
        String response = clientRestTemplate.postForObject(RESOURCE_SERVER_URL +  API_V1_REGISTRATION, registrationForm, String.class);
        System.out.println(response);
        return "redirect:" + HOME;
    }
}
