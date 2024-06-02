package org.example.orderservice.order.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ItemJPARepository extends JpaRepository<Item, Long> {
    List<Item> findAllByOrderId(long orderId);
}
