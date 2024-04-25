package org.example.usermicroservice.mapper;

import org.example.usermicroservice.dto.request.SignupRequest;
import org.example.usermicroservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    User fromSignupRequest(SignupRequest signupRequest);
}
