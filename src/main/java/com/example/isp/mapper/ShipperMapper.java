package com.example.isp.mapper;

import com.example.isp.dto.response.ShipperResponse;
import com.example.isp.model.Shipper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShipperMapper {
    @Mapping(source = "account.email", target = "email")
    @Mapping(source = "account.phone", target = "phone")
    ShipperResponse toResponse(Shipper shipper);
}
