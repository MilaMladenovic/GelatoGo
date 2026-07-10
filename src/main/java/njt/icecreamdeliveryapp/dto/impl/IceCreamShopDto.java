/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.icecreamdeliveryapp.dto.impl;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import njt.icecreamdeliveryapp.dto.Dto;

/**
 *
 * @author milam
 */
public class IceCreamShopDto implements Dto {

    private Long id;
    @NotEmpty(message = "Name is required.")
    private String name;
    @NotBlank(message = "Address is required.")
    @Size(max = 200, message = "Address cannot be longer than 200 characters.")
    private String address;
    private String image;

    public IceCreamShopDto() {
    }

    public IceCreamShopDto(Long id, String name, String address, String image) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.image = image;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
