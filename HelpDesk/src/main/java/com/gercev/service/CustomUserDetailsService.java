package com.gercev.service;

import com.gercev.domain.User;
import com.gercev.exception.UserNotFoundException;
import com.gercev.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

//    @Autowired
//    public CustomUserDetailsService(UserRepository userRepository, UserService userService) {
//        this.userRepository = userRepository;
//        this.userService = userService;
//    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userService.getByEmail(email);
        if (userOptional.isPresent()) {
            return org.springframework.security.core.userdetails.User
                    .withUsername(email)
                    .password(userOptional.get().getPassword())
                    .roles(userOptional.get().getRole().toString()).build();
        } else {
            throw new UserNotFoundException(email + "NotFound");
        }
    }
}
