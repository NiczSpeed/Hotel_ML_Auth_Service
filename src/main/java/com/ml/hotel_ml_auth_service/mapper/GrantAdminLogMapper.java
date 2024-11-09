package com.ml.hotel_ml_auth_service.mapper;

import com.ml.hotel_ml_auth_service.dto.GrantAdminLogDto;
import com.ml.hotel_ml_auth_service.model.GrantAdminLog;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GrantAdminLogMapper {

    GrantAdminLogMapper Instance = Mappers.getMapper(GrantAdminLogMapper.class);

    GrantAdminLog mapGrantAdminLogDtoToGrantAdminLog(GrantAdminLogDto grantAdminLogDto);
    GrantAdminLogDto mapGrantAdminLogToGrantAdminLog(GrantAdminLog grantAdminLog);

}
