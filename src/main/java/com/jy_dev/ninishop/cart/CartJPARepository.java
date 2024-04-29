package com.jy_dev.ninishop.cart;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {

    List<Cart> findByUserId(int userId);

    List<Cart> findByOptionIdAndUserId(int optionId, int userId);

    Optional<Cart> findCartByOptionIdAndUserId(int optionId, int userId);

    void deleteByUserId(int UserId);
}
