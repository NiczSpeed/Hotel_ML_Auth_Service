package com.ml.hotel_ml_auth_service.mapper;

import com.ml.hotel_ml_auth_service.dto.RoleDto;
import com.ml.hotel_ml_auth_service.model.Role;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class RoleMapperTest {

    @Test
    void shouldMapRoleToRoleDto(){
        //given
        Role role = Instancio.create(Role.class);

        //when
        RoleDto roleDto = RoleMapper.Instance.roleToRoleDto(role);

        //then
        assertNotNull(roleDto);
        assertEquals(role.getUuid(), roleDto.getUuid());
        assertEquals(role.getName(), roleDto.getName());
        assertEquals(role.getUsers(), roleDto.getUsers());
        assertEquals(role.getPrivileges(), roleDto.getPrivileges());
    }

}
