package com.management.task.controller;

import com.management.task.model.user.UserResponse;
import com.management.task.security.CurrentUser;
import com.management.task.security.UserPrincipal;
import com.management.task.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller class for handling user-related operations.
 */
@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Retrieves details of the authenticated user.
     *
     * @param userPrincipal The authenticated user principal.
     * @return A UserResponse object containing details of the authenticated user.
     */
    @GetMapping("/me")
    public UserResponse getMe(@CurrentUser UserPrincipal userPrincipal) {
        return userService.getUser(userPrincipal);
    }

}
