package com.example.isp.mapper;

import com.example.isp.dto.response.ShipperResponse;
import com.example.isp.model.Shipper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShipperMapper {
    ShipperResponse toResponse(Shipper shipper);
}
