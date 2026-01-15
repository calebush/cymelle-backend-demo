package com.demo.cymelle_backend.controllers;

import com.demo.cymelle_backend.dtos.OrderRequest;
import com.demo.cymelle_backend.entities.Order;
import com.demo.cymelle_backend.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Order Controller", description = "Endpoints for placing and managing orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Place a new order")
    public ResponseEntity<?> placeOrder(@Valid @RequestBody OrderRequest request) {
        String orderNumber = orderService.placeOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "message", "Order placed successfully",
                "orderNumber", orderNumber
        ));
    }


    @GetMapping
    @Operation(summary = "Admin: Fetch all orders")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<Order>> getAllOrders(
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Pageable sanitizedPageable = pageable.getSort().stream()
                .anyMatch(order -> order.getProperty().contains("["))
                ? PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending())
                : pageable;

        return ResponseEntity.ok(orderService.getAllOrders(sanitizedPageable));
    }


}