package service.impl;

import exception.ApiRequestException;
import org.springframework.http.HttpStatus;
import security.UserPrincipal;
import entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(email)
                .orElseThrow(() -> new ApiRequestException("EMAIL_NOT_FOUND", HttpStatus.NOT_FOUND));
        return UserPrincipal.create(user);
    }
}
