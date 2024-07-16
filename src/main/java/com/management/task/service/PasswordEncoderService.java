package com.management.task.service;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordEncoderService {

    private final PasswordEncoder passwordEncoder;

    @Named("getEncodedPassword")
    public String getEncodedPassword(String plainTextPassword) {
        return passwordEncoder.encode(plainTextPassword);
    }
}
