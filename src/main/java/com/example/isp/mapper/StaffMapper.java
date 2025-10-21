package com.example.isp.mapper;


import com.example.isp.dto.request.RegisterStaffRequest;
import com.example.isp.dto.response.StaffResponse;
import com.example.isp.model.Staff;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface StaffMapper {

    @Mapping(target = "staffId", ignore = true)
    @Mapping(target = "account", ignore = true)
    Staff toEntity(RegisterStaffRequest req);

    @Mapping(source = "staffId", target = "id")
    @Mapping(source = "account.username", target = "username")
    @Mapping(source = "account.email", target = "email")
    @Mapping(source = "account.phone", target = "phone")
    @Mapping(source = "account.role", target = "role")
    StaffResponse toResponse(Staff staff);
}