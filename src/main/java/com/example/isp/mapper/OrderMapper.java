package com.example.isp.mapper;

import com.example.isp.dto.response.OrderDetailResponse;
import com.example.isp.dto.response.OrderResponse;
import com.example.isp.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    // Map Order -> OrderResponse (cho viewOrder)
    @Mapping(source = "createdAt", target = "orderDate")
    @Mapping(source = "receiverName", target = "receiverName")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "voucher.code", target = "voucherCode")
    @Mapping(source = "discountAmount", target = "discountAmount")
    @Mapping(source = "totalAmount", target = "totalPrice")
    @Mapping(source = "note", target = "note")
    @Mapping(source = "status", target = "status")
    OrderResponse toOrderResponse(Order order);

    // Map Order → OrderDetailResponse (cho viewOrderDetail)
    @Mapping(source = "createdAt", target = "orderDate")
    @Mapping(source = "status", target = "orderStatus")
    @Mapping(source = "voucher.code", target = "voucherCode")
    @Mapping(source = "discountAmount", target = "discountAmount")
    @Mapping(source = "totalAmount", target = "totalPrice")
    @Mapping(target = "items", ignore = true)
    OrderDetailResponse toOrderDetailResponse(Order order);

}
