/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.icecreamdeliveryapp.dto.impl;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import njt.icecreamdeliveryapp.dto.Dto;

/**
 *
 * @author milam
 */
public class IceCreamDto implements Dto {

    private Long id;
    @NotEmpty(message = "Name is required.")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters.")
    private String name;
    @Size(max = 500, message = "Description can be at most 500 characters.")
    private String description;
    @NotNull(message = "Price is required.")
    @Positive(message = "Price must be greater than zero.")
    private BigDecimal price;
    private String image;
    private Long IceCreamShopId;

    public IceCreamDto() {
    }

    public IceCreamDto(Long id, String name, String description, BigDecimal price, String image, Long IceCreamShopId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.IceCreamShopId = IceCreamShopId;
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

    public Long getIceCreamShopId() {
        return IceCreamShopId;
    }

    public void setIceCreamShopId(Long IceCreamShopId) {
        this.IceCreamShopId = IceCreamShopId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
