package org.example.orderservice.cart;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // jpa때무넹 피료
@Entity
@Table(name = "cart_tb",
//        indexes = {
//                @Index(name = "cart_user_id_idx", columnList = "user_id"),
//                @Index(name = "cart_option_id_idx", columnList = "option_id")
//        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_cart_option_user", columnNames = {"user_id", "option_id"})
        })
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private long userId; // user-service UserId 참조

    @Column(nullable = false)
    private long optionId; // Product 서비스에서의 Option ID 참조

    @Column(nullable = false)
    private long quantity;

    // 삭제 예정
    @Column(nullable = false)
    private long price;

    @Builder
    public Cart(long id, long userId, long optionId, long quantity, long price) {
        this.id = id;
        this.userId = userId;
        this.optionId = optionId;
        this.quantity = quantity;
        this.price = price;
    }

    @Builder
    public Cart(long id, long quantity, long price) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
    }

    // 장바구니 업데이트
    public void update(long quantity, long price) {
        this.quantity = quantity;
        this.price = price;
    }
}