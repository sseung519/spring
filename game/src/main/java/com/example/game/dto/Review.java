package com.example.game.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Review {
    private int rNo;
    private String rTitle;
    private String rContent;
    private String rDate;

    private Member member;
    private Games games;
}
