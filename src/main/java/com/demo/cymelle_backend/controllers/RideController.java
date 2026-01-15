package com.demo.cymelle_backend.controllers;

import com.demo.cymelle_backend.dtos.RideRequest;
import com.demo.cymelle_backend.entities.Ride;
import com.demo.cymelle_backend.services.RideService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rides")
@RequiredArgsConstructor
@Tag(name = "Ride Controller", description = "Endpoints for managing rides")
public class RideController {

    private final RideService rideService;

    @PostMapping
    @Operation(summary = "Request a new ride")
    public ResponseEntity<Ride> addRide(@Valid @RequestBody RideRequest request) {
        Ride ride = rideService.requestRide(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ride);
    }

}
