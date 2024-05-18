package controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static constants.PathConstants.LOGOUT;
import static constants.PathConstants.REDIRECT_LOGOUT;

@Controller
class LogoutController {

    @GetMapping(LOGOUT)
    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return REDIRECT_LOGOUT; // Redirect to the login page after logout
    }
}
