package client.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import security.UserPrincipal;

import static client.constants.PathConstants.LOGOUT;
import static client.constants.PathConstants.HOME;
import static client.constants.UtilConstants.RESOURCE_SERVER_URL;
import static constants.PathConstants.*;

@RestController
@RequiredArgsConstructor
public class LogoutController {

    private final RestTemplate clientRestTemplate;

    @GetMapping(LOGOUT)
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

            if (authentication!= null && authentication.getPrincipal() instanceof OAuth2User) { //always true if auth is not null (???)
                OAuth2User oAuth2User = (UserPrincipal) authentication.getPrincipal();
//                Map<String, Object> attributes = oAuth2User.getAttributes();
//                for (Map.Entry<String, Object> entry : attributes.entrySet()) {
//                    String attributeName = entry.getKey();
//                    Object attributeValue = entry.getValue();
//                    System.out.println(attributeName + " = " + attributeValue.toString());
//                }
                try {
                    String email = oAuth2User.getAttribute("email").toString();
                        clientRestTemplate.postForObject(RESOURCE_SERVER_URL + API_V1 + REVOKE, email, String.class);
                } catch (NullPointerException ignored) {}
//                Enumeration<String> sessionattrs = request.getAttributeNames();
//                while (sessionattrs.hasMoreElements()) {
//                    System.out.println("session attr: " + sessionattrs.nextElement());
//                }


            }

            request.getSession().invalidate();

            SecurityContextHolder.clearContext();

//            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", HOME);
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            return new ResponseEntity<>(headers, HttpStatus.FOUND);

    }


}