package com.ml.hotel_ml_auth_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ResetTokenMapper {

    ResetTokenMapper Instance = Mappers.getMapper(ResetTokenMapper.class);

}
