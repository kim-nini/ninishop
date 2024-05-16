package org.example.productservice.option;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.productservice.core.errors.exception.Exception400;
import org.example.productservice.redisson.DistributedLock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OptionService {

    private final EntityManager em;
    private final OptionJPARepository optionRepository;

    public OptionResponse.OptionProductDetailsForCart findByOptionId(long id) {
        Option option = optionRepository.findById(id).orElseThrow(
                () -> new Exception400("옵션이 없습니다.")
        );
        OptionResponse.OptionProductDetailsForCart response = new OptionResponse.OptionProductDetailsForCart(option);
        return response;
    }

    // 주문 요청시 재고 차감
    @DistributedLock(value = "stockLock")
    public void decreaseStockByOption(List<OptionRequest.OptionDetailForStockUpdate> requestData) {
        // 받아온 옵션(상품) id로 옵션 조회 하고
        // - 해줄때 재고가 없으면 exception
        // 영속성 컨텍스트 초기화
        for (OptionRequest.OptionDetailForStockUpdate detail : requestData) {
            Option option = optionRepository.findById(detail.getOptionId()).orElseThrow(() -> new Exception400("옵션이 없습니다."));
            option.decreaseStocks(detail.getQuantity());
            optionRepository.save(option); // 세이브까지적용
        }
    }

    @Transactional
    public void restoreStockByOption(List<OptionRequest.OptionDetailForStockUpdate> requestData) {
        for (OptionRequest.OptionDetailForStockUpdate detail : requestData) {
            Option option = optionRepository.findById(detail.getOptionId()).orElseThrow(() -> new Exception400("옵션이 없습니다."));
            option.restoreStocks(detail.getQuantity());
        }
    }
}
