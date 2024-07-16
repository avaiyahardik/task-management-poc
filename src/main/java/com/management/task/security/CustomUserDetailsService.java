package com.management.task.security;

import com.management.task.exception.ResourceNotFoundException;
import com.management.task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class that implements Spring Security's UserDetailsService interface
 * to load user details by username or ID.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Loads user details by username (email).
     *
     * @param email The email address of the user.
     * @return UserDetails containing user information.
     * @throws UsernameNotFoundException If no user found with the given email.
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        var user =
                userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));

        return UserPrincipal.create(user);
    }

    /**
     * Loads user details by user ID.
     *
     * @param id The ID of the user.
     * @return UserDetails containing user information.
     * @throws ResourceNotFoundException If no user found with the given ID.
     */
    @Transactional
    public UserDetails loadUserById(Long id) {
        var user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        return UserPrincipal.create(user);
    }
}