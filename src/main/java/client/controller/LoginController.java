package client.controller;

import dto.LoginRequest;
import dto.auth.AuthenticationResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import static client.constants.UtilConstants.RESOURCE_SERVER_URL;
import static client.constants.PathConstants.*;
import static constants.PathConstants.*;
import static client.constants.UtilConstants.AUTHENTICATION_RESPONSE;

@Controller
@RequestMapping(CLIENT_LOGIN)
@RequiredArgsConstructor
public class LoginController {

    private final RestTemplate clientRestTemplate;

    @GetMapping()
    public String showLoginForm() {
        // Return the view name for your login page
        return "login";
    }

    @PostMapping()
    public String login (@Valid LoginRequest loginRequest, Errors errors, HttpSession session) {

        if (errors.hasErrors()) {
            return "login";
        }

        AuthenticationResponse authenticationResponse = clientRestTemplate.postForObject(RESOURCE_SERVER_URL + API_V1_AUTH + LOGIN, loginRequest, AuthenticationResponse.class);
        session.setAttribute(AUTHENTICATION_RESPONSE, authenticationResponse);
        return "redirect:" + HOME;
    }




}
