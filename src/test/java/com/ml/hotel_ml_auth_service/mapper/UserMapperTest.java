package com.ml.hotel_ml_auth_service.mapper;

import com.ml.hotel_ml_auth_service.dto.UserDto;
import com.ml.hotel_ml_auth_service.model.User;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
class UserMapperTest {

//    @Test
//    void shouldMapUserToUserDto(){
//        //given
//        User user = Instancio.create(User.class);
//
//        //when
//        UserDto userDTO = UserMapper.Instance.mapUserToUserDto(user);
//
//        //then
//        assertNotNull(userDTO);
//        assertEquals(user.getFirstName(), userDTO.getFirstName());
//        assertEquals(user.getLastName(), userDTO.getLastName());
//        assertEquals(user.getEmail(), userDTO.getEmail());
//        assertEquals(user.getPassword(), userDTO.getPassword());
//    }
//
//    @Test
//    void shouldMapUserDtoToUser(){
//        //given
//        UserDto userDto = Instancio.create(UserDto.class);
//
//        //when
//        User user = UserMapper.Instance.mapUserDtoToUser(userDto);
//
//        //then
//        assertNotNull(user);
//        assertEquals(userDto.getFirstName(), user.getFirstName());
//        assertEquals(userDto.getLastName(), user.getLastName());
//        assertEquals(userDto.getEmail(), user.getEmail());
//        assertEquals(userDto.getPassword(), user.getPassword());
//    }

}
