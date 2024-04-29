package com.jy_dev.ninishop.order;

import com.jy_dev.ninishop.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="order_tb", indexes = {
        @Index(name = "order_user_id_idx", columnList = "user_id")
})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private int status;

    @CreationTimestamp // LocalDateTime : 시간까지
    private LocalDate orderDate;



    @Builder
    public Order(int id, User user, String status, String orderDate) {
        this.id = id;
        this.user = user;
    }
}
