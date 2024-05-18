package client.controller;

import dto.RegistrationForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import static constants.PathConstants.API_V1_REGISTRATION;
import static client.constants.ResourceUrl.RESOURCE_SERVER_URL;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {

    private final RestTemplate restTemplate;

    @GetMapping
    public String showRegisterForm () {
        return "register";
    }

    @PostMapping
    public String login (@Valid RegistrationForm registrationForm, Errors errors) {
        if (errors.hasErrors()) {
            return "register";
        }
        String response = restTemplate.postForObject(RESOURCE_SERVER_URL +  API_V1_REGISTRATION, registrationForm, String.class);
        System.out.println(response);
        return "redirect:/";
    }
}
