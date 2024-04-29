package com.shopmax.dto;

import com.shopmax.entity.OrderItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDto {

    //엔티티 -> Dto
    //OrderItem 엔티티와 OrderItemDto는 속성이 일치하지 않으므로 modelMapper를 사용 X
    public OrderItemDto(OrderItem orderItem, String imgUrl) {
        this.itemNm = orderItem.getItem().getItemNm();
        this.count = orderItem.getCount();
        this.orderPrice = orderItem.getOrderPrice();
        this.imgUrl = imgUrl;
    }

    private String itemNm;
    private int count;
    private int orderPrice;;
    private String imgUrl;

}
