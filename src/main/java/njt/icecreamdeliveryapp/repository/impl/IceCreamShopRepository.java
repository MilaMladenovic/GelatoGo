/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.icecreamdeliveryapp.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import njt.icecreamdeliveryapp.entity.impl.IceCreamShop;
import njt.icecreamdeliveryapp.repository.MyAppRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author milam
 */

@Repository
public class IceCreamShopRepository implements MyAppRepository<IceCreamShop, Long> {

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public List<IceCreamShop> findAll() {
        return entityManager.createQuery("SELECT i FROM IceCreamShop i", IceCreamShop.class).getResultList();
    }

    @Override
    public IceCreamShop findByID(Long id) throws Exception {
        IceCreamShop shop = entityManager.find(IceCreamShop.class, id);
        if(shop == null){
            throw new Exception("Ice cream shop not found.");
        }
        return shop;
    }

    @Override
    @Transactional
    public void save(IceCreamShop entity) {
        if(entity.getId() == null){
            entityManager.persist(entity);
        } else {
            entityManager.merge(entity);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        IceCreamShop shop = entityManager.find(IceCreamShop.class, id);
        if(shop != null){
            entityManager.remove(shop);
        }
    }
    
}
