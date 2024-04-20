package com.ml.hotel_ml_auth_service.mapper;

import com.ml.hotel_ml_auth_service.dto.PrivilegeDto;
import com.ml.hotel_ml_auth_service.model.Privilege;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper
public interface PrivilegeMapper {

    PrivilegeMapper Instance = Mappers.getMapper(PrivilegeMapper.class);

    PrivilegeDto mapPrivilegeToPrivilegeDto(Privilege privilege);
    Privilege mapPrivilegeDtoToPrivilege(PrivilegeDto privilegeDto);
    List<PrivilegeDto> mapPrivilegeListToPrivilegeDtoList(List<Privilege> privileges);
    List<Privilege> mapPrivilegeDtoListToPrivilege(List<PrivilegeDto> privilegesDto);
    Set<PrivilegeDto> mapPrivilegeSetToPrivilegeDtoSet(Set<Privilege> privileges);


}
