/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.icecreamdeliveryapp.entity.impl;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 *
 * @author milam
 */
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Order is required.")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @NotNull(message = "Ice cream is required.")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "icecream_id", nullable = false)
    private IceCream iceCream;

    @Min(value = 1, message = "Quantity must be at least 1.")
    @Column(nullable = false)
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
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    public OrderItem() {
    }

    public OrderItem(Long id) {
        this.id = id;
    }

    public OrderItem(Long id, Order order, IceCream iceCream, int quantity, BigDecimal unitPrice) {
        this.id = id;
        this.order = order;
        this.iceCream = iceCream;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public IceCream getIceCream() {
        return iceCream;
    }

    public void setIceCream(IceCream iceCream) {
        this.iceCream = iceCream;
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

}
