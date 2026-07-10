/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.icecreamdeliveryapp.service;

import java.util.List;
import java.util.stream.Collectors;
import njt.icecreamdeliveryapp.dto.impl.IceCreamShopDto;
import njt.icecreamdeliveryapp.entity.impl.IceCreamShop;
import njt.icecreamdeliveryapp.mapper.impl.IceCreamShopMapper;
import njt.icecreamdeliveryapp.repository.impl.IceCreamShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Base64;
/**
 *
 * @author milam
 */
@Service
public class IceCreamShopService {

    private final IceCreamShopRepository iceCreamShopRepository;
    private final IceCreamShopMapper iceCreamShopMapper;

    @Autowired
    public IceCreamShopService(IceCreamShopRepository iceCreamShopRepository, IceCreamShopMapper iceCreamShopMapper) {
        this.iceCreamShopRepository = iceCreamShopRepository;
        this.iceCreamShopMapper = iceCreamShopMapper;
    }

    public List<IceCreamShopDto> findAll() {
        return iceCreamShopRepository.findAll().stream().map(iceCreamShopMapper::toDto).collect(Collectors.toList());
    }

    public IceCreamShopDto findById(Long id) throws Exception {
        return iceCreamShopMapper.toDto(iceCreamShopRepository.findByID(id));
    }

    public IceCreamShopDto create(IceCreamShopDto iceCreamShopDto) {
        IceCreamShop iceCreamShop = iceCreamShopMapper.toEntity(iceCreamShopDto);
        iceCreamShopRepository.save(iceCreamShop);
        return iceCreamShopMapper.toDto(iceCreamShop);
    }

    public void deleteById(Long id) {
        iceCreamShopRepository.deleteById(id);
    }

    public IceCreamShopDto update(IceCreamShopDto dto) throws Exception {
        IceCreamShop shop = iceCreamShopRepository
                .findByID(dto.getId());
        shop.setName(dto.getName());
        shop.setAddress(dto.getAddress());

        if (dto.getImage() != null && !dto.getImage().isBlank()) {
            shop.setImage(Base64.getDecoder().decode(dto.getImage()));
        }
        iceCreamShopRepository.save(shop);
        return iceCreamShopMapper.toDto(shop);
    }
}
