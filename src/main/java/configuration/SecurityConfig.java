package configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import security.JwtConfigurer;
import security.oauth2.CustomOAuth2UserService;
import security.oauth2.OAuth2SuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtConfigurer jwtConfigurer;
    private final OAuth2SuccessHandler oauthSuccessHandler;
    private final CustomOAuth2UserService oAuth2UserService;

    public SecurityConfig(JwtConfigurer jwtConfigurer, OAuth2SuccessHandler oauthSuccessHandler, CustomOAuth2UserService oAuth2UserService) {
        this.jwtConfigurer = jwtConfigurer;
        this.oauthSuccessHandler = oauthSuccessHandler;
        this.oAuth2UserService = oAuth2UserService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                //.authenticationProvider( authenticationProvider )
                //.headers(headers -> headers.contentTypeOptions(content -> content.disable()))
                //.cors(cors -> cors.disable())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorizeHttpRequests) ->
                                authorizeHttpRequests
//                                        .requestMatchers(
//                                                "/register",
//                                                "/",
//                                                "/design",
//                                                "/dishes",
//                                                "/orders",
//                                                "/orders/current"
//                                        ).permitAll()
//                                        .requestMatchers("/recent-orders/orders").hasRole("USER")
//                                        .anyRequest().hasRole("ADMIN")
                                        .requestMatchers("/sensors").hasAnyRole("USER")
                                        .requestMatchers("/register", "/login").permitAll()
                                        .anyRequest().permitAll()

                )
                .oauth2Login(
                        oauth -> oauth
                            //.loginPage("/login")
                            .authorizationEndpoint(
                                endpoint -> endpoint.baseUri("/oauth2/authorize")
                                )
                            .userInfoEndpoint(
                                    endpoint -> endpoint.userService(oAuth2UserService)
                            )
                            .successHandler(oauthSuccessHandler)
                )
                .logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                )
                .apply(jwtConfigurer)
                ;

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


}
