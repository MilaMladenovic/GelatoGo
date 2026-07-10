/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.icecreamdeliveryapp.service;

import java.util.List;
import java.util.stream.Collectors;
import njt.icecreamdeliveryapp.dto.impl.IceCreamDto;
import njt.icecreamdeliveryapp.dto.impl.IceCreamShopDto;
import njt.icecreamdeliveryapp.entity.impl.IceCream;
import njt.icecreamdeliveryapp.entity.impl.IceCreamShop;
import njt.icecreamdeliveryapp.mapper.impl.IceCreamMapper;
import njt.icecreamdeliveryapp.repository.impl.IceCreamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author milam
 */
@Service
public class IceCreamService {
    private final IceCreamRepository iceCreamRepository;
    private final IceCreamMapper iceCreamMapper;

    @Autowired
    public IceCreamService(IceCreamRepository iceCreamRepository, IceCreamMapper iceCreamMapper) {
        this.iceCreamRepository = iceCreamRepository;
        this.iceCreamMapper = iceCreamMapper;
    }
    
    public List<IceCreamDto> findAll(){
        return iceCreamRepository.findAll()
                .stream()
                .map(iceCreamMapper::toDto)
                .collect(Collectors.toList());
    }
    
    public IceCreamDto findById(Long id)throws Exception{
        return iceCreamMapper.toDto(iceCreamRepository.findByID(id));
    }

    public IceCreamDto create(IceCreamDto iceCreamDto) {
        IceCream iceCream = iceCreamMapper.toEntity(iceCreamDto);
        iceCreamRepository.save(iceCream);
        return iceCreamMapper.toDto(iceCream);
    }
    
    public void deleteById(Long id) {
        iceCreamRepository.deleteById(id);
    }

    public IceCreamDto update(IceCreamDto iceCreamDto) throws Exception {
        IceCream updated = iceCreamMapper.toEntity(iceCreamDto);
        iceCreamRepository.save(updated); 
        return iceCreamMapper.toDto(updated);
    }

    public List<IceCreamDto> findByIceCreamShop(Long iceCreamShopId) {
        return iceCreamRepository.findByIceCreamShop(iceCreamShopId)
            .stream()
            .map(iceCreamMapper::toDto)
            .collect(Collectors.toList());
    }
    
}
