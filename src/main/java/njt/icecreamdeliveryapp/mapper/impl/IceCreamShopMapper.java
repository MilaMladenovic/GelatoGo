/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.icecreamdeliveryapp.mapper.impl;

import njt.icecreamdeliveryapp.dto.impl.IceCreamShopDto;
import njt.icecreamdeliveryapp.entity.impl.IceCreamShop;
import njt.icecreamdeliveryapp.mapper.DtoEntityMapper;
import org.springframework.stereotype.Component;
import java.util.Base64;

/**
 *
 * @author milam
 */
@Component
public class IceCreamShopMapper implements DtoEntityMapper<IceCreamShopDto, IceCreamShop> {

    @Override
    public IceCreamShopDto toDto(IceCreamShop e) {
        String image = null;
        if (e.getImage() != null) {
            image = Base64.getEncoder().encodeToString(e.getImage());
        }
        return new IceCreamShopDto(
                e.getId(),
                e.getName(),
                e.getAddress(),
                image
        );
    }

    @Override
    public IceCreamShop toEntity(IceCreamShopDto t) {
        byte[] image = null;
        if (t.getImage() != null && !t.getImage().isBlank()) {
            image = Base64.getDecoder().decode(t.getImage());
        }
        return new IceCreamShop(
                t.getId(),
                t.getName(),
                t.getAddress(),
                null,
                image
        );
    }
}
