package client.interceptor;

import dto.auth.AuthenticationResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static client.constants.UtilConstants.*;

@Component
@RequiredArgsConstructor
@Getter
public class JwtPageVisitInterceptor extends OncePerRequestFilter {
    private final HttpSession httpSession;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Retrieve the AuthenticationResponse object from the session
        AuthenticationResponse authResponse = (AuthenticationResponse) httpSession.getAttribute(AUTHENTICATION_RESPONSE);

        if (authResponse != null && authResponse.getToken() != null) {

            // Wrap the request to add the Authorization header
            HeaderMapRequestWrapper requestWrapper = new HeaderMapRequestWrapper(request);
            requestWrapper.addHeader(AUTHORIZATION_HEADER, authResponse.getToken());

            // Continue processing with the wrapped request
            filterChain.doFilter(requestWrapper, response);
        } else {
            // Continue with the original request if no token is present
            filterChain.doFilter(request, response);
        }
    }
}


class HeaderMapRequestWrapper extends HttpServletRequestWrapper {
    private final Map<String, String> headerMap;

    public HeaderMapRequestWrapper(HttpServletRequest request) {
        super(request);
        this.headerMap = new HashMap<>();
    }

    public void addHeader(String name, String value) {
        headerMap.put(name, value);
    }

    @Override
    public String getHeader(String name) {
        String headerValue = headerMap.get(name);
        if (headerValue != null) {
            return headerValue;
        }
        return super.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        if (headerMap.containsKey(name)) {
            return Collections.enumeration(Collections.singletonList(headerMap.get(name)));
        }
        return super.getHeaders(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        Map<String, String> combinedHeaders = new HashMap<>();
        Enumeration<String> originalHeaderNames = super.getHeaderNames();
        while (originalHeaderNames.hasMoreElements()) {
            String headerName = originalHeaderNames.nextElement();
            combinedHeaders.put(headerName, super.getHeader(headerName));
        }
        combinedHeaders.putAll(headerMap);
        return Collections.enumeration(combinedHeaders.keySet());
    }
}
