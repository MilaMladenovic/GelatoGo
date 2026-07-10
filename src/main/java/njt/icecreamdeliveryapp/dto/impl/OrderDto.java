/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.icecreamdeliveryapp.dto.impl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import njt.icecreamdeliveryapp.dto.Dto;
import njt.icecreamdeliveryapp.entity.impl.OrderStatus;

/**
 *
 * @author milam
 */

public class OrderDto implements Dto {

    private Long id;

    private OrderStatus status;

    private LocalDateTime createdAt;

    @Size(max = 500, message = "Note cannot exceed 500 characters.")
    private String note;

    @NotBlank(message = "Delivery address is required.")
    @Size(max = 150, message = "Delivery address cannot exceed 150 characters.")
    private String deliveryAddress;

    @NotNull(message = "User ID is required.")
    private Long userId;

    @Valid
    @NotEmpty(message = "Order must contain at least one item.")
    private List<OrderItemDto> items;

    public OrderDto() {
    }

    public OrderDto(Long id, OrderStatus status, LocalDateTime createdAt, String note, String deliveryAddress, Long userId, List<OrderItemDto> items) {
        this.id = id;
        this.status = status;
        this.createdAt = createdAt;
        this.note = note;
        this.deliveryAddress = deliveryAddress;
        this.userId = userId;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

}
