package org.example.productservice.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductJPARepository extends JpaRepository<Product, Long> {

    Optional<Product> findById(long id);

}
