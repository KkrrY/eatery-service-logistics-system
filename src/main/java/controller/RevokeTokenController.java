package controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.ProviderAccessTokenService;

import static constants.PathConstants.*;

@RestController
@RequestMapping(API_V1)
@RequiredArgsConstructor
public class RevokeTokenController {

    private final ProviderAccessTokenService providerAccessTokenService;

    @PostMapping(REVOKE)
    public ResponseEntity<String> revokeAccessToken (@RequestBody String email) { //would be null if not suggest annotation
        return new ResponseEntity<>(providerAccessTokenService.revokeAccessToken(email), HttpStatus.OK);
    }
}
