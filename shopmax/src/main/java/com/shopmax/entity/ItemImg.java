package com.shopmax.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="item_img")
@Getter
@Setter
@ToString
public class ItemImg extends BaseEntity {
    @Id
    @Column(name="item_img_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imgName; //UUID로 바뀐 이미지 파일명

    private String oriImgName; //원본 이미지 파일명

    private String imgUrl; //이미지 경로


    private String repImgYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    //ItemImg 엔티티에 대한 정보를 업데이트 하는 메소드 ->
    // 엔티티 값을 바꿔주는 메소드 이므로 엔티티 클래스 안에 작성
    public void updateItemImg(String oriImgName, String imgName, String imgUrl) {
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }

}
