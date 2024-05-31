package client.interceptor;

import dto.auth.AuthenticationResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static client.constants.UtilConstants.*;


@Component
public class JwtInterceptor implements ClientHttpRequestInterceptor {

    private final HttpSession httpSession;

    public JwtInterceptor(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        AuthenticationResponse authResponse = (AuthenticationResponse) httpSession.getAttribute(AUTHENTICATION_RESPONSE);

        if (authResponse != null && authResponse.getToken() != null) {

            request.getHeaders().add(AUTHORIZATION_HEADER, authResponse.getToken());
        }

        return execution.execute(request, body);
    }
}
