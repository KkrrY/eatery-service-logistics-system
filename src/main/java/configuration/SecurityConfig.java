package configuration;

import client.interceptor.JwtPageVisitInterceptor;
import constants.SecurityConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import security.JwtConfigurer;
import security.oauth2.CustomOAuth2UserService;
import security.oauth2.OAuth2SuccessHandler;

import static client.constants.PathConstants.*;
import static constants.PathConstants.GUEST_ALLOWED_RESOURCES;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig implements WebMvcConfigurer{


    private final SecurityConstants securityConstants;
    private final JwtConfigurer jwtConfigurer;
    private final OAuth2SuccessHandler oauthSuccessHandler;
    private final CustomOAuth2UserService oAuth2UserService;
    private final JwtPageVisitInterceptor jwtPageVisitInterceptor;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                //.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //doesn't remember the user
                .authorizeHttpRequests((authorizeHttpRequests) ->
                                authorizeHttpRequests
                                        .requestMatchers(CLIENT_ALLOWED_GUEST_USERS_RESOURCES).permitAll()
                                        .requestMatchers(CLIENT_ALLOWED_AUTHORIZED_RESOURCES).hasAnyAuthority("USER")
                                        .requestMatchers(GUEST_ALLOWED_RESOURCES).permitAll()
                                        .requestMatchers(securityConstants.getAllowedResources()).permitAll() //allow access to static resources
                                        .requestMatchers(
                                                PathRequest
                                                        .toStaticResources()
                                                        .atCommonLocations()
                                        ).permitAll()
                                        //.anyRequest().permitAll()
                                        .anyRequest().hasRole("ADMIN")
                )
                .addFilterBefore(jwtPageVisitInterceptor, UsernamePasswordAuthenticationFilter.class)
//                .logout( //client-related
//                        logout -> logout
//                                .logoutRequestMatcher(new AntPathRequestMatcher("/logouts"))
//                                .logoutSuccessUrl(HOME)
//                                .permitAll()
//                )
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
                .apply(jwtConfigurer)
                ;

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


}
