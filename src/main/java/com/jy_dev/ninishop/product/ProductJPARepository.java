package com.jy_dev.ninishop.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductJPARepository extends JpaRepository<Product, Long> {

    Optional<Product> findById(int id);

}
