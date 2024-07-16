package com.management.task.mapper;

import com.management.task.model.user.User;
import com.management.task.model.user.UserResponse;
import com.management.task.security.UserPrincipal;
import com.management.task.security.model.SignUpRequest;
import com.management.task.service.PasswordEncoderService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.OffsetDateTime;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = {OffsetDateTime.class}, uses = {PasswordEncoderService.class})
public interface UserMapper {

    UserResponse map(UserPrincipal userPrincipal);

    @Mapping(target = "password", source = "signUpRequest.password", qualifiedByName = "getEncodedPassword")
    @Mapping(target = "firstname", source = "signUpRequest.firstname")
    @Mapping(target = "lastname", source = "signUpRequest.lastname")
    @Mapping(target = "role", source = "signUpRequest.role")
    User map(SignUpRequest signUpRequest);

}
