package com.management.task.service;

import com.management.task.exception.ResourceConflictException;
import com.management.task.mapper.UserMapper;
import com.management.task.model.user.User;
import com.management.task.model.user.UserResponse;
import com.management.task.repository.UserRepository;
import com.management.task.security.UserPrincipal;
import com.management.task.security.model.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service class for managing user-related operations.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Creates a new user based on the provided sign-up request.
     *
     * @param signUpRequest The sign-up request containing user details.
     * @throws ResourceConflictException If a user with the provided email already exists.
     */
    public void createUser(SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.email())) {
            throw new ResourceConflictException("User", "id", signUpRequest.email());
        }
        User user = userMapper.map(signUpRequest);
        userRepository.save(user);
    }

    /**
     * Retrieves details of the authenticated user.
     *
     * @param userPrincipal The authenticated user principal.
     * @return A UserResponse object representing details of the authenticated user.
     */
    public UserResponse getUser(UserPrincipal userPrincipal) {
        return userMapper.map(userPrincipal);
    }

    public boolean existsById(Long userId) {
        return userRepository.existsById(userId);
    }

}
