package com.demo.cymelle_backend.services;

import com.demo.cymelle_backend.dtos.OrderItemRequest;
import com.demo.cymelle_backend.dtos.OrderRequest;
import com.demo.cymelle_backend.entities.*;
import com.demo.cymelle_backend.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public String placeOrder(OrderRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = Order.builder()
                .orderNumber("ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase()) // Generate random order number
                .status(Order.OrderStatus.PENDING)
                .paymentMethod(request.getPaymentMethod())
                .user(user)
                .build();

        for (OrderItemRequest itemReq : request.getItems()) {
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + itemReq.getProductId()));

            if (product.getStockQuantity() < itemReq.getQuantity()) {
                throw new RuntimeException("Product no longer available in store: " + product.getName());
            }

            // Deduct Stock
            product.setStockQuantity(product.getStockQuantity() - itemReq.getQuantity());

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(itemReq.getQuantity())
                    .build();

            order.addOrderItem(orderItem);
        }

        orderRepository.save(order);

        return order.getOrderNumber();
    }

    @Transactional(readOnly = true)
    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

}