/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.icecreamdeliveryapp.entity.impl;

import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import njt.icecreamdeliveryapp.entity.MyEntity;

/**
 *
 * @author milam
 */

@Entity
@Table(name = "ice_cream_shops")
public class IceCreamShop implements MyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Ice cream shop name is required.")
    @Size(max = 100, message = "Ice cream shop name cannot exceed 100 characters.")
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank(message = "Address is required.")
    @Size(max = 150, message = "Address cannot exceed 150 characters.")
    @Column(nullable = false, length = 150)
    private String address;

    @OneToMany(mappedBy = "iceCreamShop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IceCream> iceCreams;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;

    public IceCreamShop() {
    }

    public IceCreamShop(Long id) {
        this.id = id;
    }

    public IceCreamShop(Long id, String name, String address, List<IceCream> iceCreams, byte[] image) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.iceCreams = iceCreams;
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

    public List<IceCream> getIceCreams() {
        return iceCreams;
    }

    public void setIceCreams(List<IceCream> iceCreams) {
        this.iceCreams = iceCreams;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

}
