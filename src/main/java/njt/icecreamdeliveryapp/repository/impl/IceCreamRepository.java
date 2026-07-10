/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.icecreamdeliveryapp.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import njt.icecreamdeliveryapp.entity.impl.IceCream;
import njt.icecreamdeliveryapp.repository.MyAppRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author milam
 */

@Repository
public class IceCreamRepository implements MyAppRepository<IceCream, Long>{
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public List<IceCream> findAll() {
        return entityManager.createQuery("SELECT i FROM IceCream i", IceCream.class).getResultList();
    }

    @Override
    public IceCream findByID(Long id) throws Exception {
        IceCream iceCream = entityManager.find(IceCream.class, id);
        if(iceCream == null){
            throw new Exception("Sladoled nije pronadjen");
        }
        return iceCream;
    }

    @Override
    @Transactional
    public void save(IceCream entity) {
        if(entity.getId() == null){
            entityManager.persist(entity);
        } else {
            entityManager.merge(entity);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        IceCream iceCream = entityManager.find(IceCream.class, id);
        if(iceCream != null){
            entityManager.remove(iceCream);
        }
    }

    public List<IceCream> findByIceCreamShop(Long iceCreamShopId) {
        return entityManager.createQuery("SELECT i FROM IceCream i WHERE i.iceCreamShop.id = :id", IceCream.class)
                 .setParameter("id", iceCreamShopId)
                 .getResultList();
    }
    
}
