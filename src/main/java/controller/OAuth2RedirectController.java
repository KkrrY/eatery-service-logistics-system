package controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import security.JwtProvider;

@RestController
public class OAuth2RedirectController {

    @Autowired
    JwtProvider jwtProvider;

    @GetMapping("/oauth2/redirect")
    public RedirectView oauth2Redirect(@RequestParam("token") String token, HttpServletRequest request) {
        RedirectView redirectView = new RedirectView("/");
        redirectView.addStaticAttribute("token", token);

        HttpSession session = request.getSession();
        session.setAttribute("token", token);
        request.setAttribute("token", token);


        return redirectView;
    }
}
