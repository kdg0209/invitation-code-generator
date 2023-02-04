package com.invitationcode.generator.domain.orders.repository;

import com.invitationcode.generator.domain.orders.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
