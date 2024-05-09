package com.sseungteam.entity;

import com.sseungteam.constant.GameRelease;
import com.sseungteam.dto.GameFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "game")
@Getter
@Setter
@ToString
public class Game {
    @Id
    @Column(name = "game_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String gameName;

    @Lob
    @Column(columnDefinition = "longtext")
    private String gameDetail;

    private String gameDate;
    private int price;

    @Enumerated(EnumType.STRING)
    private GameRelease gameRelease;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, fetch =  FetchType.LAZY
            , orphanRemoval = true) //연관관계 주인을 설정(order는 주인이 아니다) 주인은 fk를 가지고있는 엔티티
    private List<GameImg> gameImgs = new ArrayList<>();

    //game 엔티티 수정
    public void updateGame(GameFormDto gameFormDto) {
        this.gameName = gameFormDto.getGameName();
        this.gameDetail = gameFormDto.getGameDetail();
        this.price = gameFormDto.getPrice();
        this.gameRelease = gameFormDto.getGameRelease();
        this.gameDate = gameFormDto.getGameDate();
    }
}
