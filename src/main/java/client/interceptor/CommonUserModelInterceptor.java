package client.interceptor;

import dto.auth.AuthenticationResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import security.JwtProvider;
import security.UserPrincipal;
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

import java.security.Principal;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Set;

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
        AuthenticationResponse authenticationResponse = (AuthenticationResponse) session.getAttribute("authenticationResponse");
        if (authenticationResponse != null) session.setAttribute("token", authenticationResponse.getToken());
        User user = getUser(request);
        session.setAttribute("order", getOrder(user.getUsername()));
        session.setAttribute("user", user);
        request.setAttribute("user", user);

        return true;
    }

    private Orders getOrder(String username) {
        Orders order = new Orders();
        order.setUserName(username);
        return order;
    }

    private User getUser(HttpServletRequest request) {
        HttpSession session = request.getSession();

        String token = (String) session.getAttribute("token");
        //Authentication authentication = jwtProvider.getAuthentication(token);
        String username = "";
        try {
            username = jwtProvider.getUsername(token);
        } catch (IllegalArgumentException ignored) {}
        User user = userRepository.findByUsername(username
        ).orElse(null);
        if (user == null) {
            user = new User();
            user.setUsername("Guest");
            user.setZip("Guest");
            user.setCity("Guest");
            user.setCountry("Guest");
            user.setFirstName("Guest");
            user.setLastName("Guest");
            user.setRoles(Set.of(Role.GUEST));
        }
        return user;
    }
}
