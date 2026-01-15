package com.demo.cymelle_backend.repositories;

import com.demo.cymelle_backend.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
