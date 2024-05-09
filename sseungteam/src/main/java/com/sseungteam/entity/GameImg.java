package com.sseungteam.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name= "game_img")
@Getter
@Setter
@ToString
public class GameImg extends BaseEntity {
    @Id
    @Column(name="game_img_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imgName; //UUID로 바뀐 이미지 파일명

    private String oriImgName; //원본 이미지 파일명

    private String imgUrl; //이미지 경로

    private String repImgYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="game_id")
    private Game game;

    public void updateGameImg(String oriImgName, String imgName, String imgUrl) {
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }
}
