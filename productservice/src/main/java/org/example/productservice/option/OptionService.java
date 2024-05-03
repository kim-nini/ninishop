package org.example.productservice.option;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.productservice.core.errors.exception.Exception401;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OptionService {

    private final OptionJPARepository optionRepository;

    public OptionRequest.OptionProductDetailsForCart findByOptionId(long id) {
        // DTO로 바꿔주기
        Option option = optionRepository.findById(id).orElseThrow(
                () -> new Exception401("옵션이 없습니다.")
        );
        OptionRequest.OptionProductDetailsForCart response = new OptionRequest.OptionProductDetailsForCart(option);
        return response;
    }

}
