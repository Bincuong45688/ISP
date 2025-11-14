package com.example.isp.mapper;


import com.example.isp.dto.request.CreateStaffRequest;
import com.example.isp.dto.response.ManagerResponse;
import com.example.isp.model.Manager;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface ManagerMapper {

    @Mapping(target = "managerId", ignore = true)
    @Mapping(target = "account", ignore = true)
    Manager toEntity(CreateStaffRequest req);

    @Mapping(source = "managerId", target = "id")
    @Mapping(source = "account.username", target = "username")
    @Mapping(source = "account.email", target = "email")
    @Mapping(source = "account.phone", target = "phone")
    @Mapping(source = "account.role", target = "role")
    ManagerResponse toResponse(Manager manager);
}