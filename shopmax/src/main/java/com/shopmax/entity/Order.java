package com.shopmax.entity;

import com.shopmax.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders") //db에서 order by 예약어를 사용하므로 orders라고 저장
@Getter
@Setter
@ToString
public class Order { //클래스명은 복수형으로 쓰지 않는다.
    @Id
    @Column(name="order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime orderDate; //주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; //주문상태

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id") //FK키
    private Member member; //Order가 Member 참조

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch =  FetchType.LAZY
    , orphanRemoval = true) //연관관계 주인을 설정(order는 주인이 아니다) 주인은 fk를 가지고있는 엔티티
    private List<OrderItem> orderItems = new ArrayList<>();

}

