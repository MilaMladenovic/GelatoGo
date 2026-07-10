/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.icecreamdeliveryapp.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import njt.icecreamdeliveryapp.dto.impl.OrderDto;
import njt.icecreamdeliveryapp.dto.impl.OrderItemDto;
import njt.icecreamdeliveryapp.entity.impl.IceCream;
import njt.icecreamdeliveryapp.entity.impl.Order;
import njt.icecreamdeliveryapp.entity.impl.OrderItem;
import njt.icecreamdeliveryapp.entity.impl.OrderStatus;
import njt.icecreamdeliveryapp.entity.impl.User;
import njt.icecreamdeliveryapp.mapper.impl.OrderMapper;
import njt.icecreamdeliveryapp.repository.impl.OrderRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author milam
 */
@Service
public class OrderService {

    private final OrderRepository orders;
    private final OrderMapper mapper;

    @PersistenceContext
    private EntityManager em;

    public OrderService(OrderRepository orders, OrderMapper mapper) {
        this.orders = orders;
        this.mapper = mapper;
    }

    public List<OrderDto> findAll() {
        return orders.findAll().stream().map(mapper::toDto).toList();
    }

    public OrderDto findById(Long id) throws Exception {
        return mapper.toDto(orders.findByID(id));
    }

    //Kreira narudžbinu i SVE njene stavke u JEDNOJ transakciji
    @Transactional
    public OrderDto create(OrderDto dto) throws Exception {
        Order order = new Order();
        order.setStatus(dto.getStatus() != null ? dto.getStatus() : OrderStatus.CREATED);
        order.setNote(dto.getNote());
        order.setDeliveryAddress(dto.getDeliveryAddress());

        if (dto.getUserId() == null) {
            throw new IllegalArgumentException("User ID is required.");
        }
        order.setUser(em.getReference(User.class, dto.getUserId()));

        if (dto.getItems() == null || dto.getItems().isEmpty()) {
            throw new IllegalArgumentException("An order must contain at least one item.");
        }

        for (OrderItemDto it : dto.getItems()) {
            OrderItem oi = new OrderItem();
            IceCream i = em.find(IceCream.class, it.getIceCreamId());
            if (i == null) {
                throw new IllegalArgumentException("Ice cream not found.");
            }
            oi.setIceCream(i);
            oi.setQuantity(it.getQuantity());

            BigDecimal price = it.getUnitPrice();

            if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
                price = i.getPrice();
            }
            oi.setUnitPrice(price);
            order.addItem(oi);
        }

        orders.save(order);
        return mapper.toDto(order);
    }

     //Promena statusa (CONFIRMED/CANCELED/COMPLETED)
    @Transactional
    public OrderDto updateStatus(Long id, OrderStatus status) throws Exception {
        Order o = orders.findByID(id);
        if (status == null) {
            throw new IllegalArgumentException("Order status is required.");
        }
        o.setStatus(status);
        orders.save(o);
        return mapper.toDto(o);
    }


    //Brisanje narudžbine + stavki u jednoj transakciji (orphanRemoval)
    @Transactional
    public void deleteById(Long id) {
        Order o = em.find(Order.class, id);
        if (o == null) {
            throw new EntityNotFoundException("Order not found.");
        }
        em.remove(o);
    }

    public List<OrderDto> findByUser(Long userId) {

        return orders.findByUserId(userId)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public List<OrderDto> findMyOrders(String username) {

        return orders.findByUsername(username)
                .stream()
                .map(mapper::toDto)
                .toList();
    }
}
