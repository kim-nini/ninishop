package org.example.productservice.product;

import lombok.RequiredArgsConstructor;
import org.example.productservice.core.errors.exception.Exception401;
import org.example.productservice.option.Option;
import org.example.productservice.option.OptionJPARepository;
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
    public List<ProductResponse.findAllDTO> getAllProducts() {
        List<Product> products = productJPARepository.findAll();

        return products.stream()
                .map(ProductResponse.findAllDTO::new)
                .toList();
    }

    // 상품 상세조회
    public ProductResponse.ProductDetailDTO getProductDetail(int id) {
        Product product = productJPARepository.findById(id).orElseThrow(() -> new Exception401("no products: "+id));
        List<Option> option = optionJPARepository.findByProductId(id);
        return new ProductResponse.ProductDetailDTO(product, option);
    }
}
