package client.configuration;

import client.interceptor.JwtInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class ClientRestTemplateConfiguration {
    private final JwtInterceptor jwtInterceptor;

    @Bean
    public RestTemplate clientRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(jwtInterceptor));
        return restTemplate;
    }
}
