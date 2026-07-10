/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.icecreamdeliveryapp.mapper.impl;

import njt.icecreamdeliveryapp.dto.impl.IceCreamDto;
import njt.icecreamdeliveryapp.entity.impl.IceCream;
import njt.icecreamdeliveryapp.entity.impl.IceCreamShop;
import njt.icecreamdeliveryapp.mapper.DtoEntityMapper;
import org.springframework.stereotype.Component;
import java.util.Base64;

/**
 *
 * @author milam
 */
@Component
public class IceCreamMapper implements DtoEntityMapper<IceCreamDto, IceCream> {

    @Override
    public IceCreamDto toDto(IceCream e) {
        Long iceCreamShopId = e.getIceCreamShop() != null ? e.getIceCreamShop().getId() : null;
        String image = null;

        if (e.getImage() != null) {
            image = Base64.getEncoder().encodeToString(e.getImage());
        }
        return new IceCreamDto(
                e.getId(), 
                e.getName(), 
                e.getDescription(), 
                e.getPrice(), 
                image, 
                iceCreamShopId);
    }

    @Override
    public IceCream toEntity(IceCreamDto t) {
        IceCreamShop iceCreamShop = t.getIceCreamShopId() != null ? new IceCreamShop(t.getIceCreamShopId()) : null;
        byte[] image = null;

        if (t.getImage() != null && !t.getImage().isBlank()) {
            image = Base64.getDecoder().decode(t.getImage());
        }
        return new IceCream(
                t.getId(),
                t.getName(),
                t.getDescription(),
                t.getPrice(),
                image,
                iceCreamShop
        );
    }

}
