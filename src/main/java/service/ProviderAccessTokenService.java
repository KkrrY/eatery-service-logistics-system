package service;

import entity.ProviderAccessToken;

public interface ProviderAccessTokenService {
    String findAccessTokenByEmail (String email);
    ProviderAccessToken saveAccessToken (ProviderAccessToken token);
    String revokeAccessToken(String email);
}
