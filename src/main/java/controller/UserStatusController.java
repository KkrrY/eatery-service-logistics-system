package controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import security.JwtProvider;

import static constants.PathConstants.USER_ROLE;

@RestController
@RequestMapping(USER_ROLE)
@RequiredArgsConstructor
public class UserStatusController {

    private final JwtProvider jwtProvider;

//    @PreAuthorize("hasRole('ROLE_ANONYMOUS')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> getUserRole (@SessionAttribute String token) {

        Authentication authentication = jwtProvider.getAuthentication(token);

        return authentication == null
                ? new ResponseEntity<>(false, HttpStatus.OK)
                : new ResponseEntity<>(authentication.getAuthorities().contains(new SimpleGrantedAuthority("USER")), HttpStatus.OK);
    }
}
