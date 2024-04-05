package com.ml.hotel_ml_auth_service.mapper;

import com.ml.hotel_ml_auth_service.dto.PrivilegeDto;
import com.ml.hotel_ml_auth_service.model.Privilege;
import org.instancio.Instancio;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PrivilegeMapperTest {

    void shouldMapPrivilegeToPrivilegeDto(){
        //given
        Privilege privilege = Instancio.create(Privilege.class);

        //when
        PrivilegeDto privilegeDto = PrivilegeMapper.Instance.privilegeDto(privilege);

        //then
        assertNotNull(privilegeDto);
        assertEquals(privilege.getUuid(), privilegeDto.getUuid());
        assertEquals(privilege.getName(), privilegeDto.getName());
        assertEquals(privilege.getRoles(), privilegeDto.getRoles());
    }

}
