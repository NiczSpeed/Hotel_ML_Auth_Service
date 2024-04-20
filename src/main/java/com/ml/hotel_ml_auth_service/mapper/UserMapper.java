package com.ml.hotel_ml_auth_service.mapper;

import com.ml.hotel_ml_auth_service.dto.UserDto;
import com.ml.hotel_ml_auth_service.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper
public interface UserMapper {

    UserMapper Instance = Mappers.getMapper(UserMapper.class);


    UserDto mapUserToUserDto(User user);
    User mapUserDtoToUser(UserDto userDto);
    List<UserDto> mapUserListToUserDtoList(List<User> userList);
    List<User> mapUserDtoListToUserList(List<UserDto> userDtoList);
    Set<UserDto> mapUserSetToUserDtoSet(Set<User> userSet);
    Set<User> mapUserDtoSetToUserSet(Set<UserDto> userDtoSet);
}
