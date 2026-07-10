/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.icecreamdeliveryapp.mapper.impl;

import java.math.BigDecimal;
import java.util.Base64;
import njt.icecreamdeliveryapp.dto.impl.OrderItemDto;
import njt.icecreamdeliveryapp.entity.impl.IceCream;
import njt.icecreamdeliveryapp.entity.impl.OrderItem;
import njt.icecreamdeliveryapp.mapper.DtoEntityMapper;
import org.springframework.stereotype.Component;

/**
 *
 * @author milam
 */
@Component
public class OrderItemMapper implements DtoEntityMapper<OrderItemDto, OrderItem> {

    @Override
    public OrderItemDto toDto(OrderItem entity) {

        OrderItemDto dto = new OrderItemDto();
        dto.setId(entity.getId());
        dto.setIceCreamId(entity.getIceCream().getId());
        dto.setQuantity(entity.getQuantity());
        dto.setUnitPrice(entity.getUnitPrice());
        dto.setIceCreamName(entity.getIceCream().getName());

        if (entity.getIceCream().getImage() != null) {
            dto.setImage(Base64.getEncoder().encodeToString(entity.getIceCream().getImage()));
        }
        dto.setTotalPrice(entity.getUnitPrice().multiply(BigDecimal.valueOf(entity.getQuantity())));
        return dto;
    }

    @Override
    public OrderItem toEntity(OrderItemDto dto) {

        OrderItem item = new OrderItem();

        item.setId(dto.getId());
        item.setIceCream(new IceCream(dto.getIceCreamId()));
        item.setQuantity(dto.getQuantity());
        item.setUnitPrice(dto.getUnitPrice());

        return item;
    }
}
