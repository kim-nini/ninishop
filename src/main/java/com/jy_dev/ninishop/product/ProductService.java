package com.jy_dev.ninishop.product;

import com.jy_dev.ninishop.core.errors.exception.Exception401;
import com.jy_dev.ninishop.option.Option;
import com.jy_dev.ninishop.option.OptionJPARepository;
import com.jy_dev.ninishop.product.ProductResponse.findAllDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductJPARepository productJPARepository;
    private final OptionJPARepository optionJPARepository;

    // 상품조회
    @Transactional(readOnly = true)
    public List<findAllDTO> getAllProducts() {
        List<Product> products = productJPARepository.findAll();

        return products.stream()
                .map(findAllDTO::new)
                .toList();
    }

    // 상품 상세조회
    public ProductResponse.ProductDetailDTO getProductDetail(int id) {
        Product product = productJPARepository.findById(id).orElseThrow(() -> new Exception401("no products: "+id));
        List<Option> option = optionJPARepository.findByProductId(id);
        return new ProductResponse.ProductDetailDTO(product, option);
    }
}
