package client.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import security.JwtProvider;

import static client.constants.PathConstants.*;

/**
 * Controller that handles OAuth2 redirect requests.
 * <p>
 * This controller listens for requests from the resource server OAuth success handler sends and processes
 * the OAuth2 token by storing it in the HTTP session and request attributes,
 * then redirects the user to the client home page.
 * </p>
 */
@RestController
public class OAuth2RedirectController {

    @Autowired
    JwtProvider jwtProvider;

    @GetMapping(OAUTH2_REDIRECT)
    public RedirectView oauth2Redirect(@RequestParam("token") String token, HttpServletRequest request) {
        RedirectView redirectView = new RedirectView(HOME);
        redirectView.addStaticAttribute("token", token);

        HttpSession session = request.getSession();
        session.setAttribute("token", token);
        request.setAttribute("token", token);


        return redirectView;
    }
}
