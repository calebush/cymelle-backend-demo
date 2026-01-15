package com.demo.cymelle_backend.dtos;


import com.demo.cymelle_backend.entities.Order;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    @NotEmpty(message = "Order must have at least one item")
    private List<OrderItemRequest> items;

    @NotNull(message = "Payment method is required")
    private Order.PaymentMethod paymentMethod;

}

