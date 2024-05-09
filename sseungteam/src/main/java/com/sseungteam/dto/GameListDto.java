package com.sseungteam.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.sseungteam.constant.GameRelease;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameListDto {
    private Long id;
    private String gameName;
    private String gameDetail;
    private String imgUrl;
    private Integer price;
    private GameRelease gameRelease;

    @QueryProjection //쿼리dsl로 조회한 결과를 GameListDto 객체로 바로 받아올 수 있다.
    public GameListDto(Long id, String gameName, String gameDetail, String imgUrl, Integer price, GameRelease gameRelease) {
        this.id = id;
        this.gameName = gameName;
        this.gameDetail = gameDetail;
        this.imgUrl = imgUrl;
        this.price = price;
        this.gameRelease = gameRelease;
    }
}
