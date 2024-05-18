package client.controller;

import dto.LoginRequest;
import dto.auth.AuthenticationResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import mapper.AuthenticationMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import static client.constants.ResourceUrl.RESOURCE_SERVER_URL;
import static constants.PathConstants.*;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final RestTemplate restTemplate;

    @GetMapping("/authorization")
    public String showLoginForm() {
        // Return the view name for your login page
        return "login";
    }

    @PostMapping("/authorization")
    public String login (@Valid LoginRequest loginRequest, Errors errors, HttpSession session) {

        if (errors.hasErrors()) {
            return "login";
        }

        AuthenticationResponse authenticationResponse = restTemplate.postForObject(RESOURCE_SERVER_URL + API_V1_AUTH + LOGIN, loginRequest, AuthenticationResponse.class);
        System.out.println("usr: " +authenticationResponse.getUser().getFirstName());
        session.setAttribute("authenticationResponse", authenticationResponse);
        return "redirect:/";
    }




}
