package com.ml.hotel_ml_auth_service.mapper;

import com.ml.hotel_ml_auth_service.dto.RoleDto;
import com.ml.hotel_ml_auth_service.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {

    RoleMapper Instance = Mappers.getMapper(RoleMapper.class);

    @Mapping(source = "uuid", target = "uuid")
    RoleDto roleToRoleDto(Role role);

}
