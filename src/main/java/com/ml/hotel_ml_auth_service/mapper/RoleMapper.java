package com.ml.hotel_ml_auth_service.mapper;

import com.ml.hotel_ml_auth_service.dto.RoleDto;
import com.ml.hotel_ml_auth_service.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper
public interface RoleMapper {

    RoleMapper Instance = Mappers.getMapper(RoleMapper.class);

    RoleDto mapRoleToRoleDto(Role role);
    Set<RoleDto> mapRoleSetToRoleDtoSet(Set<Role> roleSet);
}
