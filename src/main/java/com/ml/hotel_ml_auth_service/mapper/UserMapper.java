package com.ml.hotel_ml_auth_service.mapper;

import com.ml.hotel_ml_auth_service.dto.UserDto;
import com.ml.hotel_ml_auth_service.dto.UserResponseDetailsDto;
import com.ml.hotel_ml_auth_service.model.Role;
import com.ml.hotel_ml_auth_service.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface UserMapper {

    UserMapper Instance = Mappers.getMapper(UserMapper.class);

    UserDto mapUserToUserDto(User user);
    User mapUserDtoToUser(UserDto userDto);
    List<UserDto> mapUserListToUserDtoList(List<User> userList);
    List<User> mapUserDtoListToUserList(List<UserDto> userDtoList);
    Set<UserDto> mapUserSetToUserDtoSet(Set<User> userSet);
    Set<User> mapUserDtoSetToUserSet(Set<UserDto> userDtoSet);

    User mapUserResponseDetailsToUser(UserResponseDetailsDto userResponseDetailsDto);

    @Mapping(source = "roles", target = "role", qualifiedByName = "rolesToString")
    UserResponseDetailsDto mapUserToUserResponseDetailsDto(User user);

    @Named("rolesToString")
    default String rolesToString(Set<Role> roles) {
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.joining(", "));
    }
}
