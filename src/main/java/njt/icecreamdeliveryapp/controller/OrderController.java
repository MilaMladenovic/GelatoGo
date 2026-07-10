/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.icecreamdeliveryapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import njt.icecreamdeliveryapp.dto.impl.OrderDto;
import njt.icecreamdeliveryapp.entity.impl.OrderStatus;
import njt.icecreamdeliveryapp.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author milam
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/order")
@Tag(name = "Orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> all() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> byId(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Purchase history for user")
    public ResponseEntity<List<OrderDto>> getUserOrders(@PathVariable Long userId) {
        return ResponseEntity.ok(service.findByUser(userId));
    }

    @GetMapping("/my")
    @Operation(summary = "Purchase history of logged user")
    public ResponseEntity<List<OrderDto>> myOrders(Authentication authentication) {

        return ResponseEntity.ok(
                service.findMyOrders(authentication.getName())
        );
    }

    @PostMapping
    @Operation(summary = "Create order with items in a single transaction")
    public ResponseEntity<OrderDto> create(@Valid @RequestBody @NotNull OrderDto dto) {
        try {
            OrderDto saved = service.create(dto);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Create order failed: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderDto> updateStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
        try {
            return new ResponseEntity<>(service.updateStatus(id, status), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok("Order deleted successfully.");
    }
}
