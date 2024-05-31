package security.oauth2;

import entity.ProviderAccessToken;
import entity.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import security.UserPrincipal;
import service.AuthenticationService;
import service.ProviderAccessTokenService;
import service.UserService;

/**
 * The dependencies of some of the beans in the application context form a cycle:
 *
 *    authenticationMapper defined in file [mapper\AuthenticationMapper.class]
 * ┌─────┐
 * |  authenticationServiceImpl defined in file [service\impl\AuthenticationServiceImpl.class]
 * ↑     ↓
 * |  securityConfig defined in file [configuration\SecurityConfig.class]
 * ↑     ↓
 * |  customOAuth2UserService defined in file [security\oauth2\CustomOAuth2UserService.class]
 * └─────┘
 * */
@Service
//@RequiredArgsConstructor //creates constructor with final fields marked (could be used for injection)
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Lazy // lazy initialization doesn't apply to constructor injection (applicable to setter injection).
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    /*or make lazy setter initialization:
    @Lazy
    @Autowired
    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }*/

    @Autowired
    private ProviderAccessTokenService providerAccessTokenService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String provider = userRequest.getClientRegistration().getRegistrationId();
        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserFactory.getOAuth2UserInfo(provider, oAuth2User.getAttributes());
        User user = userService.getUserInfo(oAuth2UserInfo.getEmail());

        if (user == null) {
            user = authenticationService.registerOauth2User(provider, oAuth2UserInfo);
        } else {
            user = authenticationService.updateOauth2User(user, provider, oAuth2UserInfo);
        }

        ProviderAccessToken accessToken = new ProviderAccessToken();
        accessToken.setUserEmail(oAuth2UserInfo.getEmail());
        accessToken.setProvider(userRequest.getClientRegistration().getRegistrationId());
        accessToken.setTokenValue(userRequest.getAccessToken().getTokenValue());

        providerAccessTokenService.saveAccessToken(accessToken);

        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }
}
