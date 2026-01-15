package com.demo.cymelle_backend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RideRequest {
    @NotBlank(message = "Pickup location is required")
    private String pickupLocation;

    @NotBlank(message = "Dropoff location is required")
    private String dropoffLocation;

    private BigDecimal fare;
}
