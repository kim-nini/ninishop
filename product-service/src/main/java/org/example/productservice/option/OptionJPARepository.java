package org.example.productservice.option;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;
import java.util.Optional;

import static jakarta.persistence.LockModeType.PESSIMISTIC_WRITE;


public interface OptionJPARepository extends JpaRepository<Option, Long> {

    List<Option> findByProductId(long productId);

//    @Lock(PESSIMISTIC_WRITE) dblock
    Optional<Option> findById(long id);


}
