package security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.ErrorMessage.INVALID_JWT_TOKEN;
import static constants.LoggingConstants.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends GenericFilterBean {

    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String token = jwtProvider.resolveToken((HttpServletRequest) servletRequest);
        String className = this.getClass().getName();
        if (token == null) {
            request.setAttribute(TOKEN_STATUS, String.format("Class %s : NULL TOKEN",className ));
        } else {
            try {
                jwtProvider.validateToken(token);
                request.setAttribute(TOKEN_STATUS, String.format("Class %s : VALID TOKEN", className ));
            } catch (Exception ignored) {
                request.setAttribute(TOKEN_STATUS, String.format("Class %s : INVALID TOKEN", className ));
            }
        }

        try {

            if (token != null && jwtProvider.validateToken(token)) {

                Authentication authentication = jwtProvider.getAuthentication(token);

                if (authentication != null) {
                    request.setAttribute("authenticationPrincipal", authentication.getPrincipal());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (JwtAuthenticationException e) {
            SecurityContextHolder.clearContext();
            ((HttpServletResponse) servletResponse).sendError(e.getHttpStatus().value());
            throw new JwtAuthenticationException(INVALID_JWT_TOKEN);
        }
        filterChain.doFilter(
                servletRequest, //servletRequest,
                servletResponse);
    }

}
