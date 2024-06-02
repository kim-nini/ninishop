package org.example.orderservice.order;

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
@Table(name="order_tb"
//        ,indexes = {@Index(name = "order_user_id_idx", columnList = "user_id")}
)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private long userId;

//    @Column(nullable = false)
//    @Enumerated(EnumType.STRING)
    @Convert(converter = TinyIntConverter.class)
    private StatusEnum status;

    @CreationTimestamp // LocalDateTime : 시간까지
    private LocalDate orderDate;



    @Builder
    public Order(long id, long userId, StatusEnum status, String orderDate) {
        this.id = id;
        this.status = status;
        this.userId = userId;
    }

    public void updateStatus(StatusEnum statusEnum) {
            this.status = statusEnum;
    }
}
