package com.demo.cymelle_backend.repositories;

import com.demo.cymelle_backend.entities.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRepository extends JpaRepository <Ride, Long> {
}
