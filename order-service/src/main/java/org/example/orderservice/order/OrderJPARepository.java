package org.example.orderservice.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrderJPARepository extends JpaRepository<Order, Long> {


    List<Order> findOrdersByStatusAndOrderDate(StatusEnum status, LocalDate orderDate);
}
