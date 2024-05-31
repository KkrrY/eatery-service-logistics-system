package service.impl;

import entity.ProviderAccessToken;
import enums.AuthProvider;
import exception.ApiRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import repository.ProviderAccessTokenRepository;
import service.ProviderAccessTokenService;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static constants.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class ProviderAccessTokenServiceImpl implements ProviderAccessTokenService {
    private static final String GOOGLE_TOKEN_REVOKE_URL = "https://accounts.google.com/o/oauth2/revoke";

    private final ProviderAccessTokenRepository tokenRepository;

    private final RestTemplate restTemplate;

    @Override
    public String findAccessTokenByEmail(String email) {
        ProviderAccessToken accessToken = tokenRepository.findById(email).orElseThrow(
                () -> new ApiRequestException(EMAIL_NOT_FOUND, HttpStatus.NOT_FOUND)
        );
        return accessToken.getTokenValue();
    }

    @Override
    @Transactional
    public String revokeAccessToken(String email) {
        Map<String, Predicate<String>> conditions = new HashMap<>();

        ProviderAccessToken accessToken = tokenRepository.findByUserEmail(email).orElseThrow(
                () -> new ApiRequestException(EMAIL_NOT_FOUND, HttpStatus.NOT_FOUND)
        );
        //returns full revoke path with token to revoke if the input value (provider) equals to GOOGLE provider
        conditions.put(GOOGLE_TOKEN_REVOKE_URL + "?token=" + accessToken.getTokenValue(), provider -> provider.equalsIgnoreCase(AuthProvider.GOOGLE.toString()) );
        String provider = accessToken.getProvider();
        String revokeLink = evaluateValue(provider, conditions);

        restTemplate.postForEntity(revokeLink, null, Void.class);

        tokenRepository.deleteByTokenValue(accessToken.getTokenValue());
        return "Token revoked successfully";
    }

    /**
     * Used in CustomOAuth2UserService to save tokens per successful authentication
     * */
    @Override
    public ProviderAccessToken saveAccessToken (ProviderAccessToken token) {
        return tokenRepository.save(token);
    }

    /**
     * Evaluates the given object against a set of conditions and returns the key of the first condition that matches.
     *
     * @param <T> The type of the object and the keys in the conditions map.
     * @param obj The object to be evaluated.
     * @param conditions A map where keys are return results for conditions in case of successful occurrence and values are predicates to test the object.
     * @return The key corresponding to the first predicate that returns true for the given object, or null if none match.
     */
    private static <T> T evaluateValue(T obj, Map<T, Predicate<T>> conditions) {
        return conditions.entrySet()
                .stream()
                .filter(entry -> entry.getValue().test(obj))
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(null);
    }
}
