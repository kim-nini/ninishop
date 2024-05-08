package org.example.productservice.option;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface OptionJPARepository extends JpaRepository<Option, Long> {

    List<Option> findByProductId(long productId);
    Optional<Option> findById(long id);


}
