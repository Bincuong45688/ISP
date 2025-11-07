package com.example.isp.mapper;

import com.example.isp.dto.response.AssignShipperResponse;
import com.example.isp.dto.response.OrderDetailResponse;
import com.example.isp.dto.response.OrderResponse;
import com.example.isp.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    // Map Order -> OrderResponse (list/view)
    @Mapping(source = "orderCode",        target = "orderCode")
    @Mapping(source = "createdAt",        target = "orderDate")
    @Mapping(source = "receiverName",     target = "receiverName")
    @Mapping(source = "phone",            target = "phone")
    @Mapping(source = "address",          target = "address")
    @Mapping(source = "voucher.code",     target = "voucherCode")
    @Mapping(source = "discountAmount",   target = "discountAmount")
    @Mapping(source = "totalAmount",      target = "totalPrice")
    @Mapping(source = "note",             target = "note")
    @Mapping(source = "status",           target = "status")
    // null-safe cho shipperName
    @Mapping(target = "shipperName", expression =
            "java(order.getShipper() != null ? order.getShipper().getShipperName() : null)")
    // Thêm 3 field POD để không còn null
    @Mapping(source = "proofImageUrl",    target = "proofImageUrl")
    @Mapping(source = "proofUploadedAt",  target = "proofUploadedAt")
    @Mapping(source = "proofUploadedBy",  target = "proofUploadedBy")
    OrderResponse toOrderResponse(Order order);

    // Map Order -> OrderDetailResponse
    @Mapping(source = "orderCode",        target = "orderCode")
    @Mapping(source = "createdAt",        target = "orderDate")
    @Mapping(source = "status",           target = "orderStatus")
    @Mapping(source = "voucher.code",     target = "voucherCode")
    @Mapping(source = "discountAmount",   target = "discountAmount")
    @Mapping(source = "totalAmount",      target = "totalPrice")
    @Mapping(target = "items",            ignore = true)
    OrderDetailResponse toOrderDetailResponse(Order order);

    @Mapping(target = "shipperName", expression =
            "java(order.getShipper() != null ? order.getShipper().getShipperName() : null)")
    AssignShipperResponse toAssignResponse(Order order);
}
