/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.icecreamdeliveryapp.entity.impl;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import njt.icecreamdeliveryapp.entity.MyEntity;

/**
 *
 * @author milam
 */
@Entity
@Table(name = "ice_creams")
public class IceCream implements MyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Ice cream name is required.")
    @Size(max = 100, message = "Ice cream name cannot exceed 100 characters.")
    @Column(nullable = false, length = 100)
    private String name;

    @Size(max = 500, message = "Description cannot exceed 500 characters.")
    @Column(length = 500)
    private String description;

    @NotNull
    @DecimalMin(value = "0.01", message = "Price must be greater than zero.")
    @Digits(
            integer = 8,
            fraction = 2,
            message = "Price must contain up to 8 integer digits and 2 decimal places.")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "image", columnDefinition = "LONGBLOB")
    private byte[] image;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ice_cream_shop_id", nullable = false)
    private IceCreamShop iceCreamShop;

    public IceCream() {
    }

    public IceCream(Long id) {
        this.id = id;
    }

    public IceCream(Long id, String name, String description, BigDecimal price, byte[] image, IceCreamShop iceCreamShop) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.iceCreamShop = iceCreamShop;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public IceCreamShop getIceCreamShop() {
        return iceCreamShop;
    }

    public void setIceCreamShop(IceCreamShop iceCreamShop) {
        this.iceCreamShop = iceCreamShop;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

}
