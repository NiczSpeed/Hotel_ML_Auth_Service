package com.ml.hotel_ml_auth_service.mapper;

import com.ml.hotel_ml_auth_service.dto.PrivilegeDto;
import com.ml.hotel_ml_auth_service.model.Privilege;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PrivilegeMapper {

    PrivilegeMapper Instance = Mappers.getMapper(PrivilegeMapper.class);

    @Mapping(source = "uuid", target = "uuid")
    PrivilegeDto privilegeDto (Privilege privilege);

}
