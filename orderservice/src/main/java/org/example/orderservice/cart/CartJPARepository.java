package org.example.orderservice.cart;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {

    List<Cart> findByUserId(int userId);

    List<Cart> findByOptionIdAndUserId(int optionId, int userId);

    Optional<Cart> findCartByOptionIdAndUserId(int optionId, int userId);

    void deleteByUserId(int UserId);
}
