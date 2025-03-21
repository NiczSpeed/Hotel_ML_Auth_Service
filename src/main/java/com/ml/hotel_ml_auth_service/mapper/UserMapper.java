package com.ml.hotel_ml_auth_service.mapper;

import com.ml.hotel_ml_auth_service.dto.UserDto;
import com.ml.hotel_ml_auth_service.dto.UserResponseDetailsDto;
import com.ml.hotel_ml_auth_service.model.Role;
import com.ml.hotel_ml_auth_service.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface UserMapper {

    UserMapper Instance = Mappers.getMapper(UserMapper.class);

    User mapUserDtoToUser(UserDto userDto);

    @Mapping(source = "roles", target = "role", qualifiedByName = "rolesToString")
    UserResponseDetailsDto mapUserToUserResponseDetailsDto(User user);

    @Named("rolesToString")
    default String rolesToString(Set<Role> roles) {
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.joining(", "));
    }
}
