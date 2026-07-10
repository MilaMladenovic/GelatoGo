/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.icecreamdeliveryapp.mapper.impl;

import java.util.List;
import njt.icecreamdeliveryapp.dto.impl.OrderDto;
import njt.icecreamdeliveryapp.dto.impl.OrderItemDto;
import njt.icecreamdeliveryapp.entity.impl.Order;
import njt.icecreamdeliveryapp.entity.impl.User;
import njt.icecreamdeliveryapp.mapper.DtoEntityMapper;
import org.springframework.stereotype.Component;

/**
 *
 * @author milam
 */
@Component
public class OrderMapper implements DtoEntityMapper<OrderDto, Order> {

    private final OrderItemMapper itemMapper;

    public OrderMapper(OrderItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    @Override
    public OrderDto toDto(Order order) {

        List<OrderItemDto> items = order.getItems()
                .stream()
                .map(itemMapper::toDto)
                .toList();

        return new OrderDto(
                order.getId(),
                order.getStatus(),
                order.getCreatedAt(),
                order.getNote(),
                order.getDeliveryAddress(),
                order.getUser().getId(),
                items
        );
    }

    @Override
    public Order toEntity(OrderDto dto) {

        Order order = new Order();

        order.setId(dto.getId());

        if (dto.getStatus() != null) {
            order.setStatus(dto.getStatus());
        }

        order.setNote(dto.getNote());
        order.setDeliveryAddress(dto.getDeliveryAddress());

        if (dto.getUserId() != null) {
            order.setUser(new User(dto.getUserId()));
        }

        if (dto.getItems() != null) {
            dto.getItems()
                    .forEach(i -> order.addItem(itemMapper.toEntity(i)));
        }

        return order;
    }
}
