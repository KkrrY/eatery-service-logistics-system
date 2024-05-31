package client.interceptor;

import dto.auth.AuthenticationResponse;
import security.JwtProvider;
import entity.Orders;
import entity.User;
import enums.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Set;

import static client.constants.UtilConstants.*;

@Component
public class CommonUserModelInterceptor implements HandlerInterceptor { //now add to config
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Autowired
    public CommonUserModelInterceptor(UserRepository userRepository, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        AuthenticationResponse authenticationResponse = (AuthenticationResponse) session.getAttribute(AUTHENTICATION_RESPONSE);
        if (authenticationResponse != null) session.setAttribute(TOKEN_ATTRIBUTE, authenticationResponse.getToken());
        User user = getUser(request);
        session.setAttribute(ORDER_ATTRIBUTE, getOrder(user.getUsername()));
        session.setAttribute(USER_ATTRIBUTE, user);
        request.setAttribute(USER_ATTRIBUTE, user);

        return true;
    }

    private Orders getOrder(String username) {
        Orders order = new Orders();
        order.setUserName(username);
        return order;
    }

    private User getUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        AuthenticationResponse authenticationResponse = (AuthenticationResponse) session.getAttribute(AUTHENTICATION_RESPONSE);
        String token = (String) session.getAttribute(TOKEN_ATTRIBUTE);
        //Authentication authentication = jwtProvider.getAuthentication(token);
        String username = "";
        try {
            username = jwtProvider.getUsername(token); //TODO: refactor
        } catch (Exception ignored) {}
        User user = userRepository.findByUsername(username
        ).orElse(null);
        if (user == null) {
            user = new User();
            user.setUsername("Guest");
            user.setZip("");
            user.setCity("");
            user.setCountry("");
            user.setFirstName("");
            user.setLastName("");
            user.setRoles(Set.of(Role.GUEST));
        }
        return user;
    }
}
