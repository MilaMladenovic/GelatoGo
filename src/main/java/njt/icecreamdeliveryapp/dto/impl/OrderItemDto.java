/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.icecreamdeliveryapp.dto.impl;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import njt.icecreamdeliveryapp.dto.Dto;

/**
 *
 * @author milam
 */
public class OrderItemDto implements Dto {

    private Long id;

    @NotNull(message = "Ice cream ID is required.")
    private Long iceCreamId;

    private String iceCreamName;

    private String image;

    @Min(value = 1, message = "Quantity must be at least 1.")
    private int quantity;

    @NotNull(message = "Unit price is required.")
    @DecimalMin(
            value = "0.01",
            message = "Unit price must be greater than zero."
    )
    @Digits(
            integer = 8,
            fraction = 2,
            message = "Unit price must contain up to 8 integer digits and 2 decimal places."
    )
    private BigDecimal unitPrice;

    private BigDecimal totalPrice;

    public OrderItemDto() {
    }

    public OrderItemDto(Long id, Long iceCreamId, String iceCreamName, String image, int quantity, BigDecimal unitPrice, BigDecimal totalPrice) {
        this.id = id;
        this.iceCreamId = iceCreamId;
        this.iceCreamName = iceCreamName;
        this.image = image;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIceCreamId() {
        return iceCreamId;
    }

    public void setIceCreamId(Long iceCreamId) {
        this.iceCreamId = iceCreamId;
    }

    public String getIceCreamName() {
        return iceCreamName;
    }

    public void setIceCreamName(String iceCreamName) {
        this.iceCreamName = iceCreamName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    
}
