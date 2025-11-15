package com.example.isp.mapper;

import com.example.isp.dto.request.CreateStaffRequest;
import com.example.isp.dto.response.StaffResponse;
import com.example.isp.model.Staff;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StaffMapper {
    @Mapping(source = "account.email", target = "email")
    @Mapping(source = "account.phone", target = "phone")
    StaffResponse toResponse(Staff staff);
}

