/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.icecreamdeliveryapp.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import njt.icecreamdeliveryapp.entity.impl.Order;
import njt.icecreamdeliveryapp.repository.MyAppRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author milam
 */
@Repository
public class OrderRepository implements MyAppRepository<Order, Long> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Order> findAll() {
        return em.createQuery("SELECT o FROM Order o ORDER BY o.createdAt DESC", Order.class).getResultList();
    }

    @Override
    public Order findByID(Long id) throws Exception {
        Order o = em.find(Order.class, id);
        if (o == null) {
            throw new Exception("Order not found: " + id);
        }
        return o;
    }

    @Override
    @Transactional
    public void save(Order entity) {
        if (entity.getId() == null) {
            em.persist(entity);
        } else {
            em.merge(entity);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Order o = em.find(Order.class, id);
        if (o != null) {
            em.remove(o); // orphanRemoval briše i stavke
        }
    }

    public List<Order> findByUserId(Long userId) {

        return em.createQuery("""
            SELECT DISTINCT o 
            FROM Order o 
            LEFT JOIN FETCH o.items i 
            LEFT JOIN FETCH i.iceCream 
            WHERE o.user.id = :userId 
            ORDER BY o.createdAt DESC
        """, Order.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<Order> findByUsername(String username) {

        return em.createQuery("""
            SELECT DISTINCT o
            FROM Order o
            LEFT JOIN FETCH o.items i
            LEFT JOIN FETCH i.iceCream
            WHERE o.user.username = :username
            ORDER BY o.createdAt DESC
        """, Order.class)
                .setParameter("username", username)
                .getResultList();
    }
}
