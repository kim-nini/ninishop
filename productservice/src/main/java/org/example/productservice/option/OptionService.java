package org.example.productservice.option;

import lombok.RequiredArgsConstructor;
import org.example.productservice.core.errors.exception.Exception401;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OptionService {

    private final OptionJPARepository optionRepository;

    public Option findByOptionId(int id){

        Option option = new Option();
        option = optionRepository.findById(id).orElseThrow(
                () -> new Exception401("옵션이 없습니다.")
        );

     return option;
    }

}
