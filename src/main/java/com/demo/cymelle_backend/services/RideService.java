package com.demo.cymelle_backend.services;

import com.demo.cymelle_backend.dtos.RideRequest;
import com.demo.cymelle_backend.entities.Ride;
import com.demo.cymelle_backend.entities.User;
import com.demo.cymelle_backend.repositories.RideRepository;
import com.demo.cymelle_backend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideService {

    private final RideRepository rideRepository;
    private final UserRepository userRepository;

    @Transactional
    public Ride requestRide(RideRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Ride ride = Ride.builder()
                .user(user)
                .pickupLocation(request.getPickupLocation())
                .dropoffLocation(request.getDropoffLocation())
                .fare(request.getFare())
                .status(Ride.RideStatus.REQUESTED)
                .build();

        return rideRepository.save(ride);
    }

}
