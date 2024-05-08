package org.example.productservice.option;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.productservice.core.errors.exception.Exception500;
import org.example.productservice.product.Product;

// 재고 관리 여부는 없다.
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="option_tb",
        indexes = {
                @Index(name = "option_product_id_idx", columnList = "product_id")
        })
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @Column(length = 100, nullable = false)
    private String optionName;
    private long price;

    private long stock;

    @Builder
    public Option(long id, Product product, String optionName, long price) {
        this.id = id;
        this.product = product;
        this.optionName = optionName;
        this.price = price;
    }

    public void decreaseStock(Long unitCount) {
        long remainingStock = this.stock - unitCount;

        if (remainingStock < 0) {
            throw new Exception500("OUT OF STOCK");
        }
        this.stock = remainingStock;
    }


    public void decreaseStocks(long quantity) {
        this.stock -= quantity;
    }
}
