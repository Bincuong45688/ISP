package com.example.isp.mapper;

import com.example.isp.dto.request.RegisterCustomerRequest;
import com.example.isp.dto.request.UpdateCustomerRequest;
import com.example.isp.dto.response.CustomerResponse;
import com.example.isp.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(target = "customerId", ignore = true)
    @Mapping(target = "account", ignore = true)
    Customer toEntity(RegisterCustomerRequest request);

    @Mapping(source = "customerId", target = "id")
    @Mapping(source = "account.username", target = "username")
    @Mapping(source = "account.email", target = "email")
    @Mapping(source = "account.phone", target = "phone")
    CustomerResponse toResponse(Customer customer);

    @Mapping(source = "fullName", target = "customerName")
    @Mapping(target = "account", ignore = true)
    @Mapping(target = "customerId", ignore = true)
    void updateCustomerFromRequest(UpdateCustomerRequest request, @MappingTarget Customer customer);
}
