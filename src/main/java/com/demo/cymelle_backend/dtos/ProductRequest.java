package com.demo.cymelle_backend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductRequest {
    @NotBlank( message = "Name is required")
    private String name;

    @NotBlank( message = "Description is required")
    private String description;

    @NotNull( message = "Price is required")
    private BigDecimal price = BigDecimal.ZERO;

    @NotNull( message = "Stock quantity is required")
    private Integer stockQuantity;
}
