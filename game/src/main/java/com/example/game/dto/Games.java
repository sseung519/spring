package com.example.game.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Games {
    private int gNo;
    private String gName;
    private String gInfo;
    private String gDate;
    private String gPrice;
    private String img;

    private Member member;
}
