package org.example.orderservice.cart;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {

    List<Cart> findByUserId(long userId);

    List<Cart> findByOptionIdAndUserId(long optionId, long userId);

    Optional<Cart> findCartByOptionIdAndUserId(long optionId, long userId);

    void deleteByUserId(long UserId);
}
